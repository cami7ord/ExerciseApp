package com.example.exerciseapp.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exerciseapp.R
import com.example.exerciseapp.databinding.FragmentOverviewBinding
import com.example.exerciseapp.ui.viewmodel.ExerciseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val viewModel: ExerciseViewModel by viewModel()

    private lateinit var binding: FragmentOverviewBinding
    private lateinit var listAdapter: OverviewListAdapter
    private lateinit var swipeRefreshContainer: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOverviewBinding.bind(view)

        listAdapter = OverviewListAdapter(
            onFavoriteClicked = { exercise ->
                viewModel.toggleFavoriteExercise(exercise)
            }
        )

        with(binding.listExercises) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        swipeRefreshContainer = binding.swipeContainer
        swipeRefreshContainer.setOnRefreshListener {
            swipeRefreshContainer.isRefreshing = true
            fetchExercises()
        }

        binding.btnStartTraining.setOnClickListener {
            findNavController().navigate(
                R.id.action_overviewFragment_to_exerciseFragment
            )
        }

        if (savedInstanceState == null) {
            swipeRefreshContainer.isRefreshing = true
            fetchExercises()
        }
        observeViewModel()
    }

    private fun fetchExercises() {
        viewModel.loadExercises()
    }

    private fun observeViewModel() {
        viewModel.exercises.observe(viewLifecycleOwner) {
            listAdapter.updateItems(it)
            swipeRefreshContainer.isRefreshing = false
        }
    }
}
