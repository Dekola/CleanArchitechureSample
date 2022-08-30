package com.dekola.fhrs.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.toPresentation
import com.dekola.fhrs.network.api.AuthoritiesApiService
import org.json.JSONObject

const val AUTHORITIES_STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 10

class AuthoritiesPagingSource(
    private val authoritiesApi: AuthoritiesApiService,
) : PagingSource<Int, AuthoritiesPresentation>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuthoritiesPresentation> {
        val position = params.key ?: AUTHORITIES_STARTING_PAGE_INDEX
        return try {
            val response =
                authoritiesApi.getPaginationAuthorities(position, NETWORK_PAGE_SIZE)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                val items = responseBody.authorities
                val nextKey = if (items?.isEmpty() == true) {
                    null
                } else {
                    position + (params.loadSize / NETWORK_PAGE_SIZE)
                }
                items?.let {
                    LoadResult.Page(
                        data = items.map { it.toPresentation() },
                        prevKey = if (position == AUTHORITIES_STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = nextKey
                    )
                } ?: run {
                    LoadResult.Error(Exception(""))
                }
            } else {
                val responseString = response.errorBody()?.string()
                val errorString = JSONObject(responseString ?: "").getString("message")
                LoadResult.Error(Exception(errorString))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AuthoritiesPresentation>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}