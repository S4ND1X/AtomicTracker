package mx.tec.atomictracker

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter(private var habits: ArrayList<Home.habit>, private var listener: View.OnClickListener) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {


    class HabitViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var nombre : TextView = itemView.findViewById(R.id.cardName)
        var descripcion : TextView = itemView.findViewById(R.id.cardDescription)
        var frecuencia : TextView = itemView.findViewById(R.id.cardFrequency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.habitrow, parent, false)
        val dvh = HabitViewHolder(view)
        view.setOnClickListener(listener)
        return dvh
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {

        holder.nombre.text = habits[position].nombre
        holder.frecuencia.text = habits[position].frecuencia
        holder.descripcion.text = habits[position].descripcion
    }

    override fun getItemCount(): Int {

        return habits.size
    }

}