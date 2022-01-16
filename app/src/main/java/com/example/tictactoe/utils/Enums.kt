package com.example.tictactoe.utils

enum class PlayerState {
    PLAYER_ONE,PLAYER_TWO
}


sealed class GameState {
    class WIN(val player : String) : GameState()
    class DRAW(val player : String) : GameState()
    class PLAYING(val player : String) : GameState()
}