package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.model.DataDailyModel


class CustomAdapter(private var itemsList: List<DataDailyModel>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
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
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]

        holder.description.text = item.description.toString()
        holder.day.text = item.day.toString()
        holder.minMaxTempView.text = item.minTemp + "/" + item.maxTemp
        holder.rainView.text = item.rain.toString() + "mm"
        holder.humidityView.text = item.humidity.toString() + "%"
        holder.windView.text = item.wind.toString() + "m/s"
        holder.pressureView.text = item.pressure.toString() + "hPa"

        Glide.with(holder.iconView.context).load(item.icon).into(holder.iconView!!);

        holder.iconView!!.animate().apply{
            duration = 1000
            rotationYBy(360f)
        }.start()

        holder.dailyConstraint.setBackgroundResource(item.background)

    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}



