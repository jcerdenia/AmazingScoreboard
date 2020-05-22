package com.joshuacerdenia.amazingscoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

private const val REPORT_ARG = "report"

class LeaderboardFragment : DialogFragment() {

    private lateinit var leaderboard: TextView
    private lateinit var backButton: Button
    private lateinit var sendButton: Button

    companion object {
        fun newInstance(ranking: String): LeaderboardFragment {
            val args = Bundle().apply {
                putString(REPORT_ARG, ranking)
            }

            return LeaderboardFragment().apply {
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
            inflater.inflate(R.layout.fragment_leaderboard, container, false)

        leaderboard = view.findViewById(R.id.leaderBoard)
        backButton = view.findViewById(R.id.back_button)
        sendButton = view.findViewById(R.id.send_button)

        val ranking: String? = arguments?.getString(REPORT_ARG)

        leaderboard.text = ranking

        backButton.setOnClickListener {
            dismiss()
        }

        sendButton.setOnClickListener {
            targetFragment?.let { fragment ->
            (fragment as Callbacks).onGenerateReport()
            }
            dismiss()
        }

        return view
    }

    interface Callbacks {
        fun onGenerateReport()
    }
}