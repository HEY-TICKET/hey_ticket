package com.sypark.openTicket.view.fragments

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sypark.openTicket.Common
import com.sypark.openTicket.PREFERENCE_KEY_ACCESS_TOKEN
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentMainBinding
import com.sypark.openTicket.excensions.dpToPx
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.MainViewModel
import com.sypark.openTicket.popups.showClosePopup
import com.sypark.openTicket.util.AppPreference
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

        binding.apply {
            layoutBottom.navigationBottom.menu.getItem(0).isChecked = true

            topTitle.imgSearch.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
            }

            if (AppPreference.get(PREFERENCE_KEY_ACCESS_TOKEN, "").isEmpty()) {
                // 로그인 X
                layoutRecommand.show()
            } else {
                // 로그인 O
                layoutRecommand.hide()
            }

            layoutRecommand.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFirstFragment())
            }

            recyclerviewRankingFilter.apply {
                genreAdapter = GenreAdapter { position, item ->
                    rankingFilterItemClicked(position)

                    lifecycleScope.launch {
                        viewModel.getRankingData(item.code)
                    }
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                genreAdapter.submitList(Common.genreList)
                adapter = genreAdapter
                genreAdapter.setSelectedPosition(0)
            }

            recyclerviewRanking.apply {
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
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = rankingAdapter
            }

            recyclerviewNewTicketFilter.apply {
                newFilterAdapter = GenreAdapter { position, item ->
                    Log.e("newFilterAdapter", item.toString())
                    newFilterItemClicked(position)
                    lifecycleScope.launch {
                        viewModel.getNewTicketData(item.code)
                    }
                }
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                newFilterAdapter.submitList(Common.genreList)
                adapter = newFilterAdapter
                newFilterAdapter.setSelectedPosition(0)
            }

            recyclerviewNewTicket.apply {
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
                addItemDecoration(Common.MarginItemDecoration(8.dpToPx()))
                adapter = newTicketAdapter
            }

            recyclerviewCampusTicket.apply {
                campusTicketAdapter = MainDefaultAdapter {

                }
//            campusTicketAdapter.submitList()
                adapter = campusTicketAdapter
            }

            recyclerviewEtcTicket.apply {
                etcTicketAdapter = MainDefaultAdapter {

                }
//            etcTicketAdapter.submitList()
                adapter = etcTicketAdapter
            }
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

        viewModel.newTicketList.observe(this) {
            newTicketAdapter.submitList(it)
        }
    }

    override fun backPressed() {
        requireActivity().showClosePopup(getString(R.string.finish_app), {

        }, {
            requireActivity().finish()
        })
    }

    private fun rankingFilterItemClicked(position: Int) {
        genreAdapter.setSelectedPosition(position)
    }

    private fun newFilterItemClicked(position: Int) {
        newFilterAdapter.setSelectedPosition(position)
    }
}