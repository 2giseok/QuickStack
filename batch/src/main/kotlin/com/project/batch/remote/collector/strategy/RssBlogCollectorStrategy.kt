package com.project.batch.remote.collector.strategy

import com.project.batch.constants.BlogSource
import com.project.batch.constants.CollectionType
import com.project.batch.domain.TechBlog
import com.project.batch.utils.Snowflake
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Component
class RssBlogCollectorStrategy(
    private val snowflake: Snowflake,
) : BlogCollectorStrategy {

    companion object {
        private const val USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"

        private val trustAllCerts = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    }

    private val httpClient = run {
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(trustAllCerts), SecureRandom())
        }
        OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts)
            .hostnameVerifier { _, _ -> true }
            .followRedirects(true)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    override fun supports(type: CollectionType): Boolean = type == CollectionType.RSS

    override suspend fun collect(source: BlogSource): List<TechBlog> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(source.url)
            .header(HttpHeaders.USER_AGENT, source.userAgent ?: USER_AGENT)
            .header(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .header(HttpHeaders.ACCEPT_LANGUAGE, "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
            .apply { source.referer?.let { header(HttpHeaders.REFERER, it) } }
            .build()

        val bytes = httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return@withContext emptyList()
            response.body?.bytes() ?: return@withContext emptyList()
        }

        val feed = SyndFeedInput().build(XmlReader(bytes.inputStream()))

        feed.entries.mapNotNull { entry ->
            val title = entry.title?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val url = (entry.link ?: entry.uri)?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val publishedAt = (entry.publishedDate ?: entry.updatedDate)?.toInstant() ?: return@mapNotNull null
            TechBlog(
                id = snowflake.nextId(),
                source = source.name,
                region = source.region,
                title = title,
                url = url,
                publishedAt = publishedAt,
            )
        }
    }
}