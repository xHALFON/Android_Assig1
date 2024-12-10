package com.example.coursetask1

// Importing files and libraries for the app.
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainMenuLayout: View
    private lateinit var gameLayout: View
    private lateinit var gridLayout: GridLayout
    private lateinit var statusTextView: TextView
    private lateinit var playAgainButton: Button
    private lateinit var goBackButton: Button

    private var gameBoard = Array(3) { arrayOfNulls<String>(3) }
    private var currentPlayer = "X"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        mainMenuLayout = findViewById(R.id.mainMenuLayout)
        gameLayout = findViewById(R.id.gameLayout)
        gridLayout = findViewById(R.id.gridLayout)
        statusTextView = findViewById(R.id.statusTextView)
        playAgainButton = findViewById(R.id.playAgainButton)
        goBackButton = findViewById(R.id.goBackButton)

        // Main menu button to start the game
        findViewById<Button>(R.id.startGameButton).setOnClickListener {
            startNewGame()
        }

        // Play Again button
        playAgainButton.setOnClickListener {
            startNewGame()
        }

        // Go Back to Main Menu after each game button
        goBackButton.setOnClickListener {
            showMainMenu()
        }
    }

    private fun showMainMenu() {
        mainMenuLayout.visibility = View.VISIBLE
        gameLayout.visibility = View.GONE
    }

    private fun showGameLayout() {
        mainMenuLayout.visibility = View.GONE
        gameLayout.visibility = View.VISIBLE
    }
// Starting a new game. Player X's starts all the time, according to the rules of Tic Tac Toe game.
    private fun startNewGame() {
        // Reset the game state
        gameBoard = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer = "X"
        statusTextView.text = "Player X's Turn"
        playAgainButton.visibility = View.GONE
        goBackButton.visibility = View.GONE

        // Clear the board
        initializeGameBoard()
        showGameLayout()
    }

    private fun initializeGameBoard() {
        gridLayout.removeAllViews()

        for (row in 0..2) {
            for (col in 0..2) {
                val button = Button(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 200
                        height = 200
                        rowSpec = GridLayout.spec(row)
                        columnSpec = GridLayout.spec(col)
                        setMargins(5, 5, 5, 5)
                    }
                    textSize = 24f
                    tag = "$row,$col"
                }

                button.setOnClickListener {
                    if (gameBoard[row][col] == null) {
                        gameBoard[row][col] = currentPlayer
                        button.text = currentPlayer
                        button.isEnabled = false

                        if (checkWinner()) {
                            statusTextView.text = "Player $currentPlayer Wins!"
                            showEndGameOptions()
                        } else if (isBoardFull()) {
                            statusTextView.text = "It's a Draw!"
                            showEndGameOptions()
                        } else {
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                            statusTextView.text = "Player $currentPlayer's Turn"
                        }
                    }
                }

                gridLayout.addView(button)
            }
        }
    }
// End of the game
    private fun showEndGameOptions() {
        playAgainButton.visibility = View.VISIBLE
        goBackButton.visibility = View.VISIBLE

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = false
        }
    }
// Checking for the winner
    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (gameBoard[i][0] != null && gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2]) {
                return true
            }
            if (gameBoard[0][i] != null && gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i]) {
                return true
            }
        }
        if (gameBoard[0][0] != null && gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]) {
            return true
        }
        if (gameBoard[0][2] != null && gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]) {
            return true
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        return gameBoard.all { row -> row.all { it != null } }
    }
}
