package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.data.LocationData
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var locData: LocationData
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = binding.spinner
        locData = LocationData()

        ArrayAdapter.createFromResource(
            view.context,
            R.array.cities_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        fetchSpinnerData(spinner)
    }

    private fun fetchSpinnerData(spinner:Spinner){
        val cities = resources.getStringArray(R.array.cities_array)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                viewModel.fetchData((cities[position]))
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                viewModel.fetchData("Zagreb")
            }
        }
        updateUI()
    }

     private fun updateUI(){
        binding.searchHumidity.text = viewModel.humidity
        binding.searchName.text = viewModel.cityName
        binding.searchTemp.text = viewModel.temp
        binding.searchDescription.text = viewModel.description
        binding.searchMinTemp.text = viewModel.minTemp
        binding.searchMaxTemp.text = viewModel.maxTemp
        binding.searchPressure.text = viewModel.pressure
        binding.windSearch.text = viewModel.wind
        binding.visibilitySearch.text= viewModel.visibility

        binding.searchBackground.setBackgroundResource(locData.fetchBackground(viewModel.id))
        Glide.with(this).load(locData.fetchIcon(viewModel.id)).into(binding.searchIcon);

        locData.animateImage(binding.searchIcon)
    }
}
