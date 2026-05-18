package com.project.crawler.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Source {


    // ===== 국내 - RSS =====
    KAKAO_TECH("카카오 테크", "https://tech.kakao.com/feed/", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    WOOWAHAN("우아한형제들 기술 블로그", "https://techblog.woowahan.com/feed/", CollectionType.RSS, BlogRegion.DOMESTIC, "https://techblog.woowahan.com/", null),
    KAKAO_PAY("카카오페이 테크", "https://tech.kakaopay.com/rss.xml", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    LINE_ENGINEERING("LINE Engineering", "https://engineering.linecorp.com/ko/feed", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    DAANGN("당근", "https://medium.com/feed/daangn", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    MUSINSA("무신사 기술 블로그", "https://medium.com/feed/musinsa-tech", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    NHN("NHN Cloud", "https://meetup.nhncloud.com/rss", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    SOCAR("쏘카 기술 블로그", "https://tech.socarcorp.kr/feed.xml", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    WATCHA("왓챠 기술 블로그", "https://medium.com/feed/watcha", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    DEVSISTERS("데브시스터즈 기술 블로그", "https://tech.devsisters.com/rss.xml", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    BANKSALAD("뱅크샐러드 기술 블로그", "https://blog.banksalad.com/rss.xml", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),
    MARKET_KURLY("마켓컬리", "https://helloworld.kurly.com/feed/", CollectionType.RSS, BlogRegion.DOMESTIC, null, null),

    // ===== 국내 - HTML =====
    NAVER_D2("Naver D2", "https://d2.naver.com/home", CollectionType.HTML, BlogRegion.DOMESTIC, null, null),

    // ===== 국내 - PLAYWRIGHT =====
    TOSS_TECH("토스 테크", "https://toss.tech", CollectionType.PLAYWRIGHT, BlogRegion.DOMESTIC, null, null),
    KAKAO_BANK("카카오뱅크 기술 블로그", "https://tech.kakaobank.com", CollectionType.PLAYWRIGHT, BlogRegion.DOMESTIC, null, null),
    OLIVE_YOUNG("올리브영 기술 블로그", "https://oliveyoung.tech/blog", CollectionType.PLAYWRIGHT, BlogRegion.DOMESTIC, null, null),

    // ===== 국외 - RSS =====
    NETFLIX("Netflix Tech Blog", "https://medium.com/feed/netflix-techblog", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    AIRBNB("Airbnb Engineering", "https://medium.com/feed/airbnb-engineering", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    GOOGLE("Google Developers Blog", "https://developers.googleblog.com/feeds/posts/default", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    SLACK("Slack Engineering", "https://slack.engineering/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    AMAZON("Amazon AWS Blog", "https://aws.amazon.com/blogs/aws/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    GITHUB("Github Blog", "https://github.blog/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    SPOTIFY("Spotify Engineering", "https://engineering.atspotify.com/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    DROPBOX("Dropbox Tech Blog", "https://dropbox.tech/feed", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    FACEBOOK("Facebook Engineering", "https://engineering.fb.com/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    CLOUDFLARE("Cloudflare Blog", "https://blog.cloudflare.com/rss/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    PINTEREST("Pinterest Engineering", "https://medium.com/feed/pinterest-engineering", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    LYFT("Lyft Engineering", "https://eng.lyft.com/feed", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    MICROSOFT("Microsoft Engineering", "https://devblogs.microsoft.com/engineering-at-microsoft/feed/", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null),
    STRIPE("Stripe Engineering", "https://stripe.com/blog/feed.rss", CollectionType.RSS, BlogRegion.INTERNATIONAL, null, null);


    private final String displayName;
    private final String url;
    private final CollectionType type;
    private final BlogRegion region;
    private final String referer;
    private final String userAgent;


}