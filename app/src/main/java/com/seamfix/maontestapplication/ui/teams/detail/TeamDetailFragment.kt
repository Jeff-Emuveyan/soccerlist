package com.seamfix.maontestapplication.ui.teams.detail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
import com.seamfix.maontestapplication.R
import com.seamfix.maontestapplication.data.model.entity.TeamDetail
import com.seamfix.maontestapplication.databinding.TeamDetailFragmentBinding
import com.seamfix.maontestapplication.ui.teams.TeamsAdapter
import com.seamfix.maontestapplication.util.ID
import com.seamfix.maontestapplication.util.NAME
import com.seamfix.maontestapplication.util.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.club_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamDetailFragment : Fragment() {

    //Inject the view model
    private val viewModel: TeamDetailViewModel by viewModels()

    private var _binding: TeamDetailFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TeamDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycleScope.launch {
            fetchTeamDetails()
        }

        binding.retryButton.setOnClickListener {
            lifecycleScope.launch {
                fetchTeamDetails()
            }
        }

        //get the  ID and competition name
        binding.tvCompetitionName.text = arguments?.getString(NAME)
    }

    private suspend fun fetchTeamDetails() {
        setUpUIState(UIState.LOADING, null)
        val id = arguments?.getInt(ID)
        if(id != null){
            val teamDetails = viewModel.getTeamDetail(id)
            if(teamDetails != null){
                setUpUIState(UIState.DATA_FOUND, teamDetails)
            }else{
                setUpUIState(UIState.NETWORK_ERROR, null)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setUpUIState(uiState: UIState, teamDetail: TeamDetail?) = lifecycleScope.launch(Dispatchers.Main){

        when(uiState){
            UIState.LOADING  ->{
                binding.shimmerView.visibility = View.VISIBLE
                binding.errorView.visibility = View.GONE
                binding.nestedScrollView.visibility = View.GONE
            }
            UIState.NETWORK_ERROR ->{
                binding.shimmerView.visibility = View.GONE
                binding.errorView.visibility = View.VISIBLE
                binding.nestedScrollView.visibility = View.GONE
            }
            UIState.DATA_FOUND ->{
                binding.shimmerView.visibility = View.GONE
                binding.errorView.visibility = View.GONE
                binding.nestedScrollView.visibility = View.VISIBLE
                tvFounded.text = teamDetail?.founded.toString()
                tvNickName.text = teamDetail?.shortName
                tvAddress.text = teamDetail?.address
                tvPhone.text = teamDetail?.phone
                tvWeb.text = teamDetail?.website
                tvEmail.text = teamDetail?.email

                //populate the data on the recyclerView:
                val layoutManager = LinearLayoutManager(requireContext())
                layoutManager.orientation = LinearLayoutManager.VERTICAL;
                binding.recyclerView.layoutManager = layoutManager
                val adapter = PlayerAdapter(requireContext(), teamDetail)
                binding.recyclerView.setHasFixedSize(true)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()

                try {
                    GlideToVectorYou.init()
                            .with(requireContext())
                            .setPlaceHolder(R.drawable.ic_baseline_image_24, R.drawable.ic_broken_image)
                            .withListener(object: GlideToVectorYouListener {
                                override fun onLoadFailed() {
                                    binding.shimmer.stopShimmer()
                                    binding.shimmer.hideShimmer()
                                    binding.imageView.setImageResource(R.drawable.ic_broken_image)
                                }

                                override fun onResourceReady() {
                                    binding.shimmer.stopShimmer()
                                    binding.shimmer.hideShimmer()
                                }
                            })
                            .load(Uri.parse(teamDetail?.crestUrl), binding.imageView)
                } catch (e: Exception) {
                    binding.shimmer.stopShimmer()
                    binding.shimmer.hideShimmer()
                    binding.imageView.setImageResource(R.drawable.ic_broken_image)
                    Log.e(TeamsAdapter::class.simpleName, "Error from Glide")
                }
            }
        }
    }

}