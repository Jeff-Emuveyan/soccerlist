package com.seamfix.maontestapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.databinding.MainFragmentBinding
import com.seamfix.maontestapplication.util.ID
import com.seamfix.maontestapplication.util.NAME
import com.seamfix.maontestapplication.util.UIState
import com.seamfix.maontestapplication.util.getProperColumnCount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    //Inject the view model
    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch{
            fetchCompetitions()
        }

        binding.retryButton.setOnClickListener {
            lifecycleScope.launch{
                fetchCompetitions()
            }
        }
    }

    private suspend fun fetchCompetitions() {
        setUpUIState(UIState.LOADING, null)

        val competitions = viewModel.getCompetitions()
        if(competitions != null && competitions.isNotEmpty()){
            setUpUIState(UIState.DATA_FOUND, competitions)
        }else{
            setUpUIState(UIState.NETWORK_ERROR, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpUIState(uiState: UIState, list: List<Competition>?) =
                                                            lifecycleScope.launch(Dispatchers.Main){
        when(uiState){
            UIState.LOADING ->{
                binding.recyclerView.visibility = View.GONE
                binding.shimmerView.visibility = View.VISIBLE
                binding.errorView.visibility = View.GONE
            }
            UIState.NETWORK_ERROR ->{
                binding.recyclerView.visibility = View.GONE
                binding.shimmerView.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
            }
            UIState.DATA_FOUND ->{
                binding.recyclerView.visibility = View.VISIBLE
                binding.shimmerView.visibility = View.GONE
                binding.errorView.visibility = View.GONE

                if(list != null) {
                    val gridLayoutManager = GridLayoutManager(context,
                        getProperColumnCount(requireActivity(), list.size))
                    gridLayoutManager.orientation = GridLayoutManager.VERTICAL
                    binding.recyclerView.layoutManager = gridLayoutManager
                    binding.recyclerView.setHasFixedSize(true)
                    val adapter = CompetitionsAdapter(requireContext(), list.reversed()) {
                        val bundle = Bundle()
                        bundle.putInt(ID, it.id)
                        bundle.putString(NAME, it.name)
                        findNavController().navigate(R.id.action_mainFragment_to_teamsFragment, bundle)
                    }
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }
}