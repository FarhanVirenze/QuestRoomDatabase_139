package com.pam.questroomdatabase_139

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahasiswa")
data class Mahasiswa(
    @PrimaryKey
    val nim: String,
    val nama: String,
    val alamat: String,
    val jeniskelamin: String,
    val kelas: String,
    val angkatan: String,
)
