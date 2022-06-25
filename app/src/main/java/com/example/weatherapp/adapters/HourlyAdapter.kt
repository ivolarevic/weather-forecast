package com.example.weatherapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.model.DataHourlyModel
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.utlis.LocationData
import java.sql.Timestamp
import java.text.SimpleDateFormat

class HourlyAdapter(private var itemsList: List<Forecast>) : RecyclerView.Adapter<HourlyAdapter.MyViewHolder>() {
    private val locData = LocationData()
    private val sdf = SimpleDateFormat("HH")

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var hourlyDescription:TextView = view.findViewById(R.id.hourlyDescription)
        var hour:TextView = view.findViewById(R.id.hour)
        var iconHourly:ImageView = view.findViewById(R.id.weatherIconHourly)
        var tempHourly:TextView = view.findViewById(R.id.hourlyTemp)
        var constraint : ConstraintLayout = view.findViewById(R.id.hourlyConstraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        val id = item.hourly[position].weather[0].id
        holder.hour.text = sdf.format(Timestamp(item.hourly[position].dt*1000)) + ":00h"
        holder.hourlyDescription.text = item.hourly[position].weather[0].description
        holder.tempHourly.text = locData.kelvinToCelsius(item.hourly[position].temp).toString() + "°C"
        Glide.with(holder.iconHourly.context).load(locData.fetchIcon(id)).into(holder.iconHourly);
        locData.animateImage(holder.iconHourly)
        holder.constraint.setBackgroundResource(locData.fetchBackground(id))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

}



