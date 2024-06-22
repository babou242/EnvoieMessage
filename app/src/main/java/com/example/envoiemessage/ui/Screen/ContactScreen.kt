package com.example.envoiemessage.ui.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.envoiemessage.Screen
import com.example.envoiemessage.model.Contact
import com.example.envoiemessage.ui.viewmodel.ContactViewModel

@Composable
fun ContactScreen(navController: NavController){
    val viewModel : ContactViewModel = viewModel()
    val contacts by viewModel.contacts.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
        }
    ) {
        Column {
            ContactList(contacts, viewModel::deleteContact, Modifier.padding(it))
            if (showDialog) {
                AddContactDialog(onDismiss = {
                    showDialog = false
                }) { firstName, lastName, phoneNumber ->
                    viewModel.addContact(firstName, lastName, phoneNumber)
                    showDialog = false
                }
            }
            Spacer(modifier=Modifier.weight(1f))
            Button(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                Text("Send")
            }
            Spacer(modifier=Modifier.padding(16.dp))
        }
    }
}
@Composable
fun ContactList(contacts: List<Contact>, onDelete: (Contact) -> Unit,modifier : Modifier = Modifier) {
    Column (modifier = modifier){
        contacts.forEach { contact ->
            ContactItem(contact, onDelete)
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onDelete: (Contact) -> Unit) {
    Surface(
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Text(
                        text = contact.firstName,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                    Spacer(modifier = Modifier.padding(4.dp),)
                    Text(
                        text = contact.lastName,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                }
                Text(text = contact.phoneNumber)
            }
            IconButton(onClick = { onDelete(contact) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Contact")
            }
        }
    }
}

@Composable
fun AddContactDialog(onDismiss: () -> Unit, onAdd: (String, String, String) -> Unit){
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Contact") },
        text = {
            Column {
                TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
                TextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
                TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
            }
        },
        confirmButton = {
            Button(onClick = { onAdd(firstName, lastName, phoneNumber) }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
