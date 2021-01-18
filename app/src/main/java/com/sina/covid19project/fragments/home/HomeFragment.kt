package com.sina.covid19project.fragments.home


import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.pd.chocobar.ChocoBar
import com.sina.covid19project.R
import com.sina.covid19project.data.network.ApiClient
import com.sina.covid19project.data.network.ApiInterface
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
    lateinit var mSharedPref:SharedPreferences
    lateinit var binding: FragmentHomeBinding
     var cases:String?=null
     var deaths:String?=null
     var recovered:String?=null
     var todayCases:String?=null
     var todayDeath:String?=null
     var todayRecovered:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSharedPref=requireActivity().getPreferences(MODE_PRIVATE)
        getLiveCovid19Data()
        //broadcast reciever when internet is on
//        binding.imgRefreshHf.setOnClickListener {
//            getLiveCovid19Data()
//        }
        binding.tvByCountry.visibility=View.VISIBLE
        binding.tvByCountry.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
    }


    private fun getLiveCovid19Data() {
//        Toast.makeText(requireContext(), "بروزرسانی ...", Toast.LENGTH_SHORT).show()
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
                        recovered = it["recovered"].asString
                        todayCases = it["todayCases"].asString
                        todayDeath = it["todayDeaths"].asString
                        todayRecovered = it["todayRecovered"].asString

                        writeDataToSharedPref()
                        setTakenDataToView()
                        setLastUpdatedTime()
                        hideAlertNoInternet()
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                setDatawithSharedPref()
                setTakenDataToView()
                binding.tvDate.text =
                    mSharedPref.getString("date", getString(R.string.internet_error))
                binding.tvAlertNoInternet.visibility = View.VISIBLE
//                binding.viewBigToolbar.background=ContextCompat.getDrawable(requireContext(),R.drawable.ic_pandemic__converted_redbg)
                showSnackBarRed()
                Log.e(TAG, "onFailure: ${t.message}",)
            }

        })
    }

    private fun hideAlertNoInternet() {
        binding.tvAlertNoInternet.visibility=View.GONE
    }

    fun showSnackBarRed() {
        ChocoBar.builder().setActivity(requireActivity())
            .setDuration(ChocoBar.LENGTH_LONG)
            .setText("اینترنت متصل نیست")
            .red()
            .show()
    }

    private fun writeDataToSharedPref() {
        mSharedPref.edit().apply {
            putString("cases",cases)
            putString("today_cases",todayCases)
            putString("deaths",deaths)
            putString("today_deaths",todayDeath)
            putString("recovered",recovered)
            putString("today_recovered",todayRecovered)
        }.apply()
    }

    private fun setLastUpdatedTime() {
        val date=Date().time.reformat()
        binding.tvDate.text= date
        showSnackBarGreen(date)
        //save date in sharedPref
        mSharedPref.edit().putString("date",date).apply()

    }

    private fun showSnackBarGreen(date:String) {
        ChocoBar.builder().setActivity(requireActivity())
            .setDuration(ChocoBar.LENGTH_SHORT)
            .setText(date)
            .green()
            .show()
    }

    private fun setDatawithSharedPref() {
        cases=mSharedPref.getString("cases","...")
        todayCases=mSharedPref.getString("today_cases","...")
        deaths=mSharedPref.getString("deaths","...")
        todayDeath=mSharedPref.getString("today_deaths","...")
        recovered=mSharedPref.getString("recovered","...")
        todayRecovered=mSharedPref.getString("today_recovered","...")
    }


    private fun setTakenDataToView() {
        binding.apply {
            tvShowCases.text=cases
            tvCasesTodayHf.text=todayCases
            tvDeathAll.text=deaths
            tvDeathToday.text=todayDeath
            tvShowRecovered.text=recovered
            tvRecoveredToday.text=todayRecovered
//            viewBigToolbar.background=ContextCompat.getDrawable(requireContext(),R.drawable.ic_pandemic__converted_)

        }
    }


    @SuppressLint("SimpleDateFormat")
    fun Long.reformat(): String {
        val df= SimpleDateFormat("(yyyy/MM/dd - HH:mm:ss)")
        return df.format(this)
    }
}