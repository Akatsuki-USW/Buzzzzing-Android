package com.onewx2m.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.onewx2m.data.model.CongestionHistoricalDateCategoryEntity
import com.onewx2m.local.TableName

@Entity(tableName = TableName.CONGESTION_HISTORICAL_DATE_CATEGORY)
data class CongestionHistoricalDateCategoryPref(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "value")
    val value: String,
    @ColumnInfo(name = "query")
    val query: String,
)

fun CongestionHistoricalDateCategoryEntity.toPref() = CongestionHistoricalDateCategoryPref(
    key = key,
    value = value,
    query = query,
)

fun CongestionHistoricalDateCategoryPref.toEntity() = CongestionHistoricalDateCategoryEntity(
    key = key,
    value = value,
    query = query,
)
