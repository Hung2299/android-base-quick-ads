package com.harison.core.app.repository

import com.harison.core.app.api.ApiService
import com.harison.core.app.api.BaseResponse
import com.harison.core.app.api.entity.ResponseApi
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {
    override suspend fun getApiData(): BaseResponse<ResponseApi> = withContext(Dispatchers.IO) {
        return@withContext apiService.getApiData()
    }
}