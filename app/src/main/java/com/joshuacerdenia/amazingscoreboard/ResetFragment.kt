package com.joshuacerdenia.amazingscoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

private const val RESET_SCORES = 0
private const val RESET_COLORS = 1
private const val RESET_ALL = 2

class ResetFragment : DialogFragment() {

    private lateinit var cancelButton: Button
    private lateinit var resetScoresButton: Button
    private lateinit var resetAllDataButton: Button
    private lateinit var resetColorsButton: Button

    companion object {
        fun newInstance() : ResetFragment {
            return ResetFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val view: View =
            inflater.inflate(R.layout.fragment_reset, container, false)

        cancelButton = view.findViewById(R.id.cancel_button)
        resetScoresButton = view.findViewById(R.id.reset_scores_button)
        resetAllDataButton = view.findViewById(R.id.reset_all_data_button)
        resetColorsButton = view.findViewById(R.id.reset_colors)

        resetScoresButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as ResetFragment.Callbacks).onResetConfirmed(RESET_SCORES)
            }
            dismiss()
        }

        resetColorsButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as ResetFragment.Callbacks).onResetConfirmed(RESET_COLORS)
            }
            dismiss()
        }

        resetAllDataButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as ResetFragment.Callbacks).onResetConfirmed(RESET_ALL)
            }
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    interface Callbacks {
        fun onResetConfirmed(code: Int)
    }
}