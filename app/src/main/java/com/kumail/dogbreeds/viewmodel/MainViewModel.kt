package com.kumail.dogbreeds.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumail.dogbreeds.data.model.BreedItem
import com.kumail.dogbreeds.data.model.toListOfBreedItems
import com.kumail.dogbreeds.data.repository.BreedRepository
import com.kumail.dogbreeds.network.ApiResponse
import com.kumail.dogbreeds.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by kumailhussain on 12/10/2021.
 */
@HiltViewModel
class MainViewModel @Inject internal constructor(private val breedRepository: BreedRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: SingleLiveEvent<String> = _errorMessage

    private val _breedsList = MutableLiveData<List<BreedItem>>()
    val breedsList: MutableLiveData<List<BreedItem>> = _breedsList

    private val _breedImageUrls = MutableLiveData<List<String>>()
    val breedImageUrls: LiveData<List<String>> = _breedImageUrls

    init {
        getBreedsList()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getBreedsList() {
        viewModelScope.launch {
            when (val result = breedRepository.getBreedsList()) {
                is ApiResponse.Success -> _breedsList.postValue(result.data.breedsList.toListOfBreedItems())
                is ApiResponse.Empty -> Timber.d(result.toString())
                is ApiResponse.NetworkError -> {
                    _errorMessage.postValue(result.errorResponse.errorMessage)
                    Timber.e(result.errorResponse.toString())
                }
                is ApiResponse.ExceptionError -> {
                    _errorMessage.postValue(result.errorResponse.message)
                    Timber.e(result.errorResponse.toString())
                }
            }
        }
    }

    fun getBreedRandomImages(breed: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = breedRepository.getBreedRandomImages(breed)) {
                is ApiResponse.Success -> _breedImageUrls.postValue(result.data.breedImageUrls)
                is ApiResponse.Empty -> Timber.d(result.toString())
                is ApiResponse.NetworkError -> {
                    _errorMessage.postValue(result.errorResponse.errorMessage)
                    Timber.e(result.errorResponse.toString())
                }
                is ApiResponse.ExceptionError -> {
                    _errorMessage.postValue(result.errorResponse.message)
                    Timber.e(result.errorResponse.toString())
                }
            }
            _isLoading.value = false
        }
    }

    fun getSubBreedRandomImages(breed: String, subBreed: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = breedRepository.getSubBreedRandomImages(breed, subBreed)) {
                is ApiResponse.Success -> _breedImageUrls.postValue(result.data.breedImageUrls)
                is ApiResponse.Empty -> Timber.d(result.toString())
                is ApiResponse.NetworkError -> {
                    _errorMessage.postValue(result.errorResponse.errorMessage)
                    Timber.e(result.errorResponse.toString())
                }
                is ApiResponse.ExceptionError -> {
                    _errorMessage.postValue(result.errorResponse.message)
                    Timber.e(result.errorResponse.toString())
                }
            }
            _isLoading.value = false
        }
    }
}