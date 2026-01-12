package com.myapp.myrecipes.fragments

import android.content.Intent
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
import com.myapp.myrecipes.activities.MealActivity
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
    private lateinit var randomMeal: Meal

    companion object{
        const val MEAL_ID = "com.myapp.myrecipes.fragments.idMeal"
        const val MEAL_NAME = "com.myapp.myrecipes.fragments.nameMeal"
        const val MEAL_THUMB = "com.myapp.myrecipes.fragments.thumbMeal"
    }

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
        onRandomMealClick()
    }

    fun onRandomMealClick(){
        binding.randomFoodCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal(){
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.randomFoodImg)

                this.randomMeal = meal
        }
    }

}