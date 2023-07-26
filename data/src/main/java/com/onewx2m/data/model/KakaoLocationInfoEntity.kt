package com.onewx2m.data.model

import com.onewx2m.domain.model.KakaoLocationInfo
import com.onewx2m.domain.model.KakaoLocationInfoDocument
import com.onewx2m.domain.model.Meta

data class KakaoLocationInfoEntity(
    val documents: List<KakaoLocationInfoDocumentEntity> = emptyList(),
    val meta: MetaEntity = MetaEntity(),
)

data class KakaoLocationInfoDocumentEntity(
    val placeName: String = "",
    val addressName: String = "",
    val roadAddressName: String = "",
)

data class MetaEntity(
    val isEnd: Boolean = false,
    val documentTotalCount: Int = 0,
)

fun KakaoLocationInfoEntity.toDomain() = KakaoLocationInfo(
    documents = documents.map { it.toDomain() },
    meta = meta.toDomain(),
)

fun KakaoLocationInfoDocumentEntity.toDomain() = KakaoLocationInfoDocument(
    placeName = placeName,
    addressName = addressName,
    roadAddressName = roadAddressName,
)

fun MetaEntity.toDomain() = Meta(
    isEnd = isEnd,
    documentTotalCount = documentTotalCount,
)
