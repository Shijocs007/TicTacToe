package com.example.tictactoe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.databinding.FragmentHomeBinding
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
        binding.buttonImage5.setImageResource(R.drawable.circle)
        binding.buttonImage1.setImageResource(R.drawable.cross)
    }

    override fun onClick(view: View?) {
        view?.let {

        }
    }
}