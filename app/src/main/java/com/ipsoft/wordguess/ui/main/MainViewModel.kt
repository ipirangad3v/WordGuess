package com.ipsoft.wordguess.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipsoft.wordguess.data.datasource.local.room.entities.Score
import com.ipsoft.wordguess.data.datasource.remote.entities.request.ValidateWordResponse
import com.ipsoft.wordguess.data.datasource.remote.entities.request.WordRequest
import com.ipsoft.wordguess.data.datasource.remote.entities.response.NearWordResponse
import com.ipsoft.wordguess.data.datasource.remote.entities.response.WordResponse
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.extension.removeAccents
import com.ipsoft.wordguess.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
open class MainViewModel
@Inject constructor(
    private val randomRandomWordUseCase: RandomWordUseCase,
    private val validateWordUseCase: ValidateWordUseCase,
    private val nearWordUseCase: NearWordUseCase,
    private val getScoreUseCase: GetScoreUseCase,
    private val saveScoreUseCase: SaveScoreUseCase,
    private val updateScoreUseCase: UpdateScoreUseCase
) :
    ViewModel() {

    private val _word: MutableLiveData<String> = MutableLiveData()
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    private val _state: MutableLiveData<MainState> = MutableLiveData()
    private val _validWord: MutableLiveData<Boolean> = MutableLiveData()
    private val _score: MutableLiveData<Score> = MutableLiveData()

    val failure: LiveData<Failure> = _failure
    val loading: LiveData<Boolean> = _loading
    val state: LiveData<MainState> = _state
    val validWord: LiveData<Boolean> = _validWord
    val word: LiveData<String> = _word
    val score: LiveData<Score> = _score

    private var checkWord = ""
        get() = field.removeAccents()


    fun getRandomWord(wordRequest: WordRequest) {
        handleLoading(true)
        return randomRandomWordUseCase(
            RandomWordUseCase.Params(wordRequest),
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

    fun getScore() {
        return getScoreUseCase(
            UseCase.None(),
            viewModelScope
        ) {
            it.fold(
                ::handleDBError,
                ::handleGetScore
            )
        }

    }

    fun updateScore(score: Score) {
        return updateScoreUseCase(
            UpdateScoreUseCase.Params(score), viewModelScope
        ) {

            it.fold(
                ::handleDBError,
                ::handleUpdatedScore

            )
        }
    }

    fun saveScore(score: Score) {
        return saveScoreUseCase(
            SaveScoreUseCase.Params(score), viewModelScope
        ) {
            it.fold(
                ::handleDBError,
                ::handleSaveScore
            )
        }
    }

    private fun handleSaveScore(unit: Unit) {
        getScore()
    }

    private fun handleUpdatedScore(unit: Unit) {
        getScore()

    }

    private fun handleDBError(failure: Failure) {
        _failure.postValue(failure)

    }


    private fun handleGetScore(list: List<Score>) {

        if (list.isNotEmpty()) {
            _score.postValue(list[0])
        } else {
            saveScore(
                Score(0, 0, 0, 0)
            )
        }


    }

    private fun fetchNearWord(word: String) {
        handleLoading(true)
        return nearWordUseCase(
            NearWordUseCase.Params(word),
            viewModelScope
        ) {
            it.fold(
                ::nearFailure,
                ::handleNearWordFetch
            )
        }
    }

    private fun nearFailure(failure: Failure) {

        _failure.postValue(failure)
        handleLoading(false)
        _validWord.postValue(false)

    }

    private fun handleNearWordFetch(nearWordResponse: NearWordResponse) {

        if (nearWordResponse.isEmpty()) {
            handleLoading(false)
            _validWord.postValue(false)

        } else {
            Timber.i("----- checkword $checkWord")
            val containsWord = nearWordResponse.map { it.removeAccents() }.contains(checkWord)

            if (containsWord) {
                _validWord.postValue(true)
                handleLoading(false)
            } else {
                _validWord.postValue(false)
                handleLoading(false)
            }
        }

    }


    private fun handleWordFetchSuccess(response: WordResponse?) {
        viewModelScope.launch {

            response?.get(0)?.let { _word.postValue(it.lowercase(Locale.getDefault())) }



            handleLoading(false)

        }

    }

    private fun handleWordValidator(response: ValidateWordResponse?) {
        viewModelScope.launch {

            if (response?.isEmpty() == true) {

                fetchNearWord(checkWord)

            } else {
                response?.let {
                    var containsWord = false
                    response.forEach {
                        containsWord = it.word.map { word ->
                            word.toString().removeAccents()
                        }.contains(checkWord)
                    }
                    if (containsWord) {

                        _validWord.postValue(true)
                        handleLoading(false)

                    } else {
                        fetchNearWord(checkWord)
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
    }

    private fun handleLoading(loading: Boolean) {
        _loading.postValue(loading)
    }
}

