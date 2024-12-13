package com.pam.questroomdatabase_139.repository

import com.pam.questroomdatabase_139.Mahasiswa
import com.pam.questroomdatabase_139.data.dao.MahasiswaDao
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMhs (
    private val mahasiswaDao: MahasiswaDao
): RepositoryMhs{
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        mahasiswaDao.insertMahasiswa(mahasiswa)
    }

    override fun getAllMahasiswa(): Flow<List<Mahasiswa>> {
        return  mahasiswaDao.getALLMahasiswa()
    }
}