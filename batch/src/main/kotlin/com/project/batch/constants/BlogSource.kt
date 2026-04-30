package com.project.batch.constants

enum class BlogSource(
    val displayName: String,
    val url: String,
    val type: CollectionType,
    val region: BlogRegion,
    val referer: String? = null,
    val userAgent: String? = null,
) {

    // ===== 국내 - RSS =====
    KAKAO_TECH("카카오 테크", "https://tech.kakao.com/feed/", CollectionType.RSS, BlogRegion.DOMESTIC),
    WOOWAHAN("우아한형제들 기술 블로그", "https://techblog.woowahan.com/feed/", CollectionType.RSS, BlogRegion.DOMESTIC, referer = "https://techblog.woowahan.com/"),
    KAKAO_PAY("카카오페이 테크", "https://tech.kakaopay.com/rss.xml", CollectionType.RSS, BlogRegion.DOMESTIC),
    LINE_ENGINEERING("LINE Engineering", "https://engineering.linecorp.com/ko/feed", CollectionType.RSS, BlogRegion.DOMESTIC),
    DAANGN("당근", "https://medium.com/feed/daangn", CollectionType.RSS, BlogRegion.DOMESTIC),
    MUSINSA("무신사 기술 블로그", "https://medium.com/feed/musinsa-tech", CollectionType.RSS, BlogRegion.DOMESTIC),
    NHN("NHN Cloud", "https://meetup.nhncloud.com/rss", CollectionType.RSS, BlogRegion.DOMESTIC),
    SOCAR("쏘카 기술 블로그", "https://tech.socarcorp.kr/feed.xml", CollectionType.RSS, BlogRegion.DOMESTIC),
    WATCHA("왓챠 기술 블로그", "https://medium.com/feed/watcha", CollectionType.RSS, BlogRegion.DOMESTIC),
    DEVSISTERS("데브시스터즈 기술 블로그", "https://tech.devsisters.com/rss.xml", CollectionType.RSS, BlogRegion.DOMESTIC),
    BANKSALAD("뱅크샐러드 기술 블로그", "https://blog.banksalad.com/rss.xml", CollectionType.RSS, BlogRegion.DOMESTIC),
    MARKET_KURLY("마켓컬리", "https://helloworld.kurly.com/feed/", CollectionType.RSS, BlogRegion.DOMESTIC),

    // ===== 국내 - HTML =====
    NAVER_D2("Naver D2", "https://d2.naver.com/home", CollectionType.HTML, BlogRegion.DOMESTIC),

    // ===== 국내 - PLAYWRIGHT =====
    TOSS_TECH("토스 테크", "https://toss.tech", CollectionType.PLAYWRIGHT, BlogRegion.DOMESTIC),
    KAKAO_BANK("카카오뱅크 기술 블로그", "https://tech.kakaobank.com", CollectionType.PLAYWRIGHT, BlogRegion.DOMESTIC),
    OLIVE_YOUNG("올리브영 기술 블로그", "https://oliveyoung.tech/blog", CollectionType.PLAYWRIGHT, BlogRegion.DOMESTIC),

    // ===== 국외 - RSS =====
    NETFLIX("Netflix Tech Blog", "https://medium.com/feed/netflix-techblog", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    AIRBNB("Airbnb Engineering", "https://medium.com/feed/airbnb-engineering", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    GOOGLE("Google Developers Blog", "https://developers.googleblog.com/feeds/posts/default", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    SLACK("Slack Engineering", "https://slack.engineering/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    AMAZON("Amazon AWS Blog", "https://aws.amazon.com/blogs/aws/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    GITHUB("Github Blog", "https://github.blog/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    SPOTIFY("Spotify Engineering", "https://engineering.atspotify.com/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    DROPBOX("Dropbox Tech Blog", "https://dropbox.tech/feed", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    FACEBOOK("Facebook Engineering", "https://engineering.fb.com/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    CLOUDFLARE("Cloudflare Blog", "https://blog.cloudflare.com/rss/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    PINTEREST("Pinterest Engineering", "https://medium.com/feed/pinterest-engineering", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    LYFT("Lyft Engineering", "https://eng.lyft.com/feed", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    MICROSOFT("Microsoft Engineering", "https://devblogs.microsoft.com/engineering-at-microsoft/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL),
    STRIPE("Stripe Engineering", "https://stripe.com/blog/feed.rss", CollectionType.RSS, BlogRegion.INTERNATIONAL),
}