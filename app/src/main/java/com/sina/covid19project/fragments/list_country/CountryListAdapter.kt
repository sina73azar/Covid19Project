package com.sina.covid19project.fragments.list_country


import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import com.sina.covid19project.data.data_model.ResponseData
import com.sina.covid19project.databinding.CountryItemBinding
import com.sina.covid19project.utils_extentions.getFormattedAmount
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
                if (it.faName.isNullOrEmpty()) {
                    tvCountryNameListItem.text = it.country
                } else {
                    tvCountryNameListItem.text = it.faName
                }
                tvDeathToAllCaughtPercentage.text =  "${percentage.round(3)}%"
                tvDeathItem.text = it.deaths?.let { it1 -> getFormattedAmount(it1) }
                tvRecoveredItem.text = it.recovered?.let { it1 -> getFormattedAmount(it1) }
                tvPopulationItem.text = it.population?.let { it1 -> getFormattedAmount(it1) }
                tvAllCaught.text = it.cases?.let { it1 -> getFormattedAmount(it1) }
                //listener

                viewOverall.setOnClickListener {
                    listener.listenToCountryItem(
                        item.country ?: "iran"
                    )
                }
                imgFlag.setOnClickListener {
                    listener.listenToCountryItem(
                        item.country ?: "iran"
                    )
                }
                viewBackRightCardview.setOnClickListener {
                    listener.listenToCountryItem(
                        item.country ?: "iran"
                    )
                }
                Glide.with(mContext).load(it.countryInfo?.flag).into(imgFlag)
            }

        }

    }

    override fun getItemCount(): Int {
        return listCountry?.size ?: 0
    }

    fun setList(newList: MutableList<ResponseData>) {
        listCountry = newList
    }

    class MyViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    public interface ListItemListener {
        fun listenToCountryItem(country: String)
    }
}











