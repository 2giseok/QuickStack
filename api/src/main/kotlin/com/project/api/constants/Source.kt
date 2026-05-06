package com.project.api.constants

import com.project.api.exception.QuickStackException
import com.project.api.exception.TechStackErrorCode

enum class Source(val code: String, val ko: String, val en: String, val region: Region, val url: String, val isPopular: Boolean) {
    // 국내
    KAKAO("KAKAO", "카카오", "Kakao", Region.DOMESTIC, "https://tech.kakao.com", true),
    WOOWAHAN("WOOWAHAN", "우아한형제들", "Woowahan Brothers", Region.DOMESTIC, "https://techblog.woowahan.com", true),
    KAKAO_PAY("KAKAO_PAY", "카카오페이", "Kakao Pay", Region.DOMESTIC, "https://tech.kakaopay.com", false),
    LINE("LINE", "라인", "LINE", Region.DOMESTIC, "https://engineering.linecorp.com/ko", false),
    DAANGN("DAANGN", "당근", "Daangn", Region.DOMESTIC, "https://medium.com/daangn", true),
    MUSINSA("MUSINSA", "무신사", "Musinsa", Region.DOMESTIC, "https://medium.com/musinsa-tech", false),
    NHN("NHN", "NHN", "NHN", Region.DOMESTIC, "https://meetup.nhncloud.com", false),
    SOCAR("SOCAR", "쏘카", "Socar", Region.DOMESTIC, "https://tech.socarcorp.kr", false),
    WATCHA("WATCHA", "왓챠", "Watcha", Region.DOMESTIC, "https://medium.com/watcha", false),
    DEVSISTERS("DEVSISTERS", "데브시스터즈", "Devsisters", Region.DOMESTIC, "https://tech.devsisters.com", false),
    BANKSALAD("BANKSALAD", "뱅크샐러드", "Banksalad", Region.DOMESTIC, "https://blog.banksalad.com", false),
    KURLY("KURLY", "컬리", "Kurly", Region.DOMESTIC, "https://helloworld.kurly.com", false),
    NAVER_D2("NAVER_D2", "네이버 D2", "Naver D2", Region.DOMESTIC, "https://d2.naver.com", true),
    TOSS("TOSS", "토스", "Toss", Region.DOMESTIC, "https://toss.tech", true),
    KAKAO_BANK("KAKAO_BANK", "카카오뱅크", "Kakao Bank", Region.DOMESTIC, "https://tech.kakaobank.com", false),
    OLIVE_YOUNG("OLIVE_YOUNG", "올리브영", "Olive Young", Region.DOMESTIC, "https://oliveyoung.tech", false),
    MIRIDI("MIRIDI", "미리디", "Miridi", Region.DOMESTIC, "https://www.miridi.com/blog", false),
    POSTYPE("POSTYPE", "포스타입", "Postype", Region.DOMESTIC, "https://team.postype.com", false),
    AITRICS("AITRICS", "AITRICS", "AITRICS", Region.DOMESTIC, "https://medium.com/aitrics", false),
    BEUSABLE("BEUSABLE", "뷰저블", "Beusable", Region.DOMESTIC, "https://beusable.net/blog", false),
    FLEX("FLEX", "플렉스", "flex", Region.DOMESTIC, "https://flex.team/blog", false),
    MY_REAL_TRIP("MY_REAL_TRIP", "마이리얼트립", "MyRealTrip", Region.DOMESTIC, "https://medium.com/myrealtrip-product", false),
    YOGI_YEO_GI("YOGI_YEO_GI", "여기어때", "Yanolja GC Company", Region.DOMESTIC, "https://techblog.gccompany.co.kr", false),
    YOGIYO("YOGIYO", "요기요", "Yogiyo", Region.DOMESTIC, "https://techblog.yogiyo.co.kr", false),
    HYPERCONNECT("HYPERCONNECT", "하이퍼커넥트", "Hyperconnect", Region.DOMESTIC, "https://hyperconnect.github.io", false),
    SAMSUNG("SAMSUNG", "삼성", "Samsung", Region.DOMESTIC, "https://developer.samsung.com/blog", true),
    PET_FRIENDS("PET_FRIENDS", "펫프렌즈", "Pet Friends", Region.DOMESTIC, "https://techblog.pet-friends.co.kr", false),
    LOTTE_ON("LOTTE_ON", "롯데ON", "Lotte ON", Region.DOMESTIC, "https://techblog.lotteon.com", false),
    SARAMIN("SARAMIN", "사람인", "Saramin", Region.DOMESTIC, "https://saramin.github.io", false),
    SPOQA("SPOQA", "스포카", "Spoqa", Region.DOMESTIC, "https://spoqa.github.io", false),
    HANCOM("HANCOM", "한글과컴퓨터", "Hancom", Region.DOMESTIC, "https://tech.hancom.com", false),
    AUTOPEDIA("AUTOPEDIA", "오토피디아", "Autopedia", Region.DOMESTIC, "https://medium.com/autopedia", false),
    BABITALK("BABITALK", "바비톡", "Babitalk", Region.DOMESTIC, "https://medium.com/@babitalk", false),
    WANTED("WANTED", "원티드", "Wanted", Region.DOMESTIC, "https://medium.com/wantedjobs", false),

