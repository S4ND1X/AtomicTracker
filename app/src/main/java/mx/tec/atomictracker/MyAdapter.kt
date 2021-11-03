package mx.tec.atomictracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyAdapter(private val habitList : ArrayList<HabitDTO>, private val context:Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val database = Firebase.database;
    val myRef = database.getReference("Users")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return  MyViewHolder(itemView)
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = habitList[position]

        holder.hName.text = currentItem.nombre
        holder.hDescripcion.text = currentItem.descripcion
        holder.hFrecuencia.text = currentItem.frecuencia
        holder.hInicio.text = currentItem.inicio
        holder.hFin.text = currentItem.fin
        holder.hRecordar.text = currentItem.recordar.toString()
        holder.hMaps.setOnClickListener {
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtra("Latitud", currentItem.latitud)
            intent.putExtra("Longitud", currentItem.longitud)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        holder.hEliminar.setOnClickListener {
            val user = email.split('@')[0]
            myRef.child(user).child(currentItem.id).removeValue()
        }

    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val hName : TextView = itemView.findViewById(R.id.hName)
        val hDescripcion : TextView = itemView.findViewById(R.id.hDescripcion)
        val hFrecuencia : TextView = itemView.findViewById(R.id.hFrecuencia)
        val hInicio : TextView = itemView.findViewById(R.id.hInicio)
        val hFin : TextView = itemView.findViewById(R.id.hFin)
        val hRecordar : TextView = itemView.findViewById(R.id.hRecordar)
        val hMaps : Button = itemView.findViewById(R.id.hMaps)
        val hEliminar : Button = itemView.findViewById(R.id.hEliminar)

    }

}