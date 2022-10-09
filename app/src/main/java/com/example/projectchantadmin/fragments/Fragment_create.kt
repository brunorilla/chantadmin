package com.example.projectchantadmin.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectchantadmin.R


class Fragment_create : Fragment() {

    lateinit var myView: View
    lateinit var data: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_create, container, false)

        //val data = savedInstanceState?.getString("textFromActivityA")

        //Log.d("Ver parametro recibido: ", data!!)

        return myView
    }

}