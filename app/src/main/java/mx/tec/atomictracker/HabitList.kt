package mx.tec.atomictracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HabitList : AppCompatActivity() {


    val database = Firebase.database;
    val myRef = database.getReference("Users")

    private lateinit var habitRecyclerView: RecyclerView
    private lateinit var habitArrayList: ArrayList<HabitDTO>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)

        habitRecyclerView = findViewById(R.id.habitList)
        habitRecyclerView.layoutManager = LinearLayoutManager(this)
        habitRecyclerView.setHasFixedSize(true)

        habitArrayList =  arrayListOf<HabitDTO>()

        getHabitData()

    }

    private fun getHabitData() {
        val email = intent.extras?.getString("email").toString()
        val user = email.split('@')[0]


        //Check all data inside this user
        myRef.child(user).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for (habitSnapshot in snapshot.children){
                        val habit = habitSnapshot.getValue(HabitDTO::class.java)
                        val habitID = habitSnapshot.key
                        if (habit != null) {
                            if (habitID != null) {
                                habit.id = habitID
                            }
                        }

                        habitArrayList.add(habit!!)
                    }

                    habitRecyclerView.adapter = MyAdapter(habitArrayList, baseContext)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(habitRecyclerView.context, error.toString(), Toast.LENGTH_LONG)
            }

        })

    }
}