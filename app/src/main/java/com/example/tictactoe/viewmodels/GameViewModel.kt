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

    private var board = mutableListOf("-1", "-1", "-1", "-1", "-1","-1", "-1", "-1", "-1") // -1 represents initial value for each board

    var playerOne = ""
    var playerTwo = ""


    /**
     * Method called from start button of {@link HomeFragment.kt)
     * if players names are empty, show toast message to user
     */
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


    /**
     * Method used to check, the board position is already played or not.
     * If board position vacant, board[position] will be -1
     * @param position board position clicked bu user
     * @return true, if its -1 , false if it is "X" or "O"
     */
    fun isBoardVacant(position : Int) : Boolean {
        return board[position] == "-1"
    }

    /**
     * Method will be called when user select a board
     * every time it will check all 8 lines in the grid
     * if result equals "XXX" or "OOO" returns GameState.WIN
     * if board contains atlest one "-1", the game must continue, so returns GameState.PLAYING
     * if all boards are selected and no winner, then returns GameState.DRAW
     */
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

    /**
     *  Method will be called when user select a board
     *  the _currentPlayer player should be changed after selection
     *  board[position] value set based on which player
     *  @param position board position selected by user
     */
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

    /**
     * used to set game to the initial state
     * currentPlayer will be chose randomly
     */
    fun resetGame() {
        board = mutableListOf("-1", "-1", "-1", "-1", "-1","-1", "-1", "-1", "-1")
        _currentPlayer.value = PlayerState.values().random()
    }

}