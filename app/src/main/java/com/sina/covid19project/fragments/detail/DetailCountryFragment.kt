package com.sina.covid19project.fragments.detail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.sina.covid19project.databinding.FragmentDetailCountryBinding
import com.sina.covid19project.fragments.list_country.ListViewModel
import com.sina.covid19project.utils_extentions.getFormattedAmount
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailCountryFragment : Fragment() {
    companion object {
        private const val TAG = "DetailCountryFragment"
    }

    private val viewModelShared: ListViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailCountryBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get country name from list fragment
        val countryName =
            DetailCountryFragmentArgs.fromBundle(requireArguments()).countryName
//        binding.tvCountryName.text = countryName
        getCountryData(countryName).let {
            if (it) {
                bindDataToView()
            }
        }
    }

    private fun getCountryData(countryName: String): Boolean {
        return viewModelShared.searchCountryByName(countryName)
        //now we can use curCountry in viewModel because it s been set
    }
    private fun bindDataToView() {
        viewModelShared.curCountry.let { curCountry ->
            val flagUrl = curCountry.countryInfo?.flag
            flagUrl?.let { loadImageFlag(flagUrl) }
            binding.apply {
                binding.tvCountryName.text = curCountry.faName
                binding.tvCountryNameEn.text=curCountry.country
                tvContinent.text = curCountry.faContinent
                tvPopulation.text = curCountry.population?.let { getFormattedAmount(it) }
                //setting mavared Ebtela
                tvAll4.text = curCountry.cases?.let { getFormattedAmount(it) }
                tvTodayCases5.text = curCountry.todayCases?.let { getFormattedAmount(it) }
                tvCritical7.text = curCountry.critical?.let { getFormattedAmount(it) }
                tvCasesPerPopData.text =
                    viewModelShared.getPercentageStr(curCountry.cases, curCountry.population)
                tvOnePerPeopleData.text = curCountry.oneCasePerPeople.toString()
                tvOnePerMillion5.text = curCountry.casesPerOneMillion?.toInt().toString()
                //setting test registered
                tvTestAll2.text = curCountry.tests?.let { getFormattedAmount(it) }
                tvPerToPopulationData2.text =
                    viewModelShared.getPercentageStr(curCountry.tests, curCountry.population)
                tvOnePerPeopleTest.text = curCountry.oneTestPerPeople?.toInt().toString()
                tvOnePerMillionTest.text = curCountry.testsPerOneMillion?.toInt().toString()

                //setting death
                tvAllDeathData.text = curCountry.deaths?.let { getFormattedAmount(it) }
                tvDeathTodayDfData.text = curCountry.todayDeaths?.let { getFormattedAmount(it) }
                tvOnePerPeopleDeathData.text = curCountry.oneDeathPerPeople?.toInt().toString()
                tvOnePerMillionDeathData.text = curCountry.deathsPerOneMillion?.toInt().toString()
                tvPercentDeathPerPopulationData.text =
                    viewModelShared.getPercentageStr(curCountry.deaths, curCountry.population)
                //setting recovered
                tvRecoveredAllData.text = curCountry.recovered?.let { getFormattedAmount(it) }
                tvRecoveredTodayData.text = curCountry.todayRecovered?.let { getFormattedAmount(it) }
                tvOnePerMillionRecoveredData.text =
                    curCountry.recoveredPerOneMillion?.toInt().toString()
            }


        }

    }

    private fun loadImageFlag(url: String) {
        Glide.with(requireContext()).load(url).into(binding.imgFlag)
    }

//    private fun addOffset() {
//        binding.appBarLayout.addOnOffsetChangedListener(
//            AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
//                Log.e(TAG, "onOffsetChanged: $verticalOffset",)
//
//            })
//    }
}