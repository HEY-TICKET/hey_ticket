package com.sypark.openTicket.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.request.RegisterValidationSend
import com.sypark.openTicket.ApiResponseKey
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseFragment
import com.sypark.openTicket.databinding.FragmentRegisterValidationBinding
import com.sypark.openTicket.model.RegisterValidationSendViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterValidationFragment :
    BaseFragment<FragmentRegisterValidationBinding>(R.layout.fragment_register_validation) {

    private val args by navArgs<RegisterValidationFragmentArgs>()
    private val viewModel: RegisterValidationSendViewModel by viewModels()
    override fun init(view: View) {
        binding.apply {
            layoutLoginTop.topTitle.setText(R.string.register_top)
            editEmail.setText(args.item)

            setObserver()

            layoutLoginTop.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnNext.setOnClickListener {
                viewModel.getRegisterValidationSend2(
                    RegisterValidationSend(
                        args.item, ApiResponseKey.signUp
                    )
                )

            }
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.validationResponse.collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val data = Gson().fromJson<String>(
                            result.value.data, object : TypeToken<String>() {}.type
                        )
                        findNavController().navigate(
                            RegisterValidationFragmentDirections.actionRegisterValidationFragmentToRegisterFirstFragment(
                                data
                            )
                        )
                    }

                    is ApiResult.Error -> {

                    }

                    is ApiResult.Loading -> {
                        Log.e("!!!!", "loading")
                    }
                }
            }
        }
    }
}