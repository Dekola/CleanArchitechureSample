package com.dekola.fhrs.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dekola.fhrs.data.Result
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.toPresentation
import com.dekola.fhrs.network.api.AuthoritiesApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthoritiesRemoteDataSource @Inject constructor(private val authoritiesApiService: AuthoritiesApiService) :
    IAuthoritiesRemoteDataSource {

    override suspend fun getAuthorities(): Result<List<AuthoritiesPresentation?>> {
        return try {
            val response = authoritiesApiService.getAuthorities()

            if (response.isSuccessful) {
                response.body()?.authorities?.map { it.toPresentation() }
                    ?.let { authoritiesPresentationList ->
                        Result.Success(authoritiesPresentationList)
                    } ?: kotlin.run {
                    Result.Error("An error occurred while trying to fetch authorities")
                }
            } else {
                Result.Error("An error occurred while trying to fetch authorities")
            }
        } catch (exception: Exception) {
            Result.Error("An error occurred while trying to fetch authorities")
        }
    }

    override suspend fun getAuthoritiesPagination(): Flow<PagingData<AuthoritiesPresentation>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AuthoritiesPagingSource(authoritiesApiService)
            }
        ).flow
    }
}