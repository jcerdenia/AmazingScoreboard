package com.joshuacerdenia.amazingscoreboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import java.util.*

const val DEF_SCORE = 0

const val DEF_P1_NAME = "Player 1"
const val DEF_P2_NAME = "Player 2"
const val DEF_P3_NAME = "Player 3"
const val DEF_P4_NAME = "Player 4"
const val DEF_P5_NAME = "Player 5"
const val DEF_P6_NAME = "Player 6"
const val DEF_P7_NAME = "Player 7"
const val DEF_P8_NAME = "Player 8"

const val DEF_P1_COLOR = R.color.colorPlayer1
const val DEF_P2_COLOR = R.color.colorPlayer2
const val DEF_P3_COLOR = R.color.colorPlayer3
const val DEF_P4_COLOR = R.color.colorPlayer4
const val DEF_P5_COLOR = R.color.colorPlayer5
const val DEF_P6_COLOR = R.color.colorPlayer6
const val DEF_P7_COLOR = R.color.colorPlayer7
const val DEF_P8_COLOR = R.color.colorPlayer8

const val PLAYER1_KEY = "$DEF_P1_NAME key"
const val PLAYER2_KEY = "$DEF_P2_NAME key"
const val PLAYER3_KEY = "$DEF_P3_NAME key"
const val PLAYER4_KEY = "$DEF_P4_NAME key"
const val PLAYER5_KEY = "$DEF_P5_NAME key"
const val PLAYER6_KEY = "$DEF_P6_NAME key"
const val PLAYER7_KEY = "$DEF_P7_NAME key"
const val PLAYER8_KEY = "$DEF_P8_NAME key"

abstract class PlayerFragment : Fragment(),
    EditNameFragment.Callbacks,
    EditScoreFragment.Callbacks,
    ResetFragment.Callbacks,
    BoardSelectorFragment.Callbacks,
    LeaderboardFragment.Callbacks {

    private var callbacks: Callbacks? = null

    // prepare SharedPreferences
    private lateinit var nameData: SharedPreferences
    private lateinit var nameDataEditor: SharedPreferences.Editor
    private lateinit var scoreData: SharedPreferences
    private lateinit var scoreDataEditor: SharedPreferences.Editor
    private lateinit var colorData: SharedPreferences
    private lateinit var colorDataEditor: SharedPreferences.Editor

    // player displays (only 1 and 2 are guaranteed at all times)
    lateinit var player1Display: PlayerDisplayView
    lateinit var player2Display: PlayerDisplayView
    var player3Display: PlayerDisplayView? = null
    var player4Display: PlayerDisplayView? = null
    var player5Display: PlayerDisplayView? = null
    var player6Display: PlayerDisplayView? = null
    var player7Display: PlayerDisplayView? = null
    var player8Display: PlayerDisplayView? = null

    // control panel and leaderboard
    lateinit var controlPanel: ControlPanelView
    lateinit var leaderboardButton: ImageButton

    // initial player data
    var player1Score = 0
    var player2Score = 0
    var player3Score = 0
    var player4Score = 0
    var player5Score = 0
    var player6Score = 0
    var player7Score = 0
    var player8Score = 0

    var player1Name = ""
    var player2Name = ""
    var player3Name = ""
    var player4Name = ""
    var player5Name = ""
    var player6Name = ""
    var player7Name = ""
    var player8Name = ""

    var player1Color = 0
    var player2Color = 0
    var player3Color = 0
    var player4Color = 0
    var player5Color = 0
    var player6Color = 0
    var player7Color = 0
    var player8Color = 0

    interface Callbacks {
        fun onNumberOfPlayersChanged(numOfPlayers: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    @SuppressLint("CommitPrefEdits")
    fun prepareSharedPreferences(activity: Context) {
        nameData = activity.getSharedPreferences("NAME_DATA", Context.MODE_PRIVATE)
        nameDataEditor = nameData.edit()

        scoreData = activity.getSharedPreferences("SCORE_DATA", Context.MODE_PRIVATE)
        scoreDataEditor = scoreData.edit()

        colorData = activity.getSharedPreferences("COLOR_DATA", Context.MODE_PRIVATE)
        colorDataEditor = colorData.edit()
    }

    fun loadPlayerData() {
        player1Name = nameData.getString(PLAYER1_KEY, DEF_P1_NAME).toString()
        player1Score = scoreData.getInt(PLAYER1_KEY, DEF_SCORE)
        player1Color = colorData
            .getInt(PLAYER1_KEY, ContextCompat.getColor(context!!, DEF_P1_COLOR))

        player2Name = nameData.getString(PLAYER2_KEY, DEF_P2_NAME).toString()
        player2Score = scoreData.getInt(PLAYER2_KEY, DEF_SCORE)
        player2Color = colorData
            .getInt(PLAYER2_KEY, ContextCompat.getColor(context!!, DEF_P2_COLOR))

        player3Display?.let {
            player3Name = nameData.getString(PLAYER3_KEY, DEF_P3_NAME).toString()
            player3Score = scoreData.getInt(PLAYER3_KEY, DEF_SCORE)
            player3Color = colorData
                .getInt(PLAYER3_KEY, ContextCompat.getColor(context!!, DEF_P3_COLOR))
        }
        player4Display?.let {
            player4Name = nameData.getString(PLAYER4_KEY, DEF_P4_NAME).toString()
            player4Score = scoreData.getInt(PLAYER4_KEY, DEF_SCORE)
            player4Color = colorData
                .getInt(PLAYER4_KEY, ContextCompat.getColor(context!!, DEF_P4_COLOR))
        }
        player5Display?.let {
            player5Name = nameData.getString(PLAYER5_KEY, DEF_P5_NAME).toString()
            player5Score = scoreData.getInt(PLAYER5_KEY, DEF_SCORE)
            player5Color = colorData
                .getInt(PLAYER5_KEY, ContextCompat.getColor(context!!, DEF_P5_COLOR))
        }
        player6Display?.let {
            player6Name = nameData.getString(PLAYER6_KEY, DEF_P6_NAME).toString()
            player6Score = scoreData.getInt(PLAYER6_KEY, DEF_SCORE)
            player6Color = colorData
                .getInt(PLAYER6_KEY, ContextCompat.getColor(context!!, DEF_P6_COLOR))
        }
        player7Display?.let {
            player7Name = nameData.getString(PLAYER7_KEY, DEF_P7_NAME).toString()
            player7Score = scoreData.getInt(PLAYER7_KEY, DEF_SCORE)
            player7Color = colorData
                .getInt(PLAYER7_KEY, ContextCompat.getColor(context!!, DEF_P7_COLOR))
        }
        player8Display?.let {
            player8Name = nameData.getString(PLAYER8_KEY, DEF_P8_NAME).toString()
            player8Score = scoreData.getInt(PLAYER8_KEY, DEF_SCORE)
            player8Color = colorData
                .getInt(PLAYER8_KEY, ContextCompat.getColor(context!!, DEF_P8_COLOR))
        }
    }

    fun swapTwoPlayerData() {
        val swapper = listOf(
            player1Display.getPlayerData(),
            player2Display.getPlayerData()
        )
        player1Display.setPlayerData(swapper[1])
        player2Display.setPlayerData(swapper[0])
    }

    fun shufflePlayerData() {
        var shuffler = mutableListOf(
            player1Display.getPlayerData(),
            player2Display.getPlayerData(),
            player3Display!!.getPlayerData() // this can never be null
        )
        player4Display?.getPlayerData()?.let { shuffler.add(it) }
        player5Display?.getPlayerData()?.let { shuffler.add(it) }
        player6Display?.getPlayerData()?.let { shuffler.add(it) }
        player7Display?.getPlayerData()?.let { shuffler.add(it) }
        player8Display?.getPlayerData()?.let { shuffler.add(it) }

        shuffler = shuffler.shuffled() as MutableList<Player>
        player1Display.setPlayerData(shuffler[0])
        player2Display.setPlayerData(shuffler[1])
        player3Display!!.setPlayerData(shuffler[2])
        player4Display?.setPlayerData(shuffler[3])
        player5Display?.setPlayerData(shuffler[4])
        player6Display?.setPlayerData(shuffler[5])
        player7Display?.setPlayerData(shuffler[6])
        player8Display?.setPlayerData(shuffler[7])
    }

    override fun onPointsSubmitted(key: String, newScore: Int) {
        when (key) {
            PLAYER1_KEY -> player1Display.setScore(newScore)
            PLAYER2_KEY -> player2Display.setScore(newScore)
            PLAYER3_KEY -> player3Display?.setScore(newScore)
            PLAYER4_KEY -> player4Display?.setScore(newScore)
            PLAYER5_KEY -> player5Display?.setScore(newScore)
            PLAYER6_KEY -> player6Display?.setScore(newScore)
            PLAYER7_KEY -> player7Display?.setScore(newScore)
            PLAYER8_KEY -> player8Display?.setScore(newScore)
        }
    }

    override fun onNameSubmitted(key: String, name: String) {
        when (key) {
            PLAYER1_KEY -> player1Display.setName(name)
            PLAYER2_KEY -> player2Display.setName(name)
            PLAYER3_KEY -> player3Display?.setName(name)
            PLAYER4_KEY -> player4Display?.setName(name)
            PLAYER5_KEY -> player5Display?.setName(name)
            PLAYER6_KEY -> player6Display?.setName(name)
            PLAYER7_KEY -> player7Display?.setName(name)
            PLAYER8_KEY -> player8Display?.setName(name)
        }
    }

    override fun onColorSet(key: String, color: Int) {
        when (key) {
            PLAYER1_KEY -> player1Display.setColor(color)
            PLAYER2_KEY -> player2Display.setColor(color)
            PLAYER3_KEY -> player3Display?.setColor(color)
            PLAYER4_KEY -> player4Display?.setColor(color)
            PLAYER5_KEY -> player5Display?.setColor(color)
            PLAYER6_KEY -> player6Display?.setColor(color)
            PLAYER7_KEY -> player7Display?.setColor(color)
            PLAYER8_KEY -> player8Display?.setColor(color)
        }
    }

    override fun onBoardSelected(numOfPlayers: Int) {
        callbacks?.onNumberOfPlayersChanged(numOfPlayers)
    }

    override fun onResetConfirmed(code: Int) {
        when (code) {
            0 -> resetScores()
            1 -> restoreDefaultColors()
            2 -> {
                resetNames()
                resetScores()
                restoreDefaultColors()
            }
        }
    }

    private fun resetNames() {
        nameDataEditor.clear().apply()
        player1Display.setName(DEF_P1_NAME)
        player2Display.setName(DEF_P2_NAME)
        player3Display?.setName(DEF_P3_NAME)
        player4Display?.setName(DEF_P4_NAME)
        player5Display?.setName(DEF_P5_NAME)
        player6Display?.setName(DEF_P6_NAME)
        player7Display?.setName(DEF_P7_NAME)
        player8Display?.setName(DEF_P8_NAME)
    }

    private fun resetScores() {
        scoreDataEditor.clear().apply()
        player1Display.setScore(DEF_SCORE)
        player2Display.setScore(DEF_SCORE)
        player3Display?.setScore(DEF_SCORE)
        player4Display?.setScore(DEF_SCORE)
        player5Display?.setScore(DEF_SCORE)
        player6Display?.setScore(DEF_SCORE)
        player7Display?.setScore(DEF_SCORE)
        player8Display?.setScore(DEF_SCORE)
    }

    private fun restoreDefaultColors() {
        colorDataEditor.clear().apply()
        player1Display.setColor(ContextCompat.getColor(context!!, DEF_P1_COLOR))
        player2Display.setColor(ContextCompat.getColor(context!!, DEF_P2_COLOR))
        player3Display?.setColor(ContextCompat.getColor(context!!, DEF_P3_COLOR))
        player4Display?.setColor(ContextCompat.getColor(context!!, DEF_P4_COLOR))
        player5Display?.setColor(ContextCompat.getColor(context!!, DEF_P5_COLOR))
        player6Display?.setColor(ContextCompat.getColor(context!!, DEF_P6_COLOR))
        player7Display?.setColor(ContextCompat.getColor(context!!, DEF_P7_COLOR))
        player8Display?.setColor(ContextCompat.getColor(context!!, DEF_P8_COLOR))
    }

    fun openLeaderboardDialog(fragment: Fragment) {
        LeaderboardFragment.newInstance(getLeaderboard()).apply {
            setTargetFragment(fragment, 3)
            show(fragment.requireFragmentManager(), "select")
        }
    }

    override fun onGenerateReport() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getReport())
            putExtra(Intent.EXTRA_SUBJECT, getString(
                    R.string.score_summary_subject,
                    getString(R.string.app_name)))
        }.also { intent ->
            val chooserIntent = Intent.createChooser(intent, getString(R.string.send_summary))
            startActivity(chooserIntent)
        }
    }

    private fun getReport(): String {
        val date = Date()
        val report = "$date \n \n${getLeaderboard()}" +
                "\n \n${getString(R.string.thank_you_message, getString(R.string.app_name))}"
        return report.trim()
    }

    private fun getLeaderboard(): String {
        var leaderboard = ""

        val players = mutableMapOf<String, Int>()
        // default names ensure multiple players with the same name are mapped
        players[DEF_P1_NAME] = player1Display.getScore()
        players[DEF_P2_NAME] = player2Display.getScore()
        player3Display?.getScore()?.let { players[DEF_P3_NAME] = it }
        player4Display?.getScore()?.let { players[DEF_P4_NAME] = it }
        player5Display?.getScore()?.let { players[DEF_P5_NAME] = it }
        player6Display?.getScore()?.let { players[DEF_P6_NAME] = it }
        player7Display?.getScore()?.let { players[DEF_P7_NAME] = it }
        player8Display?.getScore()?.let { players[DEF_P8_NAME] = it }

        val playersRanked = players.toList().sortedByDescending { (_, value) -> value }.toMap()
        var i = 0
        for (entry in playersRanked) {
            val playerName = when (entry.key) {
                DEF_P1_NAME -> player1Display.getName()
                DEF_P2_NAME -> player2Display.getName()
                DEF_P3_NAME -> player3Display?.getName()
                DEF_P4_NAME -> player4Display?.getName()
                DEF_P5_NAME -> player5Display?.getName()
                DEF_P6_NAME -> player6Display?.getName()
                DEF_P7_NAME -> player7Display?.getName()
                DEF_P8_NAME -> player8Display?.getName()
                else -> null
            }
            i += 1
            leaderboard += "$i. $playerName: ${entry.value} \n"
        }
        return leaderboard.trim()
    }

    override fun onPause() {
        super.onPause()

        //save all player data
        nameDataEditor
            .putString(PLAYER1_KEY, player1Display.getName())
            .putString(PLAYER2_KEY, player2Display.getName())
            .apply()

        scoreDataEditor
            .putInt(PLAYER1_KEY, player1Display.getScore())
            .putInt(PLAYER2_KEY, player2Display.getScore())
            .apply()

        colorDataEditor
            .putInt(PLAYER1_KEY, player1Display.getColor())
            .putInt(PLAYER2_KEY, player2Display.getColor())
            .apply()

        if (player3Display != null) {
            val player = player3Display!!.getPlayerData()
            nameDataEditor.putString(PLAYER3_KEY, player.name).apply()
            scoreDataEditor.putInt(PLAYER3_KEY, player.score).apply()
            colorDataEditor.putInt(PLAYER3_KEY, player.color).apply()
        }
        if (player4Display != null) {
            val player = player4Display!!.getPlayerData()
            nameDataEditor.putString(PLAYER4_KEY, player.name).apply()
            scoreDataEditor.putInt(PLAYER4_KEY, player.score).apply()
            colorDataEditor.putInt(PLAYER4_KEY, player.color).apply()
        }
        if (player5Display != null) {
            val player = player5Display!!.getPlayerData()
            nameDataEditor.putString(PLAYER5_KEY, player.name).apply()
            scoreDataEditor.putInt(PLAYER5_KEY, player.score).apply()
            colorDataEditor.putInt(PLAYER5_KEY, player.color).apply()
        }
        if (player6Display != null) {
            val player = player6Display!!.getPlayerData()
            nameDataEditor.putString(PLAYER6_KEY, player.name).apply()
            scoreDataEditor.putInt(PLAYER6_KEY, player.score).apply()
            colorDataEditor.putInt(PLAYER6_KEY, player.color).apply()
        }
        if (player7Display != null) {
            val player = player7Display!!.getPlayerData()
            nameDataEditor.putString(PLAYER7_KEY, player.name).apply()
            scoreDataEditor.putInt(PLAYER7_KEY, player.score).apply()
            colorDataEditor.putInt(PLAYER7_KEY, player.color).apply()
        }
        if (player8Display != null) {
            val player = player8Display!!.getPlayerData()
            nameDataEditor.putString(PLAYER8_KEY, player.name).apply()
            scoreDataEditor.putInt(PLAYER8_KEY, player.score).apply()
            colorDataEditor.putInt(PLAYER8_KEY, player.color).apply()
        }
    }
}