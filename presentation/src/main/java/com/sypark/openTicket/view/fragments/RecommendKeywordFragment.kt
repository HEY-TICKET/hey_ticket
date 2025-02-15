package com.sypark.openTicket.view.fragments

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.RegisterToken
import com.sypark.data.db.entity.request.Signup
import com.sypark.openTicket.PREFERENCE_KEY_ACCESS_TOKEN
import com.sypark.openTicket.PREFERENCE_KEY_GRANT_TYPE
import com.sypark.openTicket.PREFERENCE_KEY_REFRESH_TOKEN
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRecommendKeywordBinding
import com.sypark.openTicket.excensions.afterTextChanged
import com.sypark.openTicket.excensions.hide
import com.sypark.openTicket.excensions.show
import com.sypark.openTicket.model.ActivityViewModel
import com.sypark.openTicket.model.RecommendKeywordViewModel
import com.sypark.openTicket.popups.showClosePopup
import com.sypark.openTicket.util.AppPreference
import com.sypark.openTicket.view.adapter.RecommendKeywordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RecommendKeywordFragment :
    BaseFragment<FragmentRecommendKeywordBinding>(R.layout.fragment_recommend_keyword) {

    private lateinit var recommendKeywordAdapter: RecommendKeywordAdapter
    private var keyWordList = ArrayList<String>()
    private val viewModel: RecommendKeywordViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()


    override fun init(view: View) {
        setUpObserver()

        FlexboxLayoutManager(view.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.recyclerviewKeyword.apply {
                layoutManager = it
                recommendKeywordAdapter = RecommendKeywordAdapter {
                    keyWordList.remove(it)
                    recommendKeywordAdapter.remove(it)
                }
                adapter = recommendKeywordAdapter
            }
        }


        binding.apply {
            layoutRegisterTop.imgBack.setOnClickListener {
                backPressed()
            }

            layoutRegisterTop.imgClose.setOnClickListener {
                backPressed()
            }

            btnRegisterKeyword.setOnClickListener {
                keyDown(it.context)
                setKeywordData()
            }

            textKeyword.afterTextChanged {
                viewModel.setKeywordText(it)
            }

            btnNext.setOnClickListener {
                activityViewModel.setKeyWords(keyWordList)
                binding.includeAgree.root.show()
            }

            includeAgree.imgClose.setOnClickListener {
                activityViewModel.reSetPushAgree()
                activityViewModel.reSetProvisionAgree()
                activityViewModel.reSetRegisterFinish()

                binding.includeAgree.root.hide()
            }

            includeAgree.checkBoxAgreePush.setOnClickListener {
                activityViewModel.isPushAgree()
                activityViewModel.isRegisterFinish()
            }

            includeAgree.checkBoxProvisionPush.setOnClickListener {
                activityViewModel.isProvisionAgree()
                activityViewModel.isRegisterFinish()
            }

            includeAgree.btnNext.setOnClickListener {
                val genreList = if (activityViewModel.genres.value.isNullOrEmpty()) {
                    arrayListOf()
                } else {
                    activityViewModel.genres.value!!.map { data ->
                        data.code
                    } as ArrayList<String>
                }

                val areaList = if (activityViewModel.areas.value.isNullOrEmpty()) {
                    arrayListOf()
                } else {
                    activityViewModel.areas.value!!.map { data ->
                        data.code
                    } as ArrayList<String>
                }

                viewModel.getSignUp(
                    signup = Signup(
                        email = activityViewModel.email.value!!,
                        password = activityViewModel.pw.value!!,
                        verificationCode = activityViewModel.verificationCode.value!!,
                        genres = genreList,
                        areas = areaList,
                        keywords = activityViewModel.keywords.value!!,
                        keywordPush = activityViewModel.isPushAgree.value!!
                    )
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.response.collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val data = Gson().fromJson<RegisterToken>(
                            result.value.data, object : TypeToken<RegisterToken>() {}.type
                        )

                        //todo_sypark response code 로 수정 예정
                        if (result.value.code == "OK") {
                            AppPreference.set(PREFERENCE_KEY_GRANT_TYPE, data.grantType)
                            AppPreference.set(PREFERENCE_KEY_ACCESS_TOKEN, data.accessToken)
                            AppPreference.set(PREFERENCE_KEY_REFRESH_TOKEN, data.refreshToken)
                            findNavController().navigate(RecommendKeywordFragmentDirections.actionRecommendKeywordFragmentToMainFragment())
                        }


                    }

                    is ApiResult.Error -> {

                    }

                    is ApiResult.Loading -> {

                    }
                }
            }
        }
    }

    override fun backPressed() {
        requireActivity().showClosePopup(getString(R.string.register_close_popup), {

        }, {
            findNavController().popBackStack()
        })
    }

    private fun keyDown(view: Context) {
        //키보드 내리기
        val imm = view.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textKeyword.windowToken, 0)
    }

    private fun setKeywordData() {
        if (!viewModel.keywordText.value.isNullOrEmpty()) {
            keyWordList.add(viewModel.keywordText.value.toString())
            recommendKeywordAdapter.add(viewModel.keywordText.value.toString())
        }
    }

    private fun setUpObserver() {
        viewModel.keywordText.observe(viewLifecycleOwner, ::keyWordWatcher)
        activityViewModel.keywords.observe(viewLifecycleOwner, ::keyWordsWatcher)
        activityViewModel.isPushAgree.observe(viewLifecycleOwner, ::pushWatcher)
        activityViewModel.isProvisionAgree.observe(viewLifecycleOwner, ::provisionWatcher)
        activityViewModel.isRegisterAgree.observe(viewLifecycleOwner,::isRegisterWatcher)
    }


    private fun keyWordWatcher(keyword: String?) {
        if (keyword.isNullOrEmpty()) {
            binding.btnRegisterKeyword.isEnabled = false
            binding.btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_gray)

        } else {
            binding.btnRegisterKeyword.isEnabled = true
            binding.btnRegisterKeyword.setBackgroundResource(R.drawable.round_12_black)
        }
    }

    private fun keyWordsWatcher(keywords: ArrayList<String>?) {
        binding.apply {
            if (keywords.isNullOrEmpty()) {
                includeAgree.layoutAgreePush.hide()
            } else {
                includeAgree.layoutAgreePush.show()
            }
        }
    }

    private fun pushWatcher(isPush: Boolean) {
        binding.includeAgree.checkBoxAgreePush.isSelected = isPush
    }

    private fun provisionWatcher(isProvision: Boolean) {
        binding.includeAgree.checkBoxProvisionPush.isSelected = isProvision
    }

    private fun isRegisterWatcher(isRegister: Boolean) {
        binding.apply {
            if (isRegister) {
                includeAgree.btnNext.setBackgroundResource(R.drawable.round_12_black)
                includeAgree.btnNext.isEnabled = true
            } else {
                includeAgree.btnNext.setBackgroundResource(R.drawable.round_12_gray)
                includeAgree.btnNext.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}