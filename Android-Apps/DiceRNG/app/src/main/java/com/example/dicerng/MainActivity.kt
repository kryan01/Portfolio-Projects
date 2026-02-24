package com.example.dicerng

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Views
        val spSides = findViewById<Spinner>(R.id.spSides)
        val spDice = findViewById<Spinner>(R.id.spDice)
        val btnRoll = findViewById<Button>(R.id.btnRoll)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Button click listener
        btnRoll.setOnClickListener {

            // Get number of sides
            val sidesText = spSides.selectedItem.toString()
            val sides = sidesText.filter { it.isDigit() }.toIntOrNull() ?: 6

            // Get number of dice
            val dice = spDice.selectedItem.toString().toIntOrNull() ?: 1

            // Generate rolls
            val rolls = (1..dice).map { Random.nextInt(1, sides + 1) }
            val total = rolls.sum()

            // Display results
            tvResult.text = buildSpannedString {
                bold { append("\nRolls:\n   ") }
                append(rolls.joinToString("     "))
                append("\n\n")
                bold { append("Total:\n   ") }
                append(total.toString())
            }
        }
    }
}
