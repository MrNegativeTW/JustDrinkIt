package com.txwstudio.drinkitclient.ui.activity.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.messaging.FirebaseMessaging
import com.txwstudio.drinkitclient.firebase.FirestoreManager
import com.txwstudio.drinkitclient.model.BottleStatus

class MainActivityViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainActivityViewModel"
        private val docRef = FirestoreManager().listeningForRealtimeBottleStatus()
    }

    var isBottleExists = MutableLiveData<Boolean>(false)
    val bottleStatusDocument = MutableLiveData<BottleStatus>()
    var firebaseToken = MutableLiveData<String>()
    lateinit var registration: ListenerRegistration

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            firebaseToken.value = task.result
            Log.d(TAG, "Here is the fucking token $token")
        })
    }

    fun test() {
        Log.i(TAG, "Adding the fucking listener, here we goooooooooo")
        registration = docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
                snapshot.data?.get("isBottleExists").let {
                    isBottleExists.value = it as Boolean?
                }

            } else {
                Log.d(TAG, "Current data: null")
            }
        }

    }

    fun remove() {
        Log.i(TAG, "Remove the fucking listener, here we goooooooooo")
        registration.remove()
    }

}