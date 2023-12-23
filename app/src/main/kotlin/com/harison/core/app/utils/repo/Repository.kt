package com.harison.core.app.utils.repo

import com.harison.core.app.utils.api.FakeVideoData
import com.harison.core.app.utils.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository {

    fun getDataList(): Flow<List<FakeVideoData>> = flow {
        val r = RetrofitClient.retrofit.getDataList()
        emit(r)
    }.flowOn(Dispatchers.IO)

}
