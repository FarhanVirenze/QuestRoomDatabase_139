package com.pam.questroomdatabase_139.repository

import com.pam.questroomdatabase_139.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
    //getAllMhs
    fun getAllMahasiswa(): Flow<List<Mahasiswa>>
    //getMhs
    fun getMhs(nim: String): Flow<Mahasiswa>
    //deleteMhs
    suspend fun deleteMhs(mahasiswa: Mahasiswa)
    //updateMhs
    suspend fun updateMhs(mahasiswa: Mahasiswa)
}