    // 국외
    NETFLIX("NETFLIX", "넷플릭스", "Netflix", Region.INTERNATIONAL, "https://netflixtechblog.com", true),
    AIRBNB("AIRBNB", "에어비앤비", "Airbnb", Region.INTERNATIONAL, "https://medium.com/airbnb-engineering", false),
    GOOGLE("GOOGLE", "구글", "Google", Region.INTERNATIONAL, "https://developers.googleblog.com", false),
    SLACK("SLACK", "슬랙", "Slack", Region.INTERNATIONAL, "https://slack.engineering", false),
    AMAZON("AMAZON", "아마존", "Amazon", Region.INTERNATIONAL, "https://aws.amazon.com/blogs/aws", false),
    GITHUB("GITHUB", "깃허브", "GitHub", Region.INTERNATIONAL, "https://github.blog", true),
    SPOTIFY("SPOTIFY", "스포티파이", "Spotify", Region.INTERNATIONAL, "https://engineering.atspotify.com", false),
    DROPBOX("DROPBOX", "드롭박스", "Dropbox", Region.INTERNATIONAL, "https://dropbox.tech", false),
    FACEBOOK("FACEBOOK", "페이스북", "Facebook", Region.INTERNATIONAL, "https://engineering.fb.com", false),
    CLOUDFLARE("CLOUDFLARE", "클라우드플레어", "Cloudflare", Region.INTERNATIONAL, "https://blog.cloudflare.com", false),
    PINTEREST("PINTEREST", "핀터레스트", "Pinterest", Region.INTERNATIONAL, "https://medium.com/pinterest-engineering", false),
    LYFT("LYFT", "리프트", "Lyft", Region.INTERNATIONAL, "https://eng.lyft.com", false),
    MICROSOFT("MICROSOFT", "마이크로소프트", "Microsoft", Region.INTERNATIONAL, "https://devblogs.microsoft.com/engineering-at-microsoft", false),
    STRIPE("STRIPE", "스트라이프", "Stripe", Region.INTERNATIONAL, "https://stripe.com/blog", false),
    ;

    companion object {
        private val codeMap = entries.associateBy { it.code }
        val popularEntries: List<Source> = entries.filter { it.isPopular }

        fun fromCode(code: String): Source =
            codeMap[code] ?: throw QuickStackException(TechStackErrorCode.INVALID_SOURCE)

        fun fromValue(value: String): Source? =
            entries.firstOrNull { it.code == value || it.ko == value || it.en == value }
    }
}