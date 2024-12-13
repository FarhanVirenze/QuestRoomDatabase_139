package com.pam.questroomdatabase_139

import android.app.Application
import com.pam.questroomdatabase_139.dependenciesinjection.ContainerApp

class KrsApp : Application() {

    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        containerApp = ContainerApp(this)
    }
}