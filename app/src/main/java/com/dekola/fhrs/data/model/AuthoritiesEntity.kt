package com.dekola.fhrs.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dekola.fhrs.AUTHORITIES_TABLE
import java.util.*

@Entity(tableName = AUTHORITIES_TABLE)
data class AuthoritiesEntity(
    @ColumnInfo(name = "Id")
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var name: String?,
)