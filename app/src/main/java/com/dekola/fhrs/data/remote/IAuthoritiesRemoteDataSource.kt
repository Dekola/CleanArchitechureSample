package com.dekola.fhrs.data.remote

import androidx.paging.PagingData
import com.dekola.fhrs.data.Result
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import kotlinx.coroutines.flow.Flow

interface IAuthoritiesRemoteDataSource {
    suspend fun getAuthorities(): Result<List<AuthoritiesPresentation?>>
    suspend fun getAuthoritiesPagination(): Flow<PagingData<AuthoritiesPresentation>>
}