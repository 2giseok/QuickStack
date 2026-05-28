package com.project.admin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Source {

    KAKAO("카카오", "https://tech.kakao.com/feed/", CollectionType.RSS, Region.DOMESTIC,null,null),
    WOOWAHAN("우아한형제들", "https://techblog.woowahan.com/feed/", CollectionType.RSS, Region.DOMESTIC,null,null),
    KAKAO_PAY("카카오페이", "https://tech.kakaopay.com/rss.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    LINE("LINE", "https://engineering.linecorp.com/ko/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    DAANGN("당근", "https://medium.com/feed/daangn", CollectionType.RSS, Region.DOMESTIC,null,null),
    MUSINSA("무신사", "https://medium.com/feed/musinsa-tech", CollectionType.RSS, Region.DOMESTIC,null,null),
    NHN("NHN", "https://meetup.nhncloud.com/rss", CollectionType.RSS, Region.DOMESTIC,null,null),
    SOCAR("쏘카", "https://tech.socarcorp.kr/feed.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    WATCHA("왓챠", "https://medium.com/feed/watcha", CollectionType.RSS, Region.DOMESTIC,null,null),
    DEVSISTERS("데브시스터즈", "https://tech.devsisters.com/rss.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    BANKSALAD("뱅크샐러드", "https://blog.banksalad.com/rss.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    KURLY("컬리", "https://helloworld.kurly.com/rss.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    NAVER_D2("Naver D2", "https://d2.naver.com/d2.atom", CollectionType.RSS, Region.DOMESTIC,null,null),
    TOSS("토스", "https://toss.tech/rss.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    KAKAO_BANK("카카오뱅크", "https://tech.kakaobank.com/index.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    OLIVE_YOUNG("올리브영", "https://oliveyoung.tech/rss.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    POSTYPE("포스타입", "https://team.postype.com/rss", CollectionType.RSS, Region.DOMESTIC,null,null),
    AITRICS("AITRICS", "https://medium.com/feed/aitrics", CollectionType.RSS, Region.DOMESTIC,null,null),
    BEUSABLE("뷰저블", "https://beusable.net/blog/?feed=rss2", CollectionType.RSS, Region.DOMESTIC,null,null),
    MY_REAL_TRIP("마이리얼트립", "https://medium.com/feed/myrealtrip-product", CollectionType.RSS, Region.DOMESTIC,null,null),
    YOGI_YEO_GI("여기어때", "https://techblog.gccompany.co.kr/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    YOGIYO("요기요", "https://techblog.yogiyo.co.kr/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    HYPERCONNECT("하이퍼커넥트", "https://hyperconnect.github.io/feed.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    SAMSUNG("Samsung", "https://developer.samsung.com/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    PET_FRIENDS("펫프렌즈", "https://techblog.pet-friends.co.kr/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    LOTTE_ON("롯데ON", "https://techblog.lotteon.com/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    SARAMIN("사람인", "https://saramin.github.io/feed.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    SPOQA("스포카", "https://spoqa.github.io/atom.xml", CollectionType.RSS, Region.DOMESTIC,null,null),
    HANCOM("한글과컴퓨터", "https://tech.hancom.com/feed", CollectionType.RSS, Region.DOMESTIC,null,null),
    AUTOPEDIA("오토피디아", "https://medium.com/feed/autopedia", CollectionType.RSS, Region.DOMESTIC,null,null),
    BABITALK("바비톡", "https://medium.com/feed/@babitalk", CollectionType.RSS, Region.DOMESTIC,null,null),
    WANTED("원티드", "https://medium.com/feed/wantedjobs", CollectionType.RSS, Region.DOMESTIC,null,null),

    NETFLIX("Netflix", "https://medium.com/feed/netflix-techblog", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    AIRBNB("Airbnb", "https://medium.com/feed/airbnb-engineering", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    GOOGLE("Google", "https://developers.googleblog.com/feeds/posts/default/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    SLACK("Slack", "https://slack.engineering/feed/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    AMAZON("Amazon", "https://aws.amazon.com/blogs/aws/feed/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    GITHUB("Github", "https://github.blog/feed/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    SPOTIFY("Spotify", "https://engineering.atspotify.com/feed/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    DROPBOX("Dropbox", "https://dropbox.tech/feed", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    FACEBOOK("Facebook", "https://engineering.fb.com/feed/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    CLOUDFLARE("Cloudflare", "https://blog.cloudflare.com/rss/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    PINTEREST("Pinterest", "https://medium.com/feed/pinterest-engineering", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    LYFT("Lyft", "https://eng.lyft.com/feed", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    MICROSOFT("Microsoft", "https://devblogs.microsoft.com/engineering-at-microsoft/feed/", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    STRIPE("Stripe", "https://stripe.com/blog/feed.rss", CollectionType.RSS, Region.INTERNATIONAL,null,null);

    private final String displayName;
    private final String url;
    private final CollectionType type;
    private final Region region;
    private final String referer;
    private final String userAgent;

}