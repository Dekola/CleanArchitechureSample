package com.dekola.fhrs.data.repository

import androidx.paging.PagingData
import com.dekola.fhrs.data.Result
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import kotlinx.coroutines.flow.Flow

interface IAuthoritiesRepository {
    suspend fun getAuthorities(): Result<List<AuthoritiesPresentation?>>
    suspend fun getAuthoritiesPagination(): Flow<PagingData<AuthoritiesPresentation>>
    suspend fun getAuthoritiesFlow(): Flow<Result<List<AuthoritiesPresentation?>?>>
}