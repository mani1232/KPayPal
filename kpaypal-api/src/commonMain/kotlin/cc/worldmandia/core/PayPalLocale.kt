package cc.worldmandia.core

enum class PayPalLocale(val languageTag: String) {
    ENGLISH_US("en_US"),
    ENGLISH_UK("en_GB"),
    ENGLISH_AUSTRALIA("en_AU"),
    ENGLISH_CANADA("en_CA"),
    GERMAN("de_DE"),
    FRENCH("fr_FR"),
    FRENCH_CANADA("fr_CA"),
    ITALIAN("it_IT"),
    SPANISH("es_ES"),
    SPANISH_MEXICO("es_MX"),
    DUTCH("nl_NL"),
    JAPANESE("ja_JP"),
    CHINESE_CHINA("zh_CN"),
    CHINESE_HONG_KONG("zh_HK"),
    CHINESE_TAIWAN("zh_TW"),
    POLISH("pl_PL"),
    PORTUGUESE_BRAZIL("pt_BR"),
    PORTUGUESE_PORTUGAL("pt_PT"),
    RUSSIAN("ru_RU"),
    SWEDISH("sv_SE"),
    THAI("th_TH"),
    TURKISH("tr_TR"),
    DANISH("da_DK"),
    CZECH("cs_CZ"),
    NORWEGIAN("no_NO"),
    HEBREW("he_IL"),
    ARABIC("ar_EG");

    companion object {
        fun fromLanguageTag(tag: String): PayPalLocale? = entries.find { it.languageTag == tag }
    }
}