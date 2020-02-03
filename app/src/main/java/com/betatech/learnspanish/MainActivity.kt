package com.betatech.learnspanish

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupToolbarWithNavigationComponent()
    }

    private fun setupToolbarWithNavigationComponent() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setupWithNavController(
            navController,
            appBarConfiguration
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.visibility =
                if (destination.id == R.id.quizFragment || destination.id == R.id.resultFragment) View.GONE else View.VISIBLE
        }
    }
}
