package com.example.envoiemessage.ui.viewmodel

import android.app.Application
import android.telephony.SmsManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.envoiemessage.model.Contact
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SMSViewModel(application: Application):AndroidViewModel(application) {

    private val realm : Realm
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    init {
        val config = RealmConfiguration.Builder(setOf(Contact::class))
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.open(config)
        loadContacts()
    }
    private fun loadContacts() {
        viewModelScope.launch {
            val contactsFromDb = realm.query<Contact>().find()
            _contacts.value = contactsFromDb
        }
    }
    fun getAllPhoneNumbers(): List<String> {
        return contacts.value.map { it.phoneNumber }
    }

    fun sendSMS(phoneNumbers : List<String>,message:String){
        phoneNumbers.forEach {phoneNumber ->
            SmsManager.getDefault().sendTextMessage(phoneNumber,null,message,null,null)
        }


    }

}