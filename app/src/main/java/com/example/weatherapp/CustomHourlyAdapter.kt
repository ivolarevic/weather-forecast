package com.example.weatherapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.model.DataDailyModel
import com.example.weatherapp.model.DataHourlyModel

class CustomHourlyAdapter(private var itemsList: List<DataHourlyModel>) : RecyclerView.Adapter<CustomHourlyAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var hourlyDescription:TextView = view.findViewById(R.id.hourlyDescription)
        var hour:TextView = view.findViewById(R.id.hour)
        var iconHourly:ImageView = view.findViewById(R.id.weatherIconHourly)
        var tempHourly:TextView = view.findViewById(R.id.hourlyTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.hour.text = item.hour
        holder.hourlyDescription.text = item.hourlyDescription
        holder.tempHourly.text = item.hourlyTemp
        Glide.with(holder.iconHourly.context).load(item.weatherIconDaily).into(holder.iconHourly!!);

        holder.iconHourly!!.animate().apply{
            duration = 1000
            rotationYBy(360f)
        }.start()


    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
}



