package com.myapp.myrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.myapp.myrecipes.databinding.CategoryItemsBinding
import com.myapp.myrecipes.dataclass.Category
import com.myapp.myrecipes.dataclass.CategoryList
import com.myapp.myrecipes.dataclass.MealsByCategoryList

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClicked:((Category) -> Unit)? = null

    fun setCategoryList(categoriesList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemsBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.categoryImage)
        holder.binding.categoryNameTextview.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClicked!!.invoke((categoriesList[position]))
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class CategoryViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)
}