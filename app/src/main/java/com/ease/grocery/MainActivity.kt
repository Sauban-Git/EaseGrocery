package com.ease.grocery

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ease.grocery.data.local.PrefManager
import com.ease.grocery.data.repository.CartRepository
import com.ease.grocery.ui.auth.Login
import com.ease.grocery.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        CartRepository.init(applicationContext)

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        checkSession()
    }

    private fun checkSession() {

        Handler(Looper.getMainLooper()).postDelayed({

            val prefManager = PrefManager(this)

            if (prefManager.isLoggedIn()) {

                startActivity(
                    Intent(this, HomeActivity::class.java)
                )

            } else {

                startActivity(
                    Intent(this, Login::class.java)
                )
            }

            finish()

        }, 2000)
    }
}