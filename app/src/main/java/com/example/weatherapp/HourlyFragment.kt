package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.HourlyAdapter
import com.example.weatherapp.databinding.FragmentHourlyBinding
import com.example.weatherapp.model.data.Hourly
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.network.NetworkResult
import com.example.weatherapp.viewmodels.HourlyViewModel

class HourlyFragment : Fragment() {

    private lateinit var customAdapter: HourlyAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var constraintLayout: ConstraintLayout
    private var hourlyList : MutableList<Hourly> = mutableListOf()
    private lateinit var model: ForecastApiCall

    private lateinit var viewModel: HourlyViewModel
    private lateinit var binding: FragmentHourlyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHourlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.hourlyRecyclerView
        customAdapter = HourlyAdapter(hourlyList)
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = customAdapter
        progressBar = binding.progressBarHourly
        constraintLayout = binding.hourlyConstraint
        model = ForecastApiCall()
        viewModel = ViewModelProvider(this)[HourlyViewModel::class.java]
        setLiveDataListeners()
        viewModel.getWeatherInfo(model)
    }

    private fun setLiveDataListeners(){
        viewModel.response.observe(viewLifecycleOwner){response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("success", response.data.toString())
                    viewModel.weatherLiveData.observe(viewLifecycleOwner) { forecastData ->
                        setAdapterInfo(forecastData)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setAdapterInfo(data: List<Hourly>){
        updateProgressBar()
        hourlyList.addAll(data)
        customAdapter.notifyDataSetChanged()
    }
    private fun updateProgressBar(){
        progressBar.visibility = View.GONE
        constraintLayout.visibility = View.VISIBLE
    }
}