package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.tictactoe.databinding.ActivityGameBinding
import com.example.tictactoe.utils.FRAGMENT_HOME_TO_GAME
import com.example.tictactoe.viewmodels.GameViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels()

    private lateinit var binding : ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_game
        )

        initObservers()
    }

    private fun initObservers() {
        viewModel.apply {
            lifecycleScope.launch {
                uiFlow.collectLatest {
                    if(it.equals(FRAGMENT_HOME_TO_GAME)) {
                        Navigation.findNavController(this@GameActivity, R.id.nav_host_fragment).navigate(R.id.gameFragment, null)
                    } else {
                        Toast.makeText(this@GameActivity, it, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}