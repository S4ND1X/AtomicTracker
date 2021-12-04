package com.example.atomictracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyAdapter(private val habitList: ArrayList<HabitDTO>, private val padre: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val database = Firebase.database;
    private val myRef = database.getReference("Users")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = habitList[position]

        val date: String =
            currentItem.day.toString() + "/" + currentItem.month.toString() + "/" + currentItem.year.toString()
        val time: String =
            currentItem.hour.toString() + ":" + currentItem.minute

        holder.name.text = currentItem.name
        holder.frequency.text = currentItem.frequency
        holder.notification.text = if (currentItem.notification) "Activadas" else "Desactivadas"
        holder.start.text = date
        holder.hour.text = time
        holder.video.text = currentItem.url
        holder.delete.setOnClickListener {
            val user: String = Firebase.auth.currentUser?.email?.split('@')?.get(0).toString()
            myRef.child(user).child(currentItem.id).removeValue()
        }
        holder.detail.setOnClickListener{
            val intent = Intent(padre, DetailHabitActivity::class.java)
            intent.putExtra("nombre",currentItem.name)
            intent.putExtra("frecuencia",currentItem.frequency)
            intent.putExtra("notificacion",if (currentItem.notification) "Activadas" else "Desactivadas")
            intent.putExtra("inicio",date)
            intent.putExtra("hora",time)
            intent.putExtra("video", currentItem.url)
            padre.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name_card)
        val frequency: TextView = itemView.findViewById(R.id.frequency_card)
        val notification: TextView = itemView.findViewById(R.id.notification_card)
        val start: TextView = itemView.findViewById(R.id.start_card)
        val hour: TextView = itemView.findViewById(R.id.hour_card)
        val delete: Button = itemView.findViewById(R.id.delete_card)
        val detail: Button = itemView.findViewById(R.id.detail_card)
        val video: TextView = itemView.findViewById(R.id.video_card)
    }

}