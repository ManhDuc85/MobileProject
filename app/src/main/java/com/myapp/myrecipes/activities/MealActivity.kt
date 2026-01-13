package com.myapp.myrecipes.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.myapp.myrecipes.R
import com.myapp.myrecipes.databinding.ActivityMealBinding
import com.myapp.myrecipes.dataclass.Meal
import com.myapp.myrecipes.fragments.HomeFragment
import com.myapp.myrecipes.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeBtnClicked()
    }

    private fun onYoutubeBtnClicked() {
        binding.youtubeIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this) { meal ->
            onResponseCase()
            binding.categoryTextview.text = "Category: ${meal.strCategory}"
            binding.areaTextview.text = "Area: ${meal.strArea}"
            binding.instructionsDetailTextview.text = meal.strInstructions
            youtubeLink = meal.strYoutube.toString()
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        val color = ContextCompat.getColor(this, R.color.white)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(color)
        binding.collapsingToolbar.setExpandedTitleColor(color)
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.instructionsTextview.visibility = View.INVISIBLE
        binding.categoryTextview.visibility = View.INVISIBLE
        binding.areaTextview.visibility = View.INVISIBLE
        binding.youtubeIcon.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.instructionsTextview.visibility = View.VISIBLE
        binding.categoryTextview.visibility = View.VISIBLE
        binding.areaTextview.visibility = View.VISIBLE
        binding.youtubeIcon.visibility = View.VISIBLE
    }
}