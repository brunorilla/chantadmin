package com.example.projectchantadmin.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectchantadmin.R
import com.example.projectchantadmin.utils.UserSession
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable


class GoogleSignInActivity : AppCompatActivity(), Serializable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        //setupBottomNavigationView()

    }

    private fun setupBottomNavigationView() {
        // Busco los componentes en la View generada por su id
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val bottomNavViewAdmin = findViewById<BottomNavigationView>(R.id.bottom_nav_view_admin)
        val bottomNavViewCustomer =
            findViewById<BottomNavigationView>(R.id.bottom_nav_view_customer)

        // Relaciono mi Bottom Nav View con mi nav graph
        bottomNavViewAdmin.setupWithNavController(navHostFragment.navController)
        bottomNavViewCustomer.setupWithNavController(navHostFragment.navController)

        if (UserSession.rol.equals("admin")) {
            bottomNavViewAdmin.visibility = View.VISIBLE
        } else {
            bottomNavViewCustomer.visibility = View.VISIBLE
        }


    }


}