package com.example.tictactoe.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.utils.FRAGMENT_HOME_TO_GAME
import com.example.tictactoe.utils.GameState
import com.example.tictactoe.utils.PlayerState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _uiFlow = MutableSharedFlow<String>()
    var uiFlow : SharedFlow<String> = _uiFlow
    private var _currentPlayer =  MutableLiveData<PlayerState>(PlayerState.values().random())
    var currentPlayer : LiveData<PlayerState> = _currentPlayer

    var board = mutableListOf("-1", "-1", "-1", "-1", "-1","-1", "-1", "-1", "-1") // -1 represents initial value for each board

    var playerOne = ""
    var playerTwo = ""

    //var _currentPlayer : LiveData = _currentPlayer


    fun onStartGame() {
        viewModelScope.launch {
            if(playerOne.isBlank())
                _uiFlow.emit("Player one is empty")
            else if(playerTwo.isBlank())
                _uiFlow.emit("Player two is empty")
            else {
                _uiFlow.emit(FRAGMENT_HOME_TO_GAME)
            }
        }
    }


    fun isBoardVacant(position : Int) : Boolean {
        return board[position] == "-1"
    }

    fun checkForWinner() : GameState {
            for (i in 1 until 9) {
                var result = ""

                when(i) {
                    1 -> result = board[0] + board[1]+ board[2]
                    2 -> result = board[3] + board[4]+ board[5]
                    3 -> result = board[6] + board[7]+ board[8]
                    4 -> result = board[0] + board[3]+ board[6]
                    5 -> result = board[1] + board[4]+ board[7]
                    6 -> result = board[2] + board[5]+ board[8]
                    7 -> result = board[0] + board[4]+ board[8]
                    8 -> result = board[2] + board[4]+ board[6]
                }

                if(result == "XXX") {
                   return GameState.WIN(playerOne)
                }
                if(result == "OOO") {
                    return GameState.WIN(playerTwo)
                }

            }

            if(board.contains("-1")) {
                return GameState.PLAYING("")
            } else {
                return GameState.DRAW("")
            }

    }

    fun setBoardSelection(position: Int) : PlayerState {
        if(_currentPlayer.value == PlayerState.PLAYER_ONE) {
            board[position] = "X"
            _currentPlayer.value = PlayerState.PLAYER_TWO
            return PlayerState.PLAYER_ONE
        } else {
            board[position] = "O"
            _currentPlayer.value = PlayerState.PLAYER_ONE
            return PlayerState.PLAYER_TWO
        }
    }

    fun resetGame() {
        board = mutableListOf("-1", "-1", "-1", "-1", "-1","-1", "-1", "-1", "-1")
        _currentPlayer.value = PlayerState.values().random()
    }

}