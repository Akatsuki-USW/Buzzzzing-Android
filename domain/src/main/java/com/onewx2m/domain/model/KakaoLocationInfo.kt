package com.onewx2m.domain.model

data class KakaoLocationInfo(
    val documents: List<KakaoLocationInfoDocument> = emptyList(),
    val meta: Meta = Meta(),
)

data class KakaoLocationInfoDocument(
    val placeName: String = "",
    val addressName: String = "",
    val roadAddressName: String = "",
)

data class Meta(
    val isEnd: Boolean = false,
    val documentTotalCount: Int = 0,
)
