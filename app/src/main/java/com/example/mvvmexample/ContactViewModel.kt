package com.example.mvvmexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel(
    private val repo: Repo
) :ViewModel(){
    val listOfContactLiveData = MutableLiveData<List<Contact>>()

    init {
        getAllData()
    }
    fun getAllData(){
        val allContact = repo.getAllContact()
        listOfContactLiveData.value = allContact
    }

    fun addContact(contact: Contact){
        repo.addDataToContactList(contact)
    }

}