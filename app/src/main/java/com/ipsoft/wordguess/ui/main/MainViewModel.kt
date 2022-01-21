package com.ipsoft.wordguess.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipsoft.wordguess.data.entities.request.ValidateWordResponse
import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.data.entities.response.WordResponse
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.usecases.GetRandomWordUseCase
import com.ipsoft.wordguess.domain.usecases.ValidateWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel
@Inject constructor(
    private val getRandomRandomWordUseCase: GetRandomWordUseCase,
    private val validateWordUseCase: ValidateWordUseCase
) :
    ViewModel() {

    private val _word: MutableLiveData<String> = MutableLiveData()
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    private val _state: MutableLiveData<MainState> = MutableLiveData()
    private val _validWord: MutableLiveData<Boolean> = MutableLiveData()

    val failure: LiveData<Failure> = _failure
    val loading: LiveData<Boolean> = _loading
    val state: LiveData<MainState> = _state
    val validWord: LiveData<Boolean> = _validWord
    val word: LiveData<String> = _word

    private var checkWord = ""


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

    fun validateWord(word: String) {
        checkWord = word
        handleLoading(true)
        return validateWordUseCase(
            ValidateWordUseCase.Params(word),
            viewModelScope
        ) {
            it.fold(
                ::validationFailure,
                ::handleWordValidator
            )
        }
    }


    private fun handleWordFetchSuccess(response: WordResponse?) {
        viewModelScope.launch {

            response?.get(0)?.let { _word.postValue(it) }



            handleLoading(false)

        }

    }

    private fun handleWordValidator(response: ValidateWordResponse?) {
        viewModelScope.launch {

            if (response?.isEmpty() == true) {

                _validWord.postValue(false)
                handleLoading(false)

            } else {
                response?.let {
                    response.forEach { word ->
                        if (word.word == _word.value || word.word == checkWord) {
                            _validWord.postValue(true)
                            handleLoading(false)
                            return@forEach
                        } else {
                            _validWord.postValue(false)
                            handleLoading(false)
                        }
                    }

                }
            }
        }


    }

    private fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
        handleLoading(false)
    }

    private fun validationFailure(failure: Failure) {
        _failure.postValue(failure)
        handleLoading(false)
        _validWord.postValue(false)
    }

    private fun handleLoading(loading: Boolean) {
        _loading.postValue(loading)
    }
}

