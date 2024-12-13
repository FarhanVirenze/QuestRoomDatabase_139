package com.pam.questroomdatabase_139.repository

import com.pam.questroomdatabase_139.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
    fun getAllMahasiswa(): Flow<List<Mahasiswa>>
}