package com.sina.covid19project.data

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sina.covid19project.databinding.CountryItemBinding
import java.math.RoundingMode

class CountryListAdapter(
    private val mContext: Context,
    private var listCountry: MutableList<ResponseData>?
) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= listCountry?.get(position)
        holder.binding.apply {
            item?.let {
                val allDeath= it.deaths
                val allCaught= it.cases
                val percentage: Float = (allDeath?.toFloat()?.div(allCaught?.toFloat()!!))?.times(100) ?:0f
                tvCountryNameListItem.text=it.country
                tvDeathToAllCaughtPercentage.text = "%${percentage.round(3)}"
                tvDeathItem.text=it.deaths.toString()
                tvRecoveredItem.text=it.recovered.toString()
                tvPopulationItem.text=it.population.toString()
                tvAllCaught.text=it.cases.toString()
            }
        }
        Glide.with(mContext).load(item?.countryInfo?.flag).into(holder.binding.imgFlag)
    }

    override fun getItemCount(): Int {
        return listCountry?.size?:0
    }

    fun setList(newList: MutableList<ResponseData>) {
        listCountry=newList
    }


    class MyViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

fun Float.round(i: Int): Float {
    return toBigDecimal().setScale(i, RoundingMode.UP).toFloat()
}









