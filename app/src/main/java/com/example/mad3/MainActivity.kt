package com.example.mad3

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var scoreTextView: TextView
    var score = 0
    var highScore = 0

    // Shared preferences to store and retrieve the high score
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        scoreTextView = findViewById(R.id.score)

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)

        // Retrieve the high score from shared preferences
        highScore = sharedPreferences.getInt("HighScore", 0)

        // Update the high score text view
        updateHighScoreTextView()

        startBtn.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        mGameView = GameView(this, this)
        mGameView.setBackgroundResource(R.drawable.roadnew)
        rootLayout.addView(mGameView)
        startBtn.visibility = View.GONE
        scoreTextView.visibility = View.GONE
        scoreTextView.text = "Score: 0"
        score = 0 // Reset the score when the game starts
    }

    override fun closeGame(mScore: Int) {
        score = mScore
        if (score > highScore) {
            highScore = score
            // Store the updated high score in shared preferences
            sharedPreferences.edit().putInt("HighScore", highScore).apply()
        }
        // Update the high score text view
        updateHighScoreTextView()

        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        scoreTextView.visibility = View.VISIBLE
        startBtn.text = "Restart" // Change the button text to "Restart"
        startBtn.setOnClickListener {
            startGame() // Restart the game when the button is clicked
        }
    }

    private fun updateHighScoreTextView() {
        scoreTextView.text = "Score: $score\nHigh Score: $highScore"
    }
}

