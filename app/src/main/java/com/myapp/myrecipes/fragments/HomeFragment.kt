package com.myapp.myrecipes.fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.myapp.myrecipes.R
import com.myapp.myrecipes.databinding.FragmentHomeBinding
import com.myapp.myrecipes.dataclass.Meal
import com.myapp.myrecipes.dataclass.MealList
import com.myapp.myrecipes.retrofit.RetrofitInstance
import com.myapp.myrecipes.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        observerRandomMeal()

    }

    private fun observerRandomMeal(){
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.randomFoodImg)
        }
    }

}