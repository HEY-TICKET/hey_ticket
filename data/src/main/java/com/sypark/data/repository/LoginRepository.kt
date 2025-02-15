package com.sypark.data.repository

import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.LoginVerification
import com.sypark.data.db.entity.request.RegisterValidationSend
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.data.db.entity.request.Signup
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getLoginValidation(loginVerification: LoginVerification): Flow<ApiResult<BaseResponse>>
    suspend fun getRegisterValidationSend(registerValidationSend: RegisterValidationSend): Flow<ApiResult<BaseResponse>>
    suspend fun getRegisterVerify(registerValidationVerify: RegisterValidationVerify): Flow<ApiResult<BaseResponse>>
    suspend fun getSignUp(signup: Signup): Flow<ApiResult<BaseResponse>>

}