package com.example.tictactoe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.databinding.FragmentHomeBinding
import com.example.tictactoe.utils.GameState
import com.example.tictactoe.utils.PlayerState
import com.example.tictactoe.viewmodels.GameViewModel


class GameFragment : Fragment(), View.OnClickListener {

    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var binding: FragmentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentGameBinding.inflate(layoutInflater, container, false)
        binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListentes()
    }

    private fun initClickListentes() {
        binding.apply {
            btn1.setOnClickListener(this@GameFragment)
            btn2.setOnClickListener(this@GameFragment)
            btn3.setOnClickListener(this@GameFragment)
            btn4.setOnClickListener(this@GameFragment)
            btn5.setOnClickListener(this@GameFragment)
            btn6.setOnClickListener(this@GameFragment)
            btn7.setOnClickListener(this@GameFragment)
            btn8.setOnClickListener(this@GameFragment)
            btn9.setOnClickListener(this@GameFragment)
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            view?.let {
                when(it){
                    btn1 -> setSelectedView(buttonImage1, 0)
                    btn2 -> setSelectedView(buttonImage2, 1)
                    btn3 -> setSelectedView(buttonImage3, 2)
                    btn4 -> setSelectedView(buttonImage4, 3)
                    btn5 -> setSelectedView(buttonImage5, 4)
                    btn6 -> setSelectedView(buttonImage6, 5)
                    btn7 -> setSelectedView(buttonImage7, 6)
                    btn8 -> setSelectedView(buttonImage8, 7)
                    btn9 -> setSelectedView(buttonImage9, 8)
                }
            }
        }
    }

    private fun setSelectedView(imageView: ImageView, position : Int) {
        if(viewModel.isBoardVacant(position)) {
           val player = viewModel.setBoardSelection(position)
            if(player == PlayerState.PLAYER_ONE)
                imageView.setImageResource(R.drawable.cross)
            else
                imageView.setImageResource(R.drawable.circle)

           val winner = viewModel.checkForWinner()
            when(winner) {
                is GameState.WIN -> {
                    print("")
                }
                is GameState.DRAW -> {
                    print("")
                }
            }
        } else {
            Toast.makeText(context, "Already Selected", Toast.LENGTH_SHORT).show()
        }
    }
}