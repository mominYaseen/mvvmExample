package com.example.mvvmexample

class Repo {
    val listOfContact = mutableListOf<Contact>()

    fun getAllContact() = listOfContact

    fun addDataToContactList(contact: Contact) = listOfContact.add(contact)
}