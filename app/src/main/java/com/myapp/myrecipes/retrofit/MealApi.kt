package com.myapp.myrecipes.retrofit

import com.myapp.myrecipes.dataclass.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}