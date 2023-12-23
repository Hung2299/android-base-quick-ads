package com.harison.core.app.api.repository

import com.harison.core.app.api.ApiService
import com.harison.core.app.api.BaseResponse
import com.harison.core.app.api.entity.ResponseApi
import com.harison.core.app.repository.Repository
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