package com.sina.covid19project.fragments.list_country

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.covid19project.data.data_model.ResponseData
import com.sina.covid19project.repository.ListRepository
import com.sina.covid19project.utils_extentions.ListState
import com.sina.covid19project.utils_extentions.round
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(val mContext: Context, private val listRepo: ListRepository) : ViewModel() {
    companion object {
        const val TAG = "ListViewModel"
    }

    var _mList: MutableList<ResponseData>? = null
    var listContainerWhole: MutableList<ResponseData>? = null


    private val _listState = MutableLiveData<ListState>()
    val listState: LiveData<ListState>
        get() = _listState
    var _CountryNameClicked = MutableLiveData("")
    val countryNameClicked: LiveData<String>
        get() = _CountryNameClicked

    //we need a country variable for detail fragment because this view model is shared
    lateinit var curCountry: ResponseData

    init {
        getListApi()


    }

    fun getListApi() {
        Log.e(TAG, "getListApi: starting request")
        _listState.postValue(ListState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(TAG, "getListApi: in coroutine scope before request")
            try {
                listContainerWhole = listRepo.fetchList()
//                Log.e(TAG, "getListApi: $listContainerWhole", )
                //remove zero population countries
                listContainerWhole =
                    listContainerWhole!!.filter { it.population != 0 && !it.faName.isNullOrEmpty() } as MutableList<ResponseData>
                //set percentage field for every country
                for (item in listContainerWhole!!) {
                    item.percentage =
                        ((item.deaths?.toFloat())?.div((item.cases?.toFloat()!!)))?.times(100)
                            ?.round(3)
                }
                listContainerWhole!!.sortByDescending { it.cases }
                _mList = listContainerWhole
                _listState.postValue(ListState.SUCCESSFULLY_LOADED)
                Log.e(TAG, "try to get list: list is successfully fetched")
            } catch (e: Exception) {
                Log.e(TAG, "getListApi: loading failed")
                _listState.postValue(ListState.LOADING_FAILED)
            }
        }

    }


    fun onSortWithPopulation() {
        _mList?.sortByDescending { it.population }
    }

    fun onSortWithCases() {
        _mList?.sortByDescending { it.cases }
    }

    fun onSortWithZtoA() {
        _mList?.sortByDescending { item -> item.country }
    }

    fun onSortWithPercentage() {
        _mList?.sortByDescending { it.percentage }
        for (i in 1..50) {
            Log.e(TAG, "onSortWithPercentage: ${_mList?.get(i)?.percentage}")
        }
    }

    fun onSearch(txtSearch: String) {
        _mList =
            listContainerWhole?.filter { it.country?.contains(txtSearch, true) ?: true }
                    as MutableList<ResponseData>
        if(_mList.isNullOrEmpty()){
            //جستجوی نام به فارسی
            _mList=listContainerWhole?.filter {
                it.faName?.contains(txtSearch, true) ?: true
            } as MutableList<ResponseData>
        }
        Log.e(TAG, "onSearch: listContainerWhole size ${listContainerWhole?.size}")
        Log.e(TAG, "onSearch: _mList size ${_mList?.size}")
    }


    fun searchCountryByName(countryName: String): Boolean {
        //return true if searched was successfull and country saved in curCountry
        for (country in _mList!!) {
            country.country.equals(countryName).let {
                if (it) {
                    curCountry = country
                    return true
                }
            }
        }
        return false
    }

    fun getPercentageStr(cases: Int?, population: Int?): CharSequence {
        if (cases != null && population != null) {

            val percent = (cases.toFloat()) / (population.toFloat()) * 100

            return "${percent.round(3)} %"
        }
        return ""
    }
}