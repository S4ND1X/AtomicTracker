package mx.tec.atomictracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val ARG_NOMBRE = "nombre"
private const val ARG_DESCRIPCION = "descripcion"
private const val ARG_FRECUENCIA = "frecuencia"
private const val ARG_INICIO = "inicio"
private const val ARG_FIN = "fin"

class HabitFragment : Fragment(), View.OnClickListener{

    lateinit var nombreText : TextView
    lateinit var descripcionText: TextView
    lateinit var frecuenciaText: TextView
    lateinit var inicioText: TextView
    lateinit var finText: TextView
    lateinit var deleteButton: Button
    lateinit var closeButton: Button
    lateinit var editButton: Button
    var ubicacion = ""
    var currid = "1"
    private var nombre : String? = null
    private var descripcion : String? = null
    private var frecuencia : String? = null
    private var inicio : String? = null
    private var fin : String? = null


    lateinit var habitListFragment : HabitListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nombre = it.getString(ARG_NOMBRE)
            descripcion = it.getString(ARG_DESCRIPCION)
            frecuencia = it.getString(ARG_FRECUENCIA)
            inicio = it.getString(ARG_INICIO)
            fin = it.getString(ARG_FIN)
        }
    }

    fun editHabit(){
        val intent = Intent(this.context, EditarHabito::class.java)
        currid = location()
        intent.putExtra("ubicacion", ubicacion)
        intent.putExtra("id", currid)
        //Toast.makeText(this.context, "Current id from habit fragment is $currid", Toast.LENGTH_SHORT).show()


        startActivity(intent)
    }

    fun location(): String {
        var currid = ""
        Firebase.firestore.collection("habitos").get().addOnSuccessListener {
            for(habito in it){
                Log.e("FIREBASE", " texto: ${habito.id} ${habito.data}")
                if(habito.data.containsValue(nombreText.text)){
                    currid = habito.id
                    //Toast.makeText(this.context, habito.id, Toast.LENGTH_SHORT).show()
                    ubicacion = habito.get("Ubicacion").toString()
                    break
                }
            }
        }.addOnFailureListener{
            Log.e("FIRESTORE", "Problema encontrando el documento")
        }
        return currid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_habit, container, false)
        habitListFragment = HabitListFragment()

        nombreText = view.findViewById(R.id.nameDetail)
        descripcionText = view.findViewById(R.id.descriptionDetail)
        frecuenciaText = view.findViewById(R.id.frecuenciaDetail)
        inicioText = view.findViewById(R.id.inicioDetail)
        finText = view.findViewById(R.id.finDetail)


        nombreText.text = nombre
        descripcionText.text = descripcion
        frecuenciaText.text = frecuencia
        inicioText.text = inicio
        finText.text = fin
        deleteButton = view.findViewById(R.id.deleteHabitButton)
        deleteButton.setOnClickListener { this.deleteHabit() }
        closeButton = view.findViewById(R.id.closeButton)
        closeButton.setOnClickListener { this.exit() }
        editButton = view.findViewById(R.id.editButton)
        editButton.setOnClickListener {this.editHabit() }
        return view
    }
    fun deleteHelper(id: String){
        Firebase.firestore.collection("habitos").document(id).delete().addOnSuccessListener {
            Log.wtf("TEST", "tratando de eliminar habito")
            Toast.makeText(this.context, "Hábito eliminado con éxito", Toast.LENGTH_SHORT).show()
            exit()

        }.addOnFailureListener{
            Log.e("FIRESTORE", "Error al borrar el habito:  ${it.message}")
        }
    }
    fun deleteHabit(){
        var currid : String
        currid = ""
        Firebase.firestore.collection("habitos").get().addOnSuccessListener {
            for(habito in it){
                Log.e("FIREBASE", " texto: ${habito.id} ${habito.data}")
                if(habito.data.containsValue(nombreText.text)){
                    currid = habito.id
                    Log.e("ELEMENTO A ELIMINAR:", currid)
                    deleteHelper(currid)
                }
            }
        }.addOnFailureListener{
            Log.e("FIRESTORE", "Problema encontrando el documento")
        }

    }
    companion object {
        private const val TAG_FRAGMENTO = "currentFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(nombre: String, descripcion: String, inicio: String, fin: String, frecuencia: String) =
            HabitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NOMBRE, nombre)
                    putString(ARG_DESCRIPCION, descripcion)
                    putString(ARG_INICIO, inicio)
                    putString(ARG_FIN, fin)
                    putString(ARG_FRECUENCIA, frecuencia)
                }
            }
    }
    fun exit() {
        //Toast.makeText(this.context, "OUCH! DON'T CLICK ME", Toast.LENGTH_SHORT).show()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView5, habitListFragment, TAG_FRAGMENTO)
        transaction.commit()
    }

    override fun onClick(p0: View?) {
        deleteHabit()
    }


}