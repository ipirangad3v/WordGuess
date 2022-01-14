package com.ipsoft.wordguess.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.data.entities.response.WordResponse
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.usecases.GetRandomWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(private val getRandomRandomWordUseCase: GetRandomWordUseCase) :
    ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _failure: MutableLiveData<Failure> = MutableLiveData()

    val failure: LiveData<Failure> = _failure
    val loading: LiveData<Boolean> = _loading


    private val _word: MutableLiveData<String> = MutableLiveData()
    val word: LiveData<String> = _word

    fun getRandomWord(wordRequest: WordRequest) {
        handleLoading(true)
        return getRandomRandomWordUseCase(
            GetRandomWordUseCase.Params(wordRequest),
            viewModelScope
        ) {
            it.fold(
                ::handleFailure,
                ::handleWordFetchSuccess
            )
        }
    }


    private fun handleWordFetchSuccess(response: WordResponse) {
        viewModelScope.launch {

            _word.postValue(response[0])


            handleLoading(false)

        }

    }

    private fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
        handleLoading(false)
    }

    private fun handleLoading(loading: Boolean) {
        _loading.postValue(loading)
    }
}

