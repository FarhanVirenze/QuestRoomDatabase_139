package com.pam.questroomdatabase_139.dependenciesinjection

import android.content.Context
import com.pam.questroomdatabase_139.data.database.KrsDatabase
import com.pam.questroomdatabase_139.repository.LocalRepositoryMhs
import com.pam.questroomdatabase_139.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}