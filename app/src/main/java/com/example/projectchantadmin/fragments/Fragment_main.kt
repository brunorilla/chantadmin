package com.example.projectchantadmin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectchantadmin.R
import com.example.projectchantadmin.entities.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Fragment_main : Fragment() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var myUser = User("Nombre", "Apellido", 33, User.customer)
        //Linkear base de datos y guardar MyUser
        db.collection("TestDataBase").add(myUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}