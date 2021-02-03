package com.sina.covid19project.fragments.splash


import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.sina.covid19project.R


class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                checkInternetPermission()
            }, 2000
        )

    }

    companion object {
        const val INTERNET_PERMISSION_CODE = 100
        const val TAG = "SplashFragment"
    }

    private fun checkInternetPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.INTERNET
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "checkInternetPermission: permission is granted before")
            goToHomeFragment()
        } else {
            Log.e(TAG, "checkInternetPermission: permission have to be requested")
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.INTERNET),
            INTERNET_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToHomeFragment()
            } else {
                Log.e(TAG, "onRequestPermissionsResult: request for permission again", )
                requestPermission()
            }
        }
    }
    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
}