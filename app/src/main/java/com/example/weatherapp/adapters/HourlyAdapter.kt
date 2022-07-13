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
import com.example.weatherapp.model.data.Hourly
import com.example.weatherapp.utlis.LocationData
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter(private var itemsList: MutableList<Hourly>) : RecyclerView.Adapter<HourlyAdapter.MyViewHolder>() {
    private lateinit var itemView : View
    private val locData = LocationData()
    private val sdf = SimpleDateFormat("HH", Locale.getDefault())

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var hourlyDescription:TextView = view.findViewById(R.id.hourlyDescription)
        var hour:TextView = view.findViewById(R.id.hour)
        var iconHourly:ImageView = view.findViewById(R.id.weatherIconHourly)
        var tempHourly:TextView = view.findViewById(R.id.hourlyTemp)
        var constraint : ConstraintLayout = view.findViewById(R.id.hourlyConstraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.hourly_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        val id = item.weather[0].id

        holder.hour.text = itemView.context.getString(R.string.hourly_hour_placeholder, "${sdf.format(Timestamp(item.dt*1000))}:00h" )
        holder.tempHourly.text = itemView.context.getString(R.string.hourly_temp_placeholder, "${locData.kelvinToCelsius(item.temp).toString()}°C")
        holder.hourlyDescription.text = item.weather[0].description
        Glide.with(holder.iconHourly.context).load(locData.fetchIcon(id)).into(holder.iconHourly)
        locData.animateImage(holder.iconHourly)
        holder.constraint.setBackgroundResource(locData.fetchBackground(id))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}



