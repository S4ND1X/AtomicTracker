package mx.tec.atomictracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val habitList : ArrayList<HabitDTO>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


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



    }

}