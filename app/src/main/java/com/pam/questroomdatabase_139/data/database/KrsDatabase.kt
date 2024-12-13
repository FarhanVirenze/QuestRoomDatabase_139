package com.pam.questroomdatabase_139.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pam.questroomdatabase_139.Mahasiswa
import com.pam.questroomdatabase_139.data.dao.MahasiswaDao

@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase() {

    abstract fun mahasiswaDao(): MahasiswaDao

    companion object {
        @Volatile
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    KrsDatabase::class.java,
                    "KrsDatabase"
                )
                    .build(). also { Instance = it }
            })
        }
    }
}