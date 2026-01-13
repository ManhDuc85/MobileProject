package com.myapp.myrecipes.db

import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverters
    fun fromAnyToString(attribute:Any?) : String{
        if(attribute == null)
            return ""
        return attribute as String
    }

    @TypeConverters
    fun fromStringToAny(attribute: String?) : Any{
        if(attribute == null)
            return ""
        return attribute
    }
}