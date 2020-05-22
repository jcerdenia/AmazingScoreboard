package com.joshuacerdenia.amazingscoreboard

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

const val MAX_SCORE = 9999
const val MIN_SCORE = 0

class PlayerDisplayView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var score: Int = DEF_SCORE
    private var playerName: String = ""
    private var color: Int = Color.GRAY //default

    private var playerNameDisplay: TextView
    private var scoreDisplay: TextView
    private var playerBoard: ConstraintLayout
    private var minusZone: FrameLayout
    private lateinit var fragment: Fragment
    private lateinit var key: String

    init {
        inflate(context, R.layout.view_playerdisplay, this)

        scoreDisplay = findViewById(R.id.player_score)
        playerNameDisplay = findViewById(R.id.player_name)
        playerBoard = findViewById(R.id.player_board)
        minusZone = findViewById(R.id.minus_zone)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PlayerDisplayView)
        scoreDisplay.textSize =
            attributes.getFloat(R.styleable.PlayerDisplayView_score_textSize, 104F)
        playerNameDisplay.textSize =
            attributes.getFloat(R.styleable.PlayerDisplayView_name_textSize, 24F)
        attributes.recycle()
        playerBoard.setBackgroundColor(color)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        scoreDisplay.text = 0.toString()
        playerNameDisplay.text = playerName

        playerBoard.apply {
            setOnClickListener {
                addPoint()
            }
            setOnLongClickListener {
                openEditScoreDialog()
                true
            }
        }

        minusZone.apply {
            setOnClickListener {
                subtractPoint()
            }
            setOnLongClickListener {
                openEditScoreDialog()
                true
            }
        }

        playerNameDisplay.setOnClickListener {
            openEditNameDialog()
        }
    }

    private fun openEditNameDialog() {
        EditNameFragment.newInstance(key, playerName, color).apply {
            setTargetFragment(fragment, 1)
            show(fragment.requireFragmentManager(), key)
        }
    }

    private fun openEditScoreDialog() {
        EditScoreFragment.newInstance(key, playerName, score).apply {
            show(fragment.requireFragmentManager(), "tag")
            setTargetFragment(fragment, 0)
        }
    }

    private fun subtractPoint() {
        score -= if (score > MIN_SCORE) {
            1
        } else {
            0
        }
        scoreDisplay.text = score.toString()
    }

    private fun addPoint() {
        score += if (score < MAX_SCORE) {
            1
        } else {
            0
        }
        scoreDisplay.text = score.toString()
    }

    fun setDataRequiredForDialogs(playerKey: String, newFragment: Fragment) {
        key = playerKey
        fragment = newFragment
    }

    fun getName(): String {
        return playerName
    }

    fun setName(newName: String) {
        playerName = newName
        playerNameDisplay.text = playerName
    }

    fun getScore(): Int {
        return score
    }

    fun setScore(newScore: Int) {
        score = newScore
        scoreDisplay.text = score.toString()
    }

    fun setColor(newColor: Int) {
        color = newColor
        playerBoard.setBackgroundColor(color)
    }

    fun getColor(): Int {
        return color
    }
    
    fun getPlayerData(): Player {
        return Player(playerName, score, color)
    }
    
    fun setPlayerData(playerData: Player) {
        setName(playerData.name)
        setScore(playerData.score)
        setColor(playerData.color)
    }
}