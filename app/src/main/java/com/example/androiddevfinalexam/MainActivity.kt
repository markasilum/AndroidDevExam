package com.example.androiddevfinalexam

import CardAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
     private val db = Firebase.firestore
     lateinit var recyclerView: RecyclerView
     var prodArrayList = ArrayList<CardData>()
     lateinit var myAdapter: CardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btLogout: Button = findViewById(R.id.btnLogout)



        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        prodArrayList = arrayListOf()

        myAdapter = CardAdapter(prodArrayList)

        recyclerView.adapter = myAdapter

        EventChangeLister()


        btLogout.setOnClickListener{
            Firebase.auth.signOut()
            finish()
        }
    }

    fun EventChangeLister(){
        db.collection("items").
        addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
               if(error!= null){
                   Log.d("Firestore Error", error.message.toString())
                   return
               }

                for(dc:DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        prodArrayList.add(dc.document.toObject(CardData::class.java))
//                        Log.d("Firestore Error", "$prodArrayList")
                    }
                }

                myAdapter.notifyDataSetChanged()
            }
        })
    }
}