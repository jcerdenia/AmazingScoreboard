package com.joshuacerdenia.amazingscoreboard

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), PlayerFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = EightPlayerFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onNumberOfPlayersChanged(numOfPlayers: Int) {
        val fragment = when (numOfPlayers) {
            2 -> TwoPlayerFragment()
            3 -> ThreePlayerFragment()
            4 -> FourPlayerFragment()
            5 -> FivePlayerFragment()
            6 -> SixPlayerFragment()
            7 -> SevenPlayerFragment()
            8 -> EightPlayerFragment()
            else -> throw IllegalArgumentException()
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}