package mx.tec.atomictracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

class Home : AppCompatActivity(), View.OnClickListener {
    lateinit var mail : String
    var habitID : Int = 0
    lateinit var habitListFragment : HabitListFragment
    lateinit var habitsFragment : HabitFragment
    //lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        habitListFragment = HabitListFragment()
        habitsFragment = HabitFragment()

        mail = intent.extras?.getString("mail").toString()
        Log.wtf("PRUEBA", mail)

    }


    public fun addHabit(view : View?){
        val intent = Intent(this, CreateHabit::class.java)
        startActivity(intent)
    }

    public fun profile(view : View?){
        val intent = Intent(this, Profile::class.java)
        //Agregar el ID del usuario para cargarlo desde ahi
        startActivity(intent)
    }

    public fun detalles(view : View?){
        val intent = Intent(this, Detalle::class.java)
        intent.putExtra("id", habitID)
        startActivity(intent)
    }

    class habit(Nombre: String, Descripcion: String, Frecuencia: String, Inicio: String, Fin: String){

        var nombre = Nombre
        var descripcion = Descripcion
        var frecuencia = Frecuencia
        var inicio = Inicio
        var fin = Fin
    }

    override fun onClick(p0: View?) {
        //oast.makeText(this, "OUCH! DON'T CLICK ME", Toast.LENGTH_SHORT).show()

        //val transaction = supportFragmentManager.beginTransaction()

        //transaction.add(R.id.fragmentContainerView, habitsFragment, TAG_FRAGMENTO)
        //transaction.commit()
    }


    companion object{
        private const val TAG_FRAGMENTO = "currentFragment"
    }
}