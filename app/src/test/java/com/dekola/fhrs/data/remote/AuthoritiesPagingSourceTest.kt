package com.dekola.fhrs.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.dekola.fhrs.data.AuthoritiesResponse
import com.dekola.fhrs.data.AuthoritiesResponseItem
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.toPresentation
import com.dekola.fhrs.network.api.AuthoritiesApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthoritiesPagingSourceTest {

    var test = UnconfinedTestDispatcher()

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var authoritiesApi: AuthoritiesApiService

    private lateinit var authoritiesPagingSource: AuthoritiesPagingSource

    @Before
    fun setUp() {
//        Dispatchers.setMain(StandardTestDispatcher())

        Dispatchers.setMain(test)
        MockKAnnotations.init(this, relaxed = true)
        authoritiesPagingSource = AuthoritiesPagingSource(authoritiesApi)
    }

    @Test
    fun `when api service response throws exception`() {
        runTest {
            val httpException = HttpException(
                Response.error<AuthoritiesResponse>(
                    500, "Test Server Error"
                        .toResponseBody("text/plain".toMediaTypeOrNull())
                )
            )

            coEvery { (authoritiesApi.getPaginationAuthorities(any(), any())) } throws httpException

            val expectedResult =
                PagingSource.LoadResult.Error<Int, AuthoritiesPresentation>(httpException)

            assertEquals(expectedResult, authoritiesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ))
        }
    }

    @Test
    fun `when api service response runs successfully`() {
        runTest {
            val response =
                AuthoritiesResponse().apply {
                    authorities = listOf(AuthoritiesResponseItem(), AuthoritiesResponseItem())
                }

            coEvery {
                (authoritiesApi.getPaginationAuthorities(any(), any()))
            } returns Response.success(response)

            val expectedResult = PagingSource.LoadResult.Page(
                data = response.authorities!!.map { it.toPresentation() },
                prevKey = null,
                nextKey = 2
            )

            assertEquals(expectedResult, authoritiesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = NETWORK_PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
            )
        }
    }

    @After
    fun teardown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}
