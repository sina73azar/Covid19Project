package com.sina.covid19project.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.sina.covid19project.R
import com.sina.covid19project.data.ApiClient
import com.sina.covid19project.data.ApiInterface
import com.sina.covid19project.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    companion object{
        const val TAG="HomeFragment"
    }
    lateinit var binding: FragmentHomeBinding
     var cases:String="0"
     var deaths:String=""
     var recoverd:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLiveCovid19Data()

        binding.tvByCountry.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
        }
    }


    private fun getLiveCovid19Data() {
        Toast.makeText(requireContext(), "getting live Data", Toast.LENGTH_SHORT).show()
        val api = ApiClient.client.create(ApiInterface::class.java)
        val call = api.getMainMessage()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        cases = it["cases"].asString
                        deaths = it["deaths"].asString
                        recoverd=it["recovered"].asString

                    }

                }

                binding.tvShowCases.text=cases
                binding.tvShowDeath.text=deaths
                binding.tvShowRecovered.text=recoverd
                //set last update
                binding.tvDate.text= Date().time.reformat()

//                Log.e(TAG, "onResponse: is response successful: ${response.isSuccessful}",)
                Log.e(TAG, "onResponse: header cases: ${response.headers().get("cases")}",)

                Log.e(TAG, "cases: $cases",)
                Log.e(TAG, "deaths: $deaths",)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e(TAG, "onFailure: $t",)
            }

        })


    }
    @SuppressLint("SimpleDateFormat")
    fun Long.reformat(): String {
        val df= SimpleDateFormat("(yyyy/MM/dd - HH:mm:ss)")
        return df.format(this)
    }
}