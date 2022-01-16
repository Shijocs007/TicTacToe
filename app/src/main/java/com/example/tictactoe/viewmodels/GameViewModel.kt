package com.example.tictactoe.viewmodels

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

    var board = mutableListOf("-1", "-1", "-1", "-1", "-1","-1", "-1", "-1", "-1") // -1 represents initial value for each board

    var playerOne = ""
    var playerTwo = ""

    var currentPlayer = PlayerState.values().random()


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
        if(currentPlayer == PlayerState.PLAYER_ONE) {
            board[position] = "X"
            currentPlayer = PlayerState.PLAYER_TWO
            return PlayerState.PLAYER_ONE
        } else {
            board[position] = "O"
            currentPlayer = PlayerState.PLAYER_ONE
            return PlayerState.PLAYER_TWO
        }
    }

}