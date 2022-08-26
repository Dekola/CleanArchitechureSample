package com.dekola.fhrs.ui.authorities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dekola.fhrs.R
import com.dekola.fhrs.data.repository.IAuthoritiesRepository
import com.dekola.fhrs.data.Result
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.data.wrapper.ToastWrapper
import com.dekola.fhrs.network.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthoritiesViewModel @Inject constructor(
    private val authoritiesRepository: IAuthoritiesRepository,
    private val networkConnectivity: NetworkConnectivity,
) : ViewModel() {

    private val _loadLiveData = MutableLiveData<Boolean>()
    val loadLiveData: LiveData<Boolean> = _loadLiveData

    private val _authoritiesResult = MutableLiveData<AuthoritiesResult>()
    val authoritiesResult: LiveData<AuthoritiesResult> = _authoritiesResult

    fun getAuthorities() {
        viewModelScope.launch {
            _loadLiveData.postValue(true)
            when (val authoritiesResult = authoritiesRepository.getAuthorities()) {
                is Result.Error -> {
                    _authoritiesResult.postValue(AuthoritiesResult(errorMessage = authoritiesResult.errorMessage))
                }
                is Result.Success -> {
                    _authoritiesResult.postValue(AuthoritiesResult(success = authoritiesResult.data))
                }
            }
            _loadLiveData.postValue(false)
        }
    }


    fun getAuthoritiesFlow() {
        viewModelScope.launch {
            _loadLiveData.postValue(true)

            authoritiesRepository.getAuthoritiesFlow().collect { AuthoritiesResult ->
                when (AuthoritiesResult) {
                    is Result.Error -> {
                        _authoritiesResult.postValue(AuthoritiesResult(errorMessage = AuthoritiesResult.errorMessage))
                    }
                    is Result.Success -> {
                        _authoritiesResult.postValue(AuthoritiesResult(success = AuthoritiesResult.data))
                    }
                }
            }
            _loadLiveData.postValue(false)
        }
    }


    private val _authoritiesPaginationLiveData =
        MutableLiveData<PagingData<AuthoritiesPresentation>>()

    val authoritiesPaginationLiveData: LiveData<PagingData<AuthoritiesPresentation>> =
        _authoritiesPaginationLiveData

    private val _toastLiveData = MutableLiveData<ToastWrapper>()
    var toastLiveData: LiveData<ToastWrapper> = _toastLiveData

    fun getAuthoritiesPagination() {
        viewModelScope.launch {
            if (!networkConnectivity.isConnected()) {
                _toastLiveData.postValue(ToastWrapper(R.string.no_internet_connection))
            } else {
                authoritiesRepository.getAuthoritiesPagination().cachedIn(viewModelScope).collect {
                    _authoritiesPaginationLiveData.postValue(it)
                }
            }
        }
    }


    private val _showError = MutableLiveData<String>()
    var showError: LiveData<String> = _showError

    fun manageLoadStates(loadState: CombinedLoadStates) {
        _loadLiveData.postValue(loadState.append is LoadState.Loading)
        when {
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }?.error?.message?.let { errorMessage ->
            _showError.postValue(errorMessage)
        }
    }

}

data class AuthoritiesResult(
    val success: List<AuthoritiesPresentation?>? = null,
    val errorMessage: String? = null,
)