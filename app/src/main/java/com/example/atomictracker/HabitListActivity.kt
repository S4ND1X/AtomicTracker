package com.example.atomictracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atomictracker.databinding.ActivityHabitListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var binding: ActivityHabitListBinding


class HabitListActivity : AppCompatActivity() {


    private val database = Firebase.database;
    val myRef = database.getReference("Users")

    private lateinit var habitRecyclerView: RecyclerView
    private lateinit var habitArrayList: ArrayList<HabitDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Activity Activity
        binding = ActivityHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitRecyclerView = binding.habitList
        habitRecyclerView.layoutManager = LinearLayoutManager(this)
        habitRecyclerView.setHasFixedSize(true)

        habitArrayList = arrayListOf<HabitDTO>()

        getHabitsData()


    }

    private fun getHabitsData() {
        // Get username before @ on the email
        val user: String = Firebase.auth.currentUser?.email?.split('@')?.get(0).toString()

        myRef.child(user).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (habitSnapshot in snapshot.children) {
                        val habit = habitSnapshot.getValue(HabitDTO::class.java)
                        val habitID = habitSnapshot.key
                        if (habit != null && habitID != null) {

                            habit.id = habitID

                        }


                        habitArrayList.add(habit!!)
                    }

                    habitRecyclerView.adapter = MyAdapter(habitArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}