package com.example.mvvmkotlin.ui.main.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.model.User
import com.example.mvvmkotlin.databinding.ActivityHomeDetailBinding
import com.example.mvvmkotlin.ui.main.viewmodel.HomeViewModel
import com.example.mvvmkotlin.utils.Constant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import timber.log.Timber
import java.util.*

class HomeDetail : FragmentActivity(), OnMapReadyCallback {

    private lateinit var context: Context
    private lateinit var homeViewModel: HomeViewModel // View Model

    private var user: User? = null

    private lateinit var homeDetailBinding: ActivityHomeDetailBinding

    // Google Map
    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeDetailBinding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(homeDetailBinding.root)

        user = intent.getSerializableExtra(Constant.OBJECT) as? User

        context = this@HomeDetail // Context
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java) // init ViewModel

        homeDetailBinding.tvTitle.text = user?.name ?: ""
        homeDetailBinding.tvEmail.text =
            resources.getString(R.string.email) + " " + user?.email
        homeDetailBinding.tvPhone.text =
            resources.getString(R.string.phone) + " " + user?.phone
        homeDetailBinding.tvUsername.text =
            resources.getString(R.string.username) + " " + user?.username
        homeDetailBinding.tvAddress.text = "Address :" + " " + user?.address?.suite + ", " + user?.address?.city + ", " + user?.address?.street + ", " + user?.address?.zipCode
        homeDetailBinding.tvWebSite.text =
            resources.getString(R.string.website) + " " + user?.website
        homeDetailBinding.tvCompany.text = "Company :" + " " + user?.company?.companyName + ", " + user?.company?.bs + ", " + user?.company?.catchPhrase

        homeDetailBinding.ivBack.setOnClickListener {
            this.finish()
        }

        // Google Map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        val latitude = user?.address?.geo?.lat?.toDouble()
        val longitude = user?.address?.geo?.lng?.toDouble()
        val zoomLevel = 15f

        val latLng = latitude?.let { longitude?.let { it1 -> LatLng(it, it1) } }
        val markerOptions = MarkerOptions()
        if (latLng != null) {
            markerOptions.position(latLng)
        }
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        map.addMarker(markerOptions)
        //move map camera
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel))

        // Timber.i("Result :: "+ latitude + " " + longitude + " " + latLng)
        enableMyLocation()
    }

    // Checks that users have given permission
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Checks if users have given their location and sets location enabled if so.
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    // Callback for the result from requesting permissions.
    // This method is invoked for every call on requestPermissions(android.app.Activity, String[],
    // int).
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}