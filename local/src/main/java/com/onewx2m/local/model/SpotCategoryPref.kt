package com.onewx2m.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.onewx2m.data.model.category.SpotCategoryEntity
import com.onewx2m.local.TableName

@Entity(tableName = TableName.SPOT_CATEGORY)
data class SpotCategoryPref(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
)

fun SpotCategoryEntity.toPref() = SpotCategoryPref(
    id = id,
    name = name,
)

fun SpotCategoryPref.toEntity() = SpotCategoryEntity(
    id = id,
    name = name,
)
