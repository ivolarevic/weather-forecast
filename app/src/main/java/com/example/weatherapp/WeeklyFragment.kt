package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.WeeklyAdapter
import com.example.weatherapp.utlis.LocationData
import com.example.weatherapp.databinding.FragmentWeeklyBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.model.DataWeeklyModel
import com.example.weatherapp.model.WeeklyListModel
import com.example.weatherapp.model.data.Forecast
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.viewmodels.WeeklyViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class WeeklyFragment : Fragment() {

    private lateinit var viewModel: WeeklyViewModel
    lateinit var binding: FragmentWeeklyBinding
    private var progressBar:ProgressBar ?= null
    private lateinit var customAdapter: WeeklyAdapter
    private var recyclerView: RecyclerView? = null
    private var weeklyList = ArrayList<Forecast>()

    private lateinit var model: ForecastApiCall

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWeeklyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WeeklyViewModel::class.java]
        recyclerView = view.findViewById(R.id.recyclerView)
        customAdapter = WeeklyAdapter(weeklyList)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = customAdapter
        progressBar = view.findViewById(R.id.progressBar2)

        model = ForecastApiCall(requireContext())
        setLiveDataListeners()
        viewModel.getWeatherInfo(model)
    }

    private fun setLiveDataListeners(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { forecastData ->
            setAdapterData(forecastData)
        })
    }

    private fun setAdapterData(data: Forecast){
        weeklyList.add(data)
        customAdapter.notifyDataSetChanged()
    }
}
