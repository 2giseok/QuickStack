package com.project.admin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Source {

    KAKAO("카카오", "https://tech.kakao.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    WOOWAHAN("우아한형제들", "https://techblog.woowahan.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    KAKAO_PAY("카카오페이", "https://tech.kakaopay.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    LINE("LINE", "https://engineering.linecorp.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    DAANGN("당근", "https://about.daangn.com/blog/", CollectionType.RSS, Region.DOMESTIC,null,null),
    MUSINSA("무신사", "https://techblog.musinsa.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    NHN("NHN", "https://meetup.nhncloud.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    SOCAR("쏘카", "https://tech.socarcorp.kr", CollectionType.RSS, Region.DOMESTIC,null,null),
    WATCHA("왓챠", "https://medium.com/watcha", CollectionType.RSS, Region.DOMESTIC,null,null),
    DEVSISTERS("데브시스터즈", "https://tech.devsisters.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    BANKSALAD("뱅크샐러드", "https://blog.banksalad.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    KURLY("컬리", "https://helloworld.kurly.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    NAVER_D2("Naver D2", "https://d2.naver.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    TOSS("토스", "https://toss.tech", CollectionType.RSS, Region.DOMESTIC,null,null),
    KAKAO_BANK("카카오뱅크", "https://tech.kakaobank.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    OLIVE_YOUNG("올리브영", "https://oliveyoung.tech", CollectionType.RSS, Region.DOMESTIC,null,null),
    POSTYPE("포스타입", "https://team.postype.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    AITRICS("AITRICS", "https://medium.com/aitrics", CollectionType.RSS, Region.DOMESTIC,null,null),
    BEUSABLE("뷰저블", "https://beusable.net/blog", CollectionType.RSS, Region.DOMESTIC,null,null),
    MY_REAL_TRIP("마이리얼트립", "https://medium.com/myrealtrip-product", CollectionType.RSS, Region.DOMESTIC,null,null),
    YOGI_YEO_GI("여기어때", "https://techblog.gccompany.co.kr", CollectionType.RSS, Region.DOMESTIC,null,null),
    YOGIYO("요기요", "https://techblog.yogiyo.co.kr", CollectionType.RSS, Region.DOMESTIC,null,null),
    HYPERCONNECT("하이퍼커넥트", "https://hyperconnect.github.io", CollectionType.RSS, Region.DOMESTIC,null,null),
    SAMSUNG("Samsung", "https://developer.samsung.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    PET_FRIENDS("펫프렌즈", "https://techblog.pet-friends.co.kr", CollectionType.RSS, Region.DOMESTIC,null,null),
    LOTTE_ON("롯데ON", "https://techblog.lotteon.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    SARAMIN("사람인", "https://saramin.github.io", CollectionType.RSS, Region.DOMESTIC,null,null),
    SPOQA("스포카", "https://spoqa.github.io", CollectionType.RSS, Region.DOMESTIC,null,null),
    HANCOM("한글과컴퓨터", "https://tech.hancom.com", CollectionType.RSS, Region.DOMESTIC,null,null),
    AUTOPEDIA("오토피디아", "https://medium.com/autopedia", CollectionType.RSS, Region.DOMESTIC,null,null),
    BABITALK("바비톡", "https://medium.com/@babitalk", CollectionType.RSS, Region.DOMESTIC,null,null),
    WANTED("원티드", "https://medium.com/wantedjobs", CollectionType.RSS, Region.DOMESTIC,null,null),

    NETFLIX("Netflix", "https://medium.com/netflix-techblog", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    AIRBNB("Airbnb", "https://medium.com/airbnb-engineering", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    GOOGLE("Google", "https://developers.googleblog.com", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    SLACK("Slack", "https://slack.engineering", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    AMAZON("Amazon", "https://aws.amazon.com/blogs/aws", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    GITHUB("Github", "https://github.blog", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    SPOTIFY("Spotify", "https://engineering.atspotify.com", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    DROPBOX("Dropbox", "https://dropbox.tech", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    FACEBOOK("Facebook", "https://engineering.fb.com", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    CLOUDFLARE("Cloudflare", "https://blog.cloudflare.com", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    PINTEREST("Pinterest", "https://medium.com/pinterest-engineering", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    LYFT("Lyft", "https://eng.lyft.com", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    MICROSOFT("Microsoft", "https://devblogs.microsoft.com/engineering-at-microsoft", CollectionType.RSS, Region.INTERNATIONAL,null,null),
    STRIPE("Stripe", "https://stripe.com/blog", CollectionType.RSS, Region.INTERNATIONAL,null,null);

    private final String displayName;
    private final String url;
    private final CollectionType type;
    private final Region region;
    private final String referer;
    private final String userAgent;

}