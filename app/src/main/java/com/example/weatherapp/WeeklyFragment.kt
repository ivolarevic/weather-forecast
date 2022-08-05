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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.WeeklyAdapter
import com.example.weatherapp.databinding.FragmentWeeklyBinding
import com.example.weatherapp.model.data.Daily
import com.example.weatherapp.network.ForecastApiCall
import com.example.weatherapp.network.NetworkResult
import com.example.weatherapp.viewmodels.WeeklyViewModel

class WeeklyFragment : Fragment() {

    private lateinit var viewModel: WeeklyViewModel
    private lateinit var binding: FragmentWeeklyBinding
    private lateinit var progressBar:ProgressBar
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var customAdapter: WeeklyAdapter
    private var recyclerView: RecyclerView? = null
    private var weeklyList : MutableList<Daily> = mutableListOf()
    private lateinit var model: ForecastApiCall

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        progressBar = binding.progressBarWeekly
        constraintLayout = binding.weeklyConstraint
        model = ForecastApiCall()
        setLiveDataListeners()
        viewModel.getWeatherInfo(model)
    }

    private fun setLiveDataListeners(){
        viewModel.response.observe(viewLifecycleOwner){response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("success", response.data.toString())
                    viewModel.weatherLiveData.observe(viewLifecycleOwner) { forecastData ->
                        setAdapterData(forecastData)
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

    private fun setAdapterData(data: List<Daily>){
        updateProgressBar()
        weeklyList.addAll(data)
        customAdapter.notifyDataSetChanged()
    }

    private fun updateProgressBar(){
        progressBar.visibility = View.GONE
        constraintLayout.visibility = View.VISIBLE
    }
}
