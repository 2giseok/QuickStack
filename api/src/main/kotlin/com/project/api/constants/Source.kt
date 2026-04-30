package com.project.api.constants

enum class Source(val code: String, val ko: String, val en: String) {
    // 국내
    KAKAO_TECH("KAKAO_TECH", "카카오 테크", "Kakao Tech"),
    WOOWAHAN("WOOWAHAN", "우아한형제들", "Woowahan Brothers"),
    KAKAO_PAY("KAKAO_PAY", "카카오페이", "Kakao Pay"),
    LINE_ENGINEERING("LINE_ENGINEERING", "라인", "LINE Engineering"),
    DAANGN("DAANGN", "당근", "Daangn"),
    MUSINSA("MUSINSA", "무신사", "Musinsa"),
    NHN("NHN", "NHN", "NHN"),
    SOCAR("SOCAR", "쏘카", "Socar"),
    WATCHA("WATCHA", "왓챠", "Watcha"),
    DEVSISTERS("DEVSISTERS", "데브시스터즈", "Devsisters"),
    BANKSALAD("BANKSALAD", "뱅크샐러드", "Banksalad"),
    MARKET_KURLY("MARKET_KURLY", "마켓컬리", "Market Kurly"),
    NAVER_D2("NAVER_D2", "네이버 D2", "Naver D2"),
    TOSS_TECH("TOSS_TECH", "토스", "Toss"),
    KAKAO_BANK("KAKAO_BANK", "카카오뱅크", "Kakao Bank"),
    OLIVE_YOUNG("OLIVE_YOUNG", "올리브영", "Olive Young"),

    // 국외
    NETFLIX("NETFLIX", "넷플릭스", "Netflix"),
    AIRBNB("AIRBNB", "에어비앤비", "Airbnb"),
    GOOGLE("GOOGLE", "구글", "Google"),
    SLACK("SLACK", "슬랙", "Slack"),
    AMAZON("AMAZON", "아마존", "Amazon"),
    GITHUB("GITHUB", "깃허브", "GitHub"),
    SPOTIFY("SPOTIFY", "스포티파이", "Spotify"),
    DROPBOX("DROPBOX", "드롭박스", "Dropbox"),
    FACEBOOK("FACEBOOK", "페이스북", "Facebook"),
    CLOUDFLARE("CLOUDFLARE", "클라우드플레어", "Cloudflare"),
    PINTEREST("PINTEREST", "핀터레스트", "Pinterest"),
    LYFT("LYFT", "리프트", "Lyft"),
    MICROSOFT("MICROSOFT", "마이크로소프트", "Microsoft"),
    STRIPE("STRIPE", "스트라이프", "Stripe"),
    ;

    companion object {
        private val codeMap = entries.associateBy { it.code }
        fun fromCode(code: String): Source? = codeMap[code]
    }
}