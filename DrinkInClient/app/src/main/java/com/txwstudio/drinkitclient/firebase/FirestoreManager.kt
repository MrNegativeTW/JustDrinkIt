package com.txwstudio.drinkitclient.firebase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreManager {

    private val db = Firebase.firestore

    fun listeningForRealtimeBottleStatus(): DocumentReference {
        return db.collection("bottleStatus").document("7HQ26j7APOAghhMip5xH")
    }
}