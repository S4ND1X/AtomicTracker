package mx.tec.atomictracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class Profile : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    var amigos = ArrayList<amigo>()
    lateinit var primero : TextView
    lateinit var segundo : TextView
    lateinit var tercero : TextView
    lateinit var cuarto : TextView
    public lateinit var lista : TextView

    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        if(result.resultCode == Activity.RESULT_OK){

            val data: Intent? = result.data
            Toast.makeText(this," Friend added successfully", Toast.LENGTH_SHORT).show()
            lista.text = lista.text.toString() + data?.getStringExtra("result")

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        queue = Volley.newRequestQueue(this)
        primero = findViewById(R.id.primerPuesto)
        segundo = findViewById(R.id.segundoPuesto)
        tercero = findViewById(R.id.tercerPuesto)
        cuarto = findViewById(R.id.cuartoPuesto)
        lista = findViewById(R.id.texto_amigos)
        load()
    }


    fun load(){

        var nombre : String
        var puesto : String
        var correo : String

        var listaAmigos = ""
        var url = "https://raw.githubusercontent.com/MathewLG/AtomicTracker/master/leaderboard"
        Toast.makeText(this,"Se han cargado los datos", Toast.LENGTH_SHORT).show()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->

                for(i in 0 until response.length()) {

                    val actual = response.getJSONObject(i)
                    nombre = actual.getString("Nombre").toString()
                    puesto =actual.getString("Puesto").toString()
                    correo = actual.getString("Correo").toString()
                    listaAmigos ="$listaAmigos$nombre, "
                    var amigoActual = amigo(nombre, puesto, correo)
                    Log.wtf("AMIGO", "El amigo actual es: ${amigoActual.nombre}, su puesto es ${amigoActual.puesto}")
                    amigos.add(amigoActual)
                }
                primero.text = amigos[0].nombre
                segundo.text = amigos[1].nombre
                tercero.text = amigos[2].nombre
                cuarto.text = amigos[3].nombre
                lista.text = listaAmigos

            },
            { error ->

                Toast.makeText(this, "error: $error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    }

    fun addFriend(view: View?){
        val intent = Intent(this, AddFriend::class.java)
        activityResultLauncher.launch(intent)
    }

    class amigo(Nombre: String, Puesto: String, Correo: String){

        val nombre = Nombre
        val puesto = Puesto
        val correo = Correo
    }

}