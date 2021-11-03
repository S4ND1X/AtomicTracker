package mx.tec.atomictracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.tec.atomictracker.databinding.ActivityMapsBinding
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    var latitud by Delegates.notNull<Double>()
    var longitud by Delegates.notNull<Double>()
    lateinit var ubicacion : String
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMapsBinding.inflate(layoutInflater)
     setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        ubicacion = intent.getStringExtra("ubicacion").toString()
        latitud = 0.0
        longitud = 0.0
        if(ubicacion != "null" && ubicacion != "" ){
            val partes = ubicacion.split(',')
            latitud = partes[0].toDouble()
            longitud = partes[1].toDouble()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val location = LatLng(latitud, longitud)
        mMap.addMarker(MarkerOptions().position(location).title("Habit location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        mMap.setOnMapClickListener(this)
    }

    override fun onMapClick(latLng: LatLng) {
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("dynamic marker")
                .alpha(0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
        val intent = Intent()
        val lat = latLng.latitude
        val longitud = latLng.longitude
        intent.putExtra("result", "$lat,$longitud")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}