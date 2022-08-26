package com.dekola.fhrs.network.api

import com.dekola.fhrs.data.AuthoritiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthoritiesApiService {

    //    @Headers("x-api-version:2")
    @GET("authorities")
    suspend fun getAuthorities(): Response<AuthoritiesResponse>

    @GET("authorities/{page_number}/{page_size}")
    suspend fun getPaginationAuthorities(
        @Path("page_number") pageNumber: Int,
        @Path("page_size") pageSize: Int,
    ): Response<AuthoritiesResponse>

}