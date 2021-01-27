package com.sina.covid19project.fragments.list_country

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.sina.covid19project.data.data_model.ResponseData
import com.sina.covid19project.databinding.CountryItemBinding
import com.sina.covid19project.utils_extentions.round


class CountryListAdapter(
    private val mContext: Context,
    private var listCountry: MutableList<ResponseData>?,
    private val listener: ListItemListener
) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listCountry?.get(position)
        holder.binding.apply {
            item?.let {
                val allDeath = it.deaths
                val allCaught = it.cases
                val percentage: Float =
                    (allDeath?.toFloat()?.div(allCaught?.toFloat()!!))?.times(100) ?: 0f
//                tvCountryNameListItem.text = it.country
                if(it.faName.isNullOrEmpty()){
                    tvCountryNameListItem.text=it.country
                }else{
                    tvCountryNameListItem.text=it.faName
                }
                tvDeathToAllCaughtPercentage.text = "%${percentage.round(3)}"
                tvDeathItem.text = it.deaths.toString()
                tvRecoveredItem.text = it.recovered.toString()
                tvPopulationItem.text = it.population.toString()
                tvAllCaught.text = it.cases.toString()
                //listener

                viewOverall.setOnClickListener {
                    listener.listenToCountryItem(
                        item.country ?: "iran"
                    )
                }
//                cardViewCountry.setOnLongClickListener {
//                    Toast.makeText(mContext ,"fdsfdsf", Toast.LENGTH_SHORT).show()
//                }
                val imageFlag = Glide.with(mContext).load(it.countryInfo?.flag).into(imgFlag)
                //how to send this drawable to next fragment so that no need to load it again?

            }

        }

    }

    override fun getItemCount(): Int {
        return listCountry?.size ?: 0
    }

    fun setList(newList:MutableList<ResponseData>){
        listCountry=newList
    }
    class MyViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    public interface ListItemListener{
        fun listenToCountryItem(country:String)
    }
}











