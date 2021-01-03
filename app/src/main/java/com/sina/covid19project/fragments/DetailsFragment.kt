package com.sina.covid19project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.sina.covid19project.R
import com.sina.covid19project.data.CountryInfo
import com.sina.covid19project.data.ApiClient
import com.sina.covid19project.data.ApiInterface
import com.sina.covid19project.data.ResponseData
import com.sina.covid19project.databinding.FragmentDetailsBinding
import com.sina.covid19project.databinding.TableRowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    lateinit var bindingRowLayout: TableRowBinding
    lateinit var mList: MutableList<ResponseData> 
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //accessing tablelayout via binding.tableLayout
        mList= mutableListOf()
        bindingRowLayout=TableRowBinding.inflate(layoutInflater)
        loadData()





    }

    companion object {
        const val TAG = "DetailFragment"
    }

    private fun loadData() {
        
        val api = ApiClient.client.create(ApiInterface::class.java)
        val call = api.getAllCountries()
        call.enqueue(object : Callback<MutableList<ResponseData>> {
            override fun onResponse(
                call: Call<MutableList<ResponseData>>,
                response: Response<MutableList<ResponseData>>
            ) {

                Log.e(TAG, "onResponse: response is successful->${response.isSuccessful}")
                if (response.isSuccessful) {

                    for (i in 0 until response.body()?.size!!) {
                        response.body()?.get(i)?.let {
                            mList.add(it)
                        }
                    }

                    //add row to table
                    for (responseData in mList) {
                        bindingRowLayout=TableRowBinding.inflate(layoutInflater)
                        val mRow=bindingRowLayout.tableRow
                        bindingRowLayout.let {
                            Glide.with(requireContext()).load(responseData.countryInfo.flag).into(it.tvCountryFlag)
                            it.tvCountryName.text = responseData.country
                            it.tvAll.text = responseData.cases.toString()
                            it.tvDeath.text = responseData.deaths.toString()
                            it.tvDeathToday.text=responseData.todayDeaths.toString()
                            it.tvOneCasePerPeople.text=responseData.casesPerOneMillion.toString()
                            it.tvPopulation.text=responseData.population.toString()
                            it.tvRecoveredColumn.text=responseData.recovered.toString()
                            it.tvRecoveredToday.text=responseData.todayRecovered.toString()
                            it.tvTodayCaught.text=responseData.todayCases.toString()
                        }
                        binding.tableLayout.addView(mRow)
                    }
//                    Log.e(TAG, "onResponse: $mList",)
                }

            }

            override fun onFailure(call: Call<MutableList<ResponseData>>, t: Throwable) {
                Log.e(TAG, "onFailure: is called...throwable is $t")

            }
        })
    }

}

