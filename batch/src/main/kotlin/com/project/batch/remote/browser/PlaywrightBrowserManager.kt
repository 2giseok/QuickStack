package com.project.batch.remote.browser

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.options.LoadState
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class PlaywrightBrowserManager : DisposableBean {

    val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val playwright: Lazy<Playwright> = lazy { Playwright.create() }
    private val browser: Lazy<Browser> = lazy {
        playwright.value.chromium().launch(BrowserType.LaunchOptions().setHeadless(true))
    }

    override fun destroy() {
        if (browser.isInitialized()) browser.value.close()
        if (playwright.isInitialized()) playwright.value.close()
        dispatcher.close()
    }

    suspend fun fetchHtml(url: String): String? = withContext(dispatcher) {
        runCatching {
            browser.value.newPage().use { page ->
                page.navigate(url)
                page.waitForLoadState(LoadState.NETWORKIDLE)
                page.content()
            }
        }.getOrNull()
    }
}