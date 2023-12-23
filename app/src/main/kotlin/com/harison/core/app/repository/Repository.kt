package com.harison.core.app.repository

import com.harison.core.app.api.BaseResponse
import com.harison.core.app.api.entity.ResponseApi

interface Repository {
    suspend fun getApiData(): BaseResponse<ResponseApi>
}