package com.dekola.fhrs.ui.authorities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.dekola.fhrs.R
import com.dekola.fhrs.data.Result
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.repository.AuthoritiesRepository
import com.dekola.fhrs.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
class AuthoritiesViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var galleryViewModel: AuthoritiesViewModel

    @Mock
    private lateinit var network: Network

    @Mock
    private lateinit var repository: AuthoritiesRepository

    lateinit var mockAnnotation: AutoCloseable

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockAnnotation = MockitoAnnotations.openMocks(this)
//        Mockito.`when`(network.isConnected() )} returns true
        galleryViewModel = AuthoritiesViewModel(repository, network)
    }


    @Test
    fun test_when_getAuthorities_returns_success() = runTest {
        Mockito.`when`(network.isConnected()).thenReturn(true)
        val expectedResult = listOf(AuthoritiesPresentation("Name"))
        Mockito.`when`(repository.getAuthorities()).thenReturn(Result.Success(expectedResult))
        galleryViewModel.getAuthorities()


        assertEquals(expectedResult, galleryViewModel.authoritiesResult.value?.success)
    }

    @Test
    fun test_when_getAuthorities_returns_failure() = runTest {
        val errorMessage = "TestErrorMessage"
        Mockito.`when`(network.isConnected()).thenReturn(true)
        Mockito.`when`(repository.getAuthorities()).thenReturn(Result.Error(errorMessage))
        galleryViewModel.getAuthorities()

        assertEquals(errorMessage, galleryViewModel.authoritiesResult.value?.errorMessage)
    }


    @Test
    fun test_no_internet_getAuthoritiesPagination() = runTest {
        Mockito.`when`(network.isConnected()).thenReturn(false)
        galleryViewModel.getAuthoritiesPagination()
        assertEquals(R.string.no_internet_connection, galleryViewModel.toastLiveData.value?.message)
    }

    @Test
    fun test_no_internet_getAuthorities() = runTest {
        Mockito.`when`(network.isConnected()).thenReturn(false)
        galleryViewModel.getAuthorities()
        assertEquals(R.string.no_internet_connection, galleryViewModel.toastLiveData.value?.message)
    }

    @Test
    fun test_true_loading_state() {
        val loadState = CombinedLoadStates(refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(endOfPaginationReached = false),
            append = LoadState.Loading,
            source = mock(),
            mediator = null)

        galleryViewModel.manageLoadStates(loadState)

        assertEquals(true, galleryViewModel.loadLiveData.value)
    }

    @Test
    fun test_false_loading_state() {
        val loadState = CombinedLoadStates(LoadState.NotLoading(endOfPaginationReached = false),
            LoadState.NotLoading(endOfPaginationReached = false),
            LoadState.NotLoading(endOfPaginationReached = false),
            mock(),
            null)

        galleryViewModel.manageLoadStates(loadState)

        assertEquals(false, galleryViewModel.loadLiveData.value)
    }


    @Test
    fun test_error_prepend_loading_state() {
        val errorMessage = "prepend error message"
//        val loadState2 = CombinedLoadStates(null, null, null, null, null)

        val loadState = CombinedLoadStates(LoadState.NotLoading(endOfPaginationReached = false),
            LoadState.Error(Throwable(errorMessage)),
            LoadState.NotLoading(endOfPaginationReached = false),
            mock(),
            null)

        galleryViewModel.manageLoadStates(loadState)

        assertEquals(errorMessage, galleryViewModel.showError.value)
    }


    @Test
    fun test_error_append_loading_state() {
        val errorMessage = "append error message"

        val loadState = CombinedLoadStates(
            LoadState.NotLoading(endOfPaginationReached = false),
            LoadState.NotLoading(endOfPaginationReached = false),
            LoadState.Error(Throwable(errorMessage)),
            mock(),
            null)

        galleryViewModel.manageLoadStates(loadState)

        assertEquals(errorMessage, galleryViewModel.showError.value)
    }

    @Test
    fun test_error_refresh_loading_state() {
        val errorMessage = "refresh error message"

        val loadState = CombinedLoadStates(LoadState.Error(Throwable(errorMessage)),
            LoadState.NotLoading(endOfPaginationReached = false),
            LoadState.NotLoading(endOfPaginationReached = false),
            mock(),
            null)

        galleryViewModel.manageLoadStates(loadState)

        assertEquals(errorMessage, galleryViewModel.showError.value)
    }

    @After
    fun teardown() {
        testDispatcher.cancel()
        mockAnnotation.close()
    }
}
