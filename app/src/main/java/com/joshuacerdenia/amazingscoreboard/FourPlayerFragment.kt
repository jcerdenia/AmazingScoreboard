package com.joshuacerdenia.amazingscoreboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val NUM_OF_PLAYERS = 4

class FourPlayerFragment : PlayerFragment() {

    private val fragment = this@FourPlayerFragment
    private val layout = R.layout.fragment_fourplayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layout, container, false)

        player1Display = view.findViewById(R.id.player_1)
        player2Display = view.findViewById(R.id.player_2)
        player3Display = view.findViewById(R.id.player_3)
        player4Display = view.findViewById(R.id.player_4)
        controlPanel = view.findViewById(R.id.control_panel)
        leaderboardButton = view.findViewById(R.id.leaderboard_button)

        return view
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()

        this.activity?.let { prepareSharedPreferences(it) }
        loadPlayerData()

        player1Display.apply {
            setPlayerData(Player(player1Name, player1Score, player1Color))
            setDataRequiredForDialogs(PLAYER1_KEY, fragment)
        }
        player2Display.apply {
            setPlayerData(Player(player2Name, player2Score, player2Color))
            setDataRequiredForDialogs(PLAYER2_KEY, fragment)
        }
        player3Display?.apply {
            setPlayerData(Player(player3Name, player3Score, player3Color))
            setDataRequiredForDialogs(PLAYER3_KEY, fragment)
        }
        player4Display?.apply {
            setPlayerData(Player(player4Name, player4Score, player4Color))
            setDataRequiredForDialogs(PLAYER4_KEY, fragment)
        }

        controlPanel.apply {
            setRequiredDataForControlPanel(NUM_OF_PLAYERS, fragment)
            shuffleButton.setOnClickListener {
                shufflePlayerData()
            }
        }

        leaderboardButton.setOnClickListener {
            openLeaderboardDialog(fragment)
        }
    }
}