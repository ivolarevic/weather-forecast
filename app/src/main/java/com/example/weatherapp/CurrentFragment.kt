package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentCurrentBinding
import com.example.weatherapp.repository.Forecast
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.repository.RetrofitService
import com.example.weatherapp.viewmodels.CurrentModelFactory
import com.example.weatherapp.viewmodels.CurrentViewModel

// Default fragment for city of Zagreb
class CurrentFragment : Fragment() {

    private lateinit var viewModel: CurrentViewModel
    private lateinit var binding: FragmentCurrentBinding
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, CurrentModelFactory(mainRepository))[CurrentViewModel::class.java]

        viewModel.getWeather()
        viewModel.weatherList.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })

    }

     private fun updateUI(list: List<Forecast>){
        var weather = mutableListOf<Forecast>()
        weather = list.toMutableList()
        binding.temp.text = weather[0].current.temp.toString()
        binding.cityName.text = "Zagreb"
        binding.humidity.text = weather[0].current.humidity.toString()
    }


}