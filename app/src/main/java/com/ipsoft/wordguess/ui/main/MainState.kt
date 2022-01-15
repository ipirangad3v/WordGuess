package com.ipsoft.wordguess.ui.main

import com.ipsoft.wordguess.ui.BaseState

sealed class MainState : BaseState() {

    object InProgress : MainState()
    object GameOver : MainState()

}