package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.HourlyAdapter
import com.example.weatherapp.databinding.FragmentHourlyBinding
import com.example.weatherapp.model.DataDailyModel
import com.example.weatherapp.model.DataHourlyModel
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.model.data.Hourly
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.viewmodels.HourlyViewModel
import com.example.weatherapp.viewmodels.WeeklyViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat

class HourlyFragment : Fragment() {

    private lateinit var customAdapter: HourlyAdapter
    private var recyclerView: RecyclerView? = null
    private var hourlyList : MutableList<Hourly> = mutableListOf()
    private lateinit var model: ForecastApiCall

    private lateinit var viewModel: HourlyViewModel
    private lateinit var binding: FragmentHourlyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHourlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.hourlyRecyclerView
        customAdapter = HourlyAdapter(hourlyList)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = customAdapter
        model = ForecastApiCall(requireContext())
        viewModel = ViewModelProvider(this)[HourlyViewModel::class.java]
        setLiveDataListeners()
        viewModel.getWeatherInfo(model)
    }

    private fun setLiveDataListeners(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { forecastData ->
            setAdapterInfo(forecastData)
        })
    }

    private fun setAdapterInfo(data: List<Hourly>){
        hourlyList.addAll(data)
        customAdapter.notifyDataSetChanged()
    }
}