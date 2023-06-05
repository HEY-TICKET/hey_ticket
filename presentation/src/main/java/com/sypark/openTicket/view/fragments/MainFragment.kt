package com.sypark.openTicket.view.fragments

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.model.MainViewModel
import com.sypark.openTicket.view.adapter.GenreAdapter
import com.sypark.openTicket.view.adapter.MainDefaultAdapter
import com.sypark.openTicket.view.adapter.RankingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val TAG = "MainFragment"

    private val viewModel: MainViewModel by viewModels()

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var newFilterAdapter: GenreAdapter

    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var newTicketAdapter: MainDefaultAdapter
    private lateinit var etcTicketAdapter: MainDefaultAdapter
    private lateinit var campusTicketAdapter: MainDefaultAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init(view: View) {
        binding.layoutBottom.navigationBottom.menu.getItem(0).isChecked = true

        binding.topTitle.imgSearch.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
        }

        binding.layoutRecommand.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFirstFragment())
        }

        binding.recyclerviewRankingFilter.apply {
            genreAdapter = GenreAdapter { position, item ->
                rankingFilterItemClicked(position)

                lifecycleScope.launch {
                    viewModel.getRankingData(item.code)
                }
            }

            genreAdapter.submitList(Common.genreList)
            adapter = genreAdapter
            genreAdapter.setSelectedPosition(0)
        }

        binding.recyclerviewRanking.apply {
            rankingAdapter = RankingAdapter {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToTicketDetailFragment(
                        it
                    )
                )
            }

            lifecycleScope.launch {
                viewModel.getRankingData("")
            }

            adapter = rankingAdapter
        }

        viewModel.rankingList.observe(viewLifecycleOwner) {
            rankingAdapter.submitList(it)
        }

        viewModel.isShimmerLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.shimmer.hideShimmer()
            } else {
                binding.shimmer.showShimmer(true)
            }
        }

        binding.recyclerviewNewTicketFilter.apply {
            newFilterAdapter = GenreAdapter { position, item ->
                Log.e("newFilterAdapter", item.toString())
                newFilterItemClicked(position)
                lifecycleScope.launch {
                    viewModel.getNewTicketData(item.code)
                }
            }

            newFilterAdapter.submitList(Common.genreList)
            adapter = newFilterAdapter
            newFilterAdapter.setSelectedPosition(0)
        }

        binding.recyclerviewNewTicket.apply {
            newTicketAdapter = MainDefaultAdapter {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToTicketDetailFragment(
                        it
                    )
                )
            }

            lifecycleScope.launch {
                viewModel.getNewTicketData("")
            }

            adapter = newTicketAdapter
        }

        viewModel.newTicketList.observe(this) {
            newTicketAdapter.submitList(it)
        }

        binding.recyclerviewCampusTicket.apply {
            campusTicketAdapter = MainDefaultAdapter {

            }
//            campusTicketAdapter.submitList()
            adapter = campusTicketAdapter
        }

        binding.recyclerviewEtcTicket.apply {
            etcTicketAdapter = MainDefaultAdapter {

            }
//            etcTicketAdapter.submitList()
            adapter = etcTicketAdapter
        }
    }

    private fun rankingFilterItemClicked(position: Int) {
        genreAdapter.setSelectedPosition(position)
    }

    private fun newFilterItemClicked(position: Int) {
        newFilterAdapter.setSelectedPosition(position)
    }
}