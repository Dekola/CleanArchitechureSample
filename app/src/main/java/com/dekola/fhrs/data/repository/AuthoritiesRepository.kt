package com.dekola.fhrs.data.repository

import androidx.paging.PagingData
import com.dekola.fhrs.data.Result
import com.dekola.fhrs.data.local.dataSource.IAuthoritiesLocalDataSource
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.remote.IAuthoritiesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthoritiesRepository @Inject constructor(
    private val authoritiesLocalDataSource: IAuthoritiesLocalDataSource,
    private val authoritiesRemoteDataSource: IAuthoritiesRemoteDataSource,
) :
    IAuthoritiesRepository {

    override suspend fun getAuthorities(): Result<List<AuthoritiesPresentation?>> {
        return authoritiesRemoteDataSource.getAuthorities()
    }

    override suspend fun getAuthoritiesPagination(): Flow<PagingData<AuthoritiesPresentation>> {
        return authoritiesRemoteDataSource.getAuthoritiesPagination()
    }

    override suspend fun getAuthoritiesFlow() = flow {
        emit(fetchSavedAuthorities())
        val authoritiesResult: Result<List<AuthoritiesPresentation?>?> = getAuthorities()

        if (authoritiesResult is Result.Success) {
            saveAuthorities(authoritiesResult.data)
        }
        emit(authoritiesResult)
    }

    private suspend fun fetchSavedAuthorities(): Result<List<AuthoritiesPresentation?>?> {
        return Result.Success(authoritiesLocalDataSource.fetchSavedAuthorities())
    }

    private suspend fun saveAuthorities(data: List<AuthoritiesPresentation?>?) {
        authoritiesLocalDataSource.saveAuthorities(data)
    }
}