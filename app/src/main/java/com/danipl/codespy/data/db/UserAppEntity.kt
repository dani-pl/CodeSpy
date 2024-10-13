package com.danipl.codespy.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserAppEntity(
    @PrimaryKey val packageName: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "framework") val framework: String,
    @ColumnInfo(name = "icon") val iconUri: String
)