package com.example.androiddevfinalexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btLogout: Button = findViewById(R.id.btnLogout)
        btLogout.setOnClickListener{
            Firebase.auth.signOut()
            finish()
        }
    }
}