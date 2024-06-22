package com.example.envoiemessage.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.envoiemessage.AppDatabase
import com.example.envoiemessage.model.Contact
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
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

    fun addContact(firstName: String, lastName: String, phoneNumber: String) {
        viewModelScope.launch {
            realm.write {
                copyToRealm(Contact().apply {
                    this.firstName = firstName
                    this.lastName = lastName
                    this.phoneNumber = phoneNumber
                })
            }
            loadContacts()
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            realm.write {
                val contactToDelete = query<Contact>("_id == $0", contact._id).first().find()
                if (contactToDelete != null) {
                    delete(contactToDelete)
                }
            }
            loadContacts()
        }
    }
}