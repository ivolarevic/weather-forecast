package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.model.data.Daily
import com.example.weatherapp.utlis.LocationData
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class WeeklyAdapter(private var itemsList: MutableList<Daily>) : RecyclerView.Adapter<WeeklyAdapter.MyViewHolder>() {
    private lateinit var itemView : View
    private val locData = LocationData()
    private val sdf = SimpleDateFormat("EEEE", Locale.getDefault())


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dailyConstraint: ConstraintLayout = view.findViewById(R.id.dailyConstraint)
        var description: TextView = view.findViewById(R.id.weatherDaily)
        var day: TextView = view.findViewById(R.id.day)
        var iconView: ImageView = view.findViewById(R.id.weatherIconHourly)
        var minMaxTempView: TextView = view.findViewById(R.id.minMaxDaily)
        var rainView: TextView = view.findViewById(R.id.rainDaily)
        var humidityView: TextView = view.findViewById(R.id.humidityDaily)
        var windView: TextView = view.findViewById(R.id.windDaily)
        var pressureView: TextView = view.findViewById(R.id.pressureDaily)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.daily_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        val id = item.weather[0].id

        holder.description.text = item.weather[0].description
        holder.day.text = sdf.format(Timestamp(item.dt*1000))
        holder.minMaxTempView.text = "${locData.kelvinToCelsius(item.temp.min).toString()}°C / ${locData.kelvinToCelsius(item.temp.max).toString()}°C"
        holder.rainView.text = "${item.rain} mm"
        holder.humidityView.text =  "${item.humidity} %"
        holder.windView.text = "${item.wind_speed} m/s"
        holder.pressureView.text = "${item.pressure} hPa"

        Glide.with(holder.iconView.context).load(locData.fetchIcon(id)).into(holder.iconView)
        locData.animateImage(holder.iconView)
        holder.dailyConstraint.setBackgroundResource(locData.fetchBackground(id))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}



