package com.seamfix.maontestapplication.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.databinding.TeamsFragmentBinding
import com.seamfix.maontestapplication.util.ID
import com.seamfix.maontestapplication.util.NAME
import com.seamfix.maontestapplication.util.UIState
import com.seamfix.maontestapplication.util.getProperColumnCount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamsFragment : Fragment() {

    //Inject the  view model
    private val viewModel: TeamsViewModel by  viewModels()

    private var _binding: TeamsFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = TeamsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpUIState(UIState.LOADING, null)

        //get the  ID and competition name
        binding.tvCompetitionName.text = arguments?.getString(NAME)

        lifecycleScope.launch(Dispatchers.IO){
            val id = arguments?.getInt(ID)
            fetchTeams(id)
        }
    }

    private suspend fun fetchTeams(competitionId: Int?) {
        if(competitionId == null) return

        val teams = viewModel.getTeams(competitionId)
        if(teams  != null){
            setUpUIState(UIState.DATA_FOUND, teams)
        }else{
            setUpUIState(UIState.NETWORK_ERROR, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setUpUIState(uiState: UIState, list: List<Team>?) =
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
                        getProperColumnCount(requireActivity(), list.size)
                    )
                    gridLayoutManager.orientation = GridLayoutManager.VERTICAL
                    binding.recyclerView.layoutManager = gridLayoutManager
                    binding.recyclerView.setHasFixedSize(true)
                    val adapter = TeamsAdapter(requireContext(), list) {
                        val bundle = Bundle()
                        bundle.putInt(ID, it.id)
                        bundle.putString(NAME, it.name)
                        findNavController().navigate(R.id.action_teamsFragment_to_teamDetailFragment, bundle)
                    }
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }
}