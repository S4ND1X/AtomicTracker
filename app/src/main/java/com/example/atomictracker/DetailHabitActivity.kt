package com.example.atomictracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class DetailHabitActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_habit)
        setValues()
    }

    private fun setValues(){
        val nombre = findViewById<TextView>(R.id.nameDetailTextView)
        val frecuencia = findViewById<TextView>(R.id.frequencyDetailTextView)
        val notificacion = findViewById<TextView>(R.id.notificationDetailTextView)
        val hora = findViewById<TextView>(R.id.hourDetailTextView)
        val fecha = findViewById<TextView>(R.id.dateDetailTextView)

        nombre.text = intent.getStringExtra("nombre")
        frecuencia.text = intent.getStringExtra("frecuencia")
        notificacion.text = intent.getStringExtra("notificacion")
        hora.text = intent.getStringExtra("hora")
        fecha.text = intent.getStringExtra("inicio")
    }
}