package com.example.mvvmkotlin.ui.base

import android.app.Application
import com.example.mvvmkotlin.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import java.security.SecureRandom

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Realm init
        Realm.init(this)

        // Generate a random encryption key
//        val key = ByteArray(64)
//        SecureRandom().nextBytes(key)

        val config = RealmConfiguration.Builder().name("Admin.realm").schemaVersion(1).deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
        // End Realm

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}