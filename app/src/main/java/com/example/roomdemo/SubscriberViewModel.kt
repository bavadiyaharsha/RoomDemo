package com.example.roomdemo

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private var repository: SubscriberRepository) : ViewModel() {
    var subscriber = repository.subscriber
    var isUpdateOrDelete = false
    lateinit var UpdateOrDeleteSubscriber: Subscriber
    val statusmsg = MutableLiveData<Event<String>>()

    val msg: LiveData<Event<String>>
        get() = statusmsg

    var personname = MutableLiveData<String>()

    var email = MutableLiveData<String>()

    var savebutton = MutableLiveData<String>()

    var clearbutton = MutableLiveData<String>()


    init {
        savebutton.value = "Save Data"
        clearbutton.value = "Clear Data"
    }

    fun saveORUpdate() {


        if (personname.value == null) {
            statusmsg.value = Event("Please Enter the Name")
        } else if (email.value == null) {
            statusmsg.value = Event("Please Enter the email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()) {
            statusmsg.value = Event("Please Enter the currect email")
        } else {
            if (isUpdateOrDelete) {
                UpdateOrDeleteSubscriber.name = personname.value!!
                UpdateOrDeleteSubscriber.email = email.value!!
                update(UpdateOrDeleteSubscriber)
            } else {
                val strname = personname.value!!
                val stremail = email.value!!
                insert(Subscriber(0, strname, stremail))

                personname.value = null
                email.value = null
            }
        }
    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            delete(UpdateOrDeleteSubscriber)
        } else {
            clearAll()

        }
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val newrow = repository.insert(subscriber)
        statusmsg.value = Event("Data Successfully Insert ${newrow}")

    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
        email.value = null
        personname.value = null
        isUpdateOrDelete = false
        savebutton.value = "Save"
        clearbutton.value = "DeleteAll"
        UpdateOrDeleteSubscriber = subscriber
        statusmsg.value = Event("Data Successfully Update")
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
        email.value = null
        personname.value = null
        isUpdateOrDelete = false
        savebutton.value = "Save"
        clearbutton.value = "DeleteAll"
        UpdateOrDeleteSubscriber = subscriber
        statusmsg.value = Event("Data Successfully Delete")

    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        email.value = subscriber.email
        personname.value = subscriber.name
        isUpdateOrDelete = true
        savebutton.value = "Update"
        clearbutton.value = "Delete"
        UpdateOrDeleteSubscriber = subscriber

    }


}