package com.example.androiddevfinalexam

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore

class RegistrationActivity : AppCompatActivity() {
    lateinit var nAuth: FirebaseAuth
    lateinit var btnRegAndLog: Button
    lateinit var displayNameET: EditText
    lateinit var etRegEmail: EditText
    lateinit var etRegNewPass: EditText
    lateinit var etRegConPass: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnRegAndLog = findViewById(R.id.regAndLogin)
        displayNameET = findViewById(R.id.displayNameET)
        etRegEmail = findViewById(R.id.etRegEmail)
        etRegNewPass = findViewById(R.id.etRegNewPass)
        etRegConPass = findViewById(R.id.etRegConPass)

        val db = Firebase.firestore

        btnRegAndLog.setOnClickListener{
            nAuth = FirebaseAuth.getInstance()

            var nameVal = displayNameET.text.toString()
            var newEmailVal = etRegEmail.text.toString()
            var newPassVal = etRegNewPass.text.toString()
            var conPassVal = etRegConPass.text.toString()

            val user = hashMapOf(
                "email" to newEmailVal,
                "name" to nameVal,
                "password" to newPassVal
            )

            if(newPassVal == conPassVal){
                Log.d("ZZZZZFirebase", "Password matched")
                Log.d("ZZZZZFirebase", "$user")
                Log.d("ZZZZZFirebase", "$db")

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }.addOnFailureListener{e ->
                    Log.w(TAG, "Error adding document", e)}
                //Toast.makeText(baseContext, "Password matched", Toast.LENGTH_SHORT).show()

                nAuth.createUserWithEmailAndPassword(newEmailVal, newPassVal).addOnCompleteListener(this){ task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, "new user created", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("ZZZZZFirebase", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }

            }else {
                Toast.makeText(baseContext, "Password does not match", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "New pass: $newPassVal")
                Log.d(TAG, "Con pass: $conPassVal")


            }








            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun updateUI(user: FirebaseUser?){
        if(user!= null){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(baseContext, "not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}