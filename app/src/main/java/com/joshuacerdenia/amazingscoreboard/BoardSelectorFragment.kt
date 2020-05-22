package com.joshuacerdenia.amazingscoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment

private const val NUM_OF_PLAYERS_ARG = "num_of_players"
private const val MAX_COUNT = 8 // max number of players
private const val MIN_COUNT = 2

class BoardSelectorFragment : DialogFragment() {

    interface Callbacks {
        fun onBoardSelected(numOfPlayers: Int)
    }

    private lateinit var counter: TextView
    private lateinit var plusButton: ImageButton
    private lateinit var minusButton: ImageButton
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    companion object {
        fun newInstance(numOfPlayers: Int): DialogFragment {
            val args = Bundle().apply {
                putInt(NUM_OF_PLAYERS_ARG, numOfPlayers)
            }

            return BoardSelectorFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val view: View =
            inflater.inflate(R.layout.fragment_boardselector, container, false)

        counter = view.findViewById(R.id.counter)
        plusButton = view.findViewById(R.id.plus_button)
        minusButton = view.findViewById(R.id.minus_button)
        okButton = view.findViewById(R.id.ok_button)
        cancelButton = view.findViewById(R.id.cancel_button)

        var numOfPlayers: Int = arguments?.getInt(NUM_OF_PLAYERS_ARG) ?: MIN_COUNT
        counter.text = numOfPlayers.toString()

        plusButton.setOnClickListener {
            numOfPlayers = if (numOfPlayers < MAX_COUNT) {
                numOfPlayers + 1
            } else {
                MIN_COUNT
            }
            counter.text = numOfPlayers.toString()
        }

        minusButton.setOnClickListener {
            numOfPlayers = if (numOfPlayers > MIN_COUNT) {
                numOfPlayers - 1
            } else {
                MAX_COUNT
            }
            counter.text = numOfPlayers.toString()
        }

        okButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onBoardSelected(numOfPlayers)
            }
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }
}