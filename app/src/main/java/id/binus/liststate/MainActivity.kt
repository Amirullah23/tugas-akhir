package id.binus.liststate

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.binus.liststate.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainActivity : AppCompatActivity(), StateDataAdapter.OnItemClickListener  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        geocoder = Geocoder(this)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)




        NetworkConfig().getService()
            .getStateData()
            .enqueue(object : Callback<StateData> {
                override fun onFailure(call: Call<StateData>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<StateData>,
                    response: Response<StateData>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val adapter = data?.let { StateDataAdapter(it.data, this@MainActivity,this@MainActivity) }
                        recyclerView.adapter = adapter
                        println(data)

                    }
                }

            })
    }

    override fun onItemClick(stateData: ResultDt) {
        val location = geocoder.getFromLocationName(stateData.State, 1)
        if (location!!.isNotEmpty()) {
            val stateLocation = LatLng(location[0].latitude, location[0].longitude)

            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra(MapActivity.EXTRA_STATE_NAME, stateData.State)
                putExtra(MapActivity.EXTRA_LATITUDE, stateLocation.latitude)
                putExtra(MapActivity.EXTRA_LONGITUDE, stateLocation.longitude)
            }

            startActivity(intent)
        } else {
            Toast.makeText(this, "Unable to find coordinates for ${stateData.State}", Toast.LENGTH_SHORT).show()
        }

    }


}