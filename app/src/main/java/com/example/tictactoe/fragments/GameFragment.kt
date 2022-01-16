package com.example.tictactoe.fragments

import android.app.AlertDialog
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
import android.content.DialogInterface
import androidx.navigation.Navigation
import androidx.activity.OnBackPressedCallback





class GameFragment : Fragment(), View.OnClickListener {

    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var binding: FragmentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showDialogue("EXIT!!", "Are you sure, want to exit?", "Resume", "Exit", true)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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
        initObservers()
    }

    private fun initObservers() {
        viewModel.currentPlayer.observe(viewLifecycleOwner, {
            if(it == PlayerState.PLAYER_ONE) {
                binding.Reset.text = viewModel.playerOne + " 's Turn"
                binding.Reset.setTextColor(resources.getColor(android.R.color.holo_green_light))
            } else {
                binding.Reset.text = viewModel.playerTwo + " 's Turn"
                binding.Reset.setTextColor(resources.getColor(android.R.color.holo_red_light))
            }
        })
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
                    showDialogue("WIN!!!!!!", winner.player + " won the match")
                }
                is GameState.DRAW -> {
                    showDialogue("DRAW!!", "Its a draw")
                }
            }
        } else {
            Toast.makeText(context, "Already Selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialogue(title : String, message : String, positiveBtn : String = "Restart", nagetiveBtn :String = "Back", isFromBack : Boolean = false) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                positiveBtn
            ) { dialog, which ->
                if (isFromBack) {
                    dialog.dismiss()
                } else {
                    resetGame()
                }
            }
            .setNegativeButton(
                nagetiveBtn
            ) { dialog, which ->
                resetGame(true)
            }
            .show()
    }

    private fun resetGame(isBack : Boolean = false) {
        viewModel.resetGame()
        binding.apply {
            buttonImage1.setImageDrawable(null)
            buttonImage2.setImageDrawable(null)
            buttonImage3.setImageDrawable(null)
            buttonImage4.setImageDrawable(null)
            buttonImage5.setImageDrawable(null)
            buttonImage6.setImageDrawable(null)
            buttonImage7.setImageDrawable(null)
            buttonImage8.setImageDrawable(null)
            buttonImage9.setImageDrawable(null)
        }
        if(isBack) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack()
        }

    }
}