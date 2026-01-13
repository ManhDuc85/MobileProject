package com.myapp.myrecipes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.myapp.myrecipes.activities.MainActivity
import com.myapp.myrecipes.adapters.FavouritesMealAdapter
import com.myapp.myrecipes.databinding.FragmentFavouriteBinding
import com.myapp.myrecipes.viewModel.HomeViewModel

class FavouriteFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteAdapter: FavouritesMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observeFavourites()
    }

    private fun prepareRecyclerView() {
        favouriteAdapter = FavouritesMealAdapter()
        binding.favouritesRecview.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL, false)
            adapter = favouriteAdapter
        }
    }

    private fun observeFavourites() {
        viewModel.observeFavouritesMealsLiveData().observe(requireActivity(), Observer{meals->
            favouriteAdapter.differ.submitList(meals)
        })
    }
}