package mx.tec.atomictracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HabitListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HabitListFragment : Fragment(), View.OnClickListener {
    var habitos  = ArrayList<Home.habit>()
    lateinit var recyclerView: RecyclerView
    lateinit var habitFragment : HabitFragment

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_habit_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        habitFragment = HabitFragment()
        leerDatos()
        return view
    }

    public fun leerDatos(){
        Firebase.firestore.collection("habitos")
            .get().addOnSuccessListener {

                for(habit in it){
                    Log.d("FIRESTORE", "${habit.id} ${habit.data}")
                    var nombre = habit.get("Nombre").toString()
                    var descripcion = habit.get("Descripcion").toString()
                    var frecuencia = habit.get("Frecuencia").toString()
                    var inicio = habit.get("Inicio").toString()
                    var fin = habit.get("Fin").toString()
                    var currentHabit = Home.habit(nombre, descripcion, frecuencia, inicio, fin)
                    habitos.add(currentHabit)
                }

                val adapter = HabitAdapter(habitos, this)
                var llm = LinearLayoutManager(this.context)
                llm.orientation = LinearLayoutManager.VERTICAL
                recyclerView.layoutManager = llm
                recyclerView.adapter = adapter

            }.addOnFailureListener{
                Log.e("FIRESTORE", "Error al leer el habito:  ${it.message}")
            }
    }



    companion object{
        const val TAG_FRAGMENTO = "currentFragment"
    }

    override fun onClick(p0: View) {
        val position = recyclerView.getChildLayoutPosition(p0)
        val actual = habitos[position]
        //Toast.makeText(this.context, "OUCH! DON'T CLICK ME", Toast.LENGTH_SHORT).show()
        habitFragment = HabitFragment.newInstance(actual.nombre, actual.descripcion, actual.inicio, actual.fin, actual.frecuencia)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView5, habitFragment, TAG_FRAGMENTO)
        transaction.commit()
    }
}