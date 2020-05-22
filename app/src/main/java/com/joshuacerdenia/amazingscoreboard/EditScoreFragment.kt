package com.joshuacerdenia.amazingscoreboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment

private const val PLAYER_KEY_ARG = "player_key"
private const val PLAYER_NAME_ARG = "player_name"
private const val PLAYER_SCORE_ARG = "player_score"

class EditScoreFragment : DialogFragment() {

    private lateinit var playerName: TextView
    private lateinit var numberField: EditText
    private lateinit var clearButton: ImageButton
    private lateinit var cancelButton: Button
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var overrideButton: Button

    companion object {
        fun newInstance(key: String, name: String, score: Int): EditScoreFragment {
            val args = Bundle().apply {
                putString(PLAYER_KEY_ARG, key)
                putString(PLAYER_NAME_ARG, name)
                putInt(PLAYER_SCORE_ARG, score)
            }

            return EditScoreFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View =
            inflater.inflate(R.layout.fragment_editscore, container, false)

        val key: String? = arguments?.getString(PLAYER_KEY_ARG)
        val name: String? = arguments?.getString(PLAYER_NAME_ARG)
        val score: Int? = arguments?.getInt(PLAYER_SCORE_ARG)
        var pointsEntered = DEF_SCORE

        playerName = view.findViewById(R.id.player_name)
        numberField = view.findViewById(R.id.number_input)
        clearButton = view.findViewById(R.id.clear_button)
        cancelButton = view.findViewById(R.id.cancel_button)
        addButton = view.findViewById(R.id.add_button)
        subtractButton = view.findViewById(R.id.subtract_button)
        overrideButton = view.findViewById(R.id.override_button)

        val pointsWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?, start: Int, count: Int, after: Int
            ) {
                // intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // this one too
            }

            override fun afterTextChanged(sequence: Editable?) {
                if (sequence != null) {
                    if (sequence.count() > 0) {
                        pointsEntered = numberField.text.toString().toInt()
                        clearButton.visibility = View.VISIBLE
                    } else {
                        pointsEntered = DEF_SCORE
                        clearButton.visibility = View.GONE
                    }
                }
            }
        }

        playerName.text = name

        numberField.apply {
            addTextChangedListener(pointsWatcher)
            hint = DEF_SCORE.toString()
        }

        clearButton.apply {
            visibility = View.GONE
            setOnClickListener {
                numberField.text = null
            }
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        overrideButton.setOnClickListener {
            if (pointsEntered > MAX_SCORE) {
                pointsEntered = MAX_SCORE
            }
            if (pointsEntered < MIN_SCORE) {
                pointsEntered = MIN_SCORE
            }
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onPointsSubmitted(key!!, pointsEntered)
            }
            dismiss()
        }

        subtractButton.setOnClickListener {
            var newScore = score?.minus(pointsEntered)
            if (newScore!! < MIN_SCORE) {
                newScore = MIN_SCORE
            }
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onPointsSubmitted(key!!, newScore!!)
            }
            dismiss()
        }

        addButton.setOnClickListener {
            var newScore = score?.plus(pointsEntered)
            if (newScore!! > MAX_SCORE) {
                newScore = MAX_SCORE
            }
            targetFragment?.let { fragment ->
                    (fragment as Callbacks).onPointsSubmitted(key!!, newScore!!)
            }
            dismiss()
        }
        return view
    }

    interface Callbacks {
        fun onPointsSubmitted(key: String, newScore: Int)
    }
}