package com.joshuacerdenia.amazingscoreboard

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class ControlPanelView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var numOfPlayers = 0
    private lateinit var fragment: Fragment

    private var addPlayersButton: ImageButton
    private var resetButton: ImageButton
    var shuffleButton: ImageButton

    init {
        inflate(context, R.layout.view_controlpanel, this)

        addPlayersButton = findViewById(R.id.addPlayers_button)
        shuffleButton = findViewById(R.id.shuffle_button)
        resetButton = findViewById(R.id.reset_button)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        addPlayersButton.setOnClickListener {
            openBoardSelectorDialog(numOfPlayers, fragment)
        }

        resetButton.setOnClickListener {
            openResetDialog(fragment)
        }

        // shuffleButton to be referenced directly by fragment
    }

    fun setRequiredDataForControlPanel(newNumOfPlayers: Int, newFragment: Fragment) {
        numOfPlayers = newNumOfPlayers
        fragment = newFragment
    }

    private fun openBoardSelectorDialog(numOfPlayers: Int, fragment: Fragment) {
        BoardSelectorFragment.newInstance(numOfPlayers).apply {
            setTargetFragment(fragment, 3)
            show(fragment.requireFragmentManager(), "select")
        }
    }

    private fun openResetDialog(fragment: Fragment) {
        ResetFragment.newInstance().apply {
            setTargetFragment(fragment, 2)
            show(fragment.requireFragmentManager(), "reset")
        }
    }
}