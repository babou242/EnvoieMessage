package com.example.envoiemessage

import android.app.Application
import com.example.envoiemessage.model.Contact
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class AppDatabase : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = RealmConfiguration.Builder(setOf(Contact::class))
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.open(config)
    }

}