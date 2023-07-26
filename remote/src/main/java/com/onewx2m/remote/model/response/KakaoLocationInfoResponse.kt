package com.onewx2m.remote.model.response

import com.onewx2m.data.model.KakaoLocationInfoDocumentEntity
import com.onewx2m.data.model.KakaoLocationInfoEntity
import com.onewx2m.data.model.MetaEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLocationInfoResponse(
    @SerialName("documents")
    val documents: List<KakaoLocationInfoDocumentResponse> = emptyList(),

    @SerialName("meta")
    val meta: MetaResponse = MetaResponse(),
)

@Serializable
data class KakaoLocationInfoDocumentResponse(
    @SerialName("place_name")
    val placeName: String = "",
    @SerialName("address_name")
    val addressName: String = "",
    @SerialName("road_address_name")
    val roadAddressName: String = "",
)

@Serializable
data class MetaResponse(
    @SerialName("is_end")
    val isEnd: Boolean = false,
    @SerialName("pageable_count")
    val documentTotalCount: Int = 0,
)

fun KakaoLocationInfoResponse.toEntity() = KakaoLocationInfoEntity(
    documents = documents.map { it.toEntity() },
    meta = meta.toEntity(),
)

fun KakaoLocationInfoDocumentResponse.toEntity() = KakaoLocationInfoDocumentEntity(
    placeName = placeName,
    addressName = addressName,
    roadAddressName = roadAddressName,
)

fun MetaResponse.toEntity() = MetaEntity(
    isEnd = isEnd,
    documentTotalCount = documentTotalCount,
)
