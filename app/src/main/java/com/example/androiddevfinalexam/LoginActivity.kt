package com.example.androiddevfinalexam

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore

class LoginActivity : AppCompatActivity() {
    lateinit var nAuth: FirebaseAuth
    lateinit var etEmail:EditText
    lateinit var etPass:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        nAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore
        val uid = nAuth.currentUser?.uid


        var btnLogin:Button = findViewById(R.id.btnLogin)
        var btnRegister:Button = findViewById(R.id.btnRegister)

        etEmail = findViewById(R.id.emailET)
        etPass = findViewById(R.id.passwordET)

        btnLogin.setOnClickListener{
            var val_email = etEmail.text.toString()
            var val_pass = etPass.text.toString()

            //var userExist = false

//            val query = db.collection("users")
//                .whereEqualTo("email", val_email).whereEqualTo("password", val_pass)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        Log.d("ZZZZZZZZZ", "${document.id} => ${document.data}")
//                    }
//                    if(documents!=null){
//                        userExist = true
//                    }
//
//                    nAuth.createUserWithEmailAndPassword(val_email, val_pass).addOnCompleteListener(this){ task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(baseContext, "new user created", Toast.LENGTH_SHORT).show()
//                            updateUI(null)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("ZZZZZFirebase", "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(
//                                baseContext,
//                                "Authentication failed.",
//                                Toast.LENGTH_SHORT,
//                            ).show()
//                            updateUI(null)
//                        }
//                    }
//
//
//                }
//                .addOnFailureListener {
//                    Log.w("ZZZZZZZZZ", "Error getting documents: ")
//                }

            if(val_email.equals("")||val_pass.equals("")){
                Toast.makeText(this, "Required fields", Toast.LENGTH_SHORT)
            }else{
                signInEmailPass(val_email, val_pass)
            }
        }

        btnRegister.setOnClickListener{
            var intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = nAuth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    override fun onResume() {
        super.onResume()

        etEmail.setText("")
        etPass.setText("")
    }

    fun updateUI(user:FirebaseUser?){
        if(user!= null){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(baseContext, "not logged in", Toast.LENGTH_SHORT).show()
        }
    }
    fun signInEmailPass(email: String, passw:String){
        var TAG = "ZZZZZFirebase"
        nAuth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(this) { task->
            if (task.isSuccessful){
                Log.d(TAG, "Success signin")
                val user = nAuth.currentUser
                Toast.makeText(baseContext, "Authentication successful", Toast.LENGTH_SHORT).show()
                updateUI(user)
            }else{
                Log.d(TAG, "Not successful", task.exception)
                Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }


        }
    }
}