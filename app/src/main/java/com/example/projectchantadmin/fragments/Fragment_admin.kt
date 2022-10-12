package com.example.projectchantadmin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectchantadmin.R
import com.example.projectchantadmin.entities.User
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase

class Fragment_admin : Fragment() {

    //val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var myUser = User("Bruno", "Rilla", 33, 12341231)
        //Linkear base de datos y guardar MyUser
        //db.collection("TestDataBase").add(myUser)
        Log.d("USER", myUser.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

}