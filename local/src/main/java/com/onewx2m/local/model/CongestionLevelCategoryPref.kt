package com.onewx2m.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.onewx2m.data.model.category.CongestionLevelCategoryEntity
import com.onewx2m.local.TableName

@Entity(tableName = TableName.CONGESTION_LEVEL_CATEGORY)
data class CongestionLevelCategoryPref(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "value")
    val value: String,
)

fun CongestionLevelCategoryEntity.toPref() = CongestionLevelCategoryPref(
    key = key,
    value = value,
)

fun CongestionLevelCategoryPref.toEntity() = CongestionLevelCategoryEntity(
    key = key,
    value = value,
)
