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
    private var listCountry: MutableList<ResponseData>
) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=listCountry[position]
        val allDeath=item.deaths
        val allCaught=item.cases
        val percentage:Float=(allDeath.toFloat()/allCaught.toFloat())* 100
        holder.binding.tvAllCaught.text=allCaught.toString()
        holder.binding.tvAllDeathItem.text=allDeath.toString()
//        holder.binding.tvCaughtTodayItem.text=item.todayCases.toString()
        holder.binding.tvCountryNameListItem.text=item.country
//        holder.binding.tvDeathTodayItem.text=item.todayDeaths.toString()
        Glide.with(mContext).load(item.countryInfo.flag).into(holder.binding.imgFlag)
        holder.binding.tvDeathToAllCaughtPercentage.text="%${percentage.round(3)}"
    }

    override fun getItemCount(): Int {
        return listCountry.size
    }
    fun setList(list: MutableList<ResponseData>) {
        listCountry=list
    }

    class MyViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

fun Float.round(i: Int): Float {
    return toBigDecimal().setScale(i, RoundingMode.UP).toFloat()
}









