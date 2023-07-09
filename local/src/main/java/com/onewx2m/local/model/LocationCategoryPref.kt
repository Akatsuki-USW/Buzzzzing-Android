package com.onewx2m.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.onewx2m.data.model.category.LocationCategoryEntity
import com.onewx2m.local.TableName

@Entity(tableName = TableName.LOCATION_CATEGORY)
data class LocationCategoryPref(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "iconImageUrl")
    val iconImageUrl: String,
)

fun LocationCategoryEntity.toPref() = LocationCategoryPref(
    id = id,
    name = name,
    iconImageUrl = iconImageUrl,
)

fun LocationCategoryPref.toEntity() = LocationCategoryEntity(
    id = id,
    name = name,
    iconImageUrl = iconImageUrl,
)
