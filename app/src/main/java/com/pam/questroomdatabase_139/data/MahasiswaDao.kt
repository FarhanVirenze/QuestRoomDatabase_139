package com.pam.questroomdatabase_139.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    //getAllMahasiswa
    @Query("SELECT * FROM mahasiswa ORDER BY nama ASC")
    fun getALLMahasiswa() : Flow<List<Mahasiswa>>
    //getMahasiswa
    @Query("SELECT * FROM mahasiswa WHERE nim = :nim")
    fun getMahasiswa(nim: String): Flow<Mahasiswa>
    @Insert
    suspend fun insertMahasiswa(
        mahasiswa: Mahasiswa
    )
}
