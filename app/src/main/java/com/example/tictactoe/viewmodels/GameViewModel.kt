package com.example.tictactoe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.utils.FRAGMENT_HOME_TO_GAME
import com.example.tictactoe.utils.PlayerState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _toastFlow = MutableSharedFlow<String>()
    var toastFlow : SharedFlow<String> = _toastFlow

    var playerX = MutableStateFlow<PlayerState>(PlayerState.PLAYER_RANDOM)

    var playerOne = ""
    var playerTwo = ""


    fun onStartGame() {
        viewModelScope.launch {
            if(playerOne.isBlank())
                _toastFlow.emit("Player one is empty")
            else if(playerTwo.isBlank())
                _toastFlow.emit("Player two is empty")
            else {
                _toastFlow.emit(FRAGMENT_HOME_TO_GAME)
            }
        }
    }

}