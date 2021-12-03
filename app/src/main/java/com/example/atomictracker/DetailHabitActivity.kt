package com.example.atomictracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atomictracker.databinding.ActivityDetailHabitBinding
import com.example.atomictracker.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class DetailHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setValues()
    }

    private fun setValues(){
        binding.nameDetailTextView.text = intent.getStringExtra("nombre")
        binding.frequencyDetailTextView.text = intent.getStringExtra("frecuencia")
        binding.notificationDetailTextView.text = intent.getStringExtra("notificacion")
        binding.hourDetailTextView.text = intent.getStringExtra("hora")
        binding.dateDetailTextView.text = intent.getStringExtra("inicio")
    }
}