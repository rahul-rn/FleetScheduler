package com.chargepoint.fleetscheduling.ui

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import com.chargepoint.fleetscheduling.ui.chargers.ChargerStatus

class FleetScheduleUtils {
    companion object {
        private fun showInputDialog(
            context: Context,
            title: String,
            inputHints: List<String>,
            inputTypes: List<Int>,
            onInputReceived: (List<String>) -> Unit
        ) {
            // Create a LinearLayout to hold EditTexts
            val linearLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
            }

            // Create EditTexts dynamically based on inputHints and inputTypes
            val editTexts = inputHints.mapIndexed { index, hint ->
                EditText(context).apply {
                    this.hint = hint
                    this.inputType = inputTypes[index]
                    linearLayout.addView(this)
                }
            }

            // Create the dialog
            val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setView(linearLayout)
                .setPositiveButton("OK") { _, _ ->
                    // Collect the input values
                    val inputs = editTexts.map { it.text.toString() }
                    onInputReceived(inputs)
                }
                .setNegativeButton("Cancel", null)
                .create()

            // Show the dialog
            dialog.show()
        }

        // Usage for Charger Input Dialog
        fun addChargerInputDialog(context: Context, onInputReceived: (Double, ChargerStatus?) -> Unit) {
            showInputDialog(
                context,
                "Add Charger",
                listOf("Enter Rate", "Enter Status"),
                listOf(InputType.TYPE_CLASS_NUMBER, InputType.TYPE_CLASS_TEXT)
            ) { inputs ->
                val rate = inputs[0].toDoubleOrNull() ?: 0.0
                val status = ChargerStatus.entries.firstOrNull { it.name.equals(inputs[1], ignoreCase = true) }
                onInputReceived(rate, status)
            }
        }

        // Usage for Vehicle Input Dialog
        fun addVehicleInputDialog(context: Context, onInputReceived: (Int, Double) -> Unit) {
            showInputDialog(
                context,
                "Add Vehicle",
                listOf("Enter Capacity", "Enter Battery State"),
                listOf(InputType.TYPE_CLASS_NUMBER, InputType.TYPE_CLASS_NUMBER)
            ) { inputs ->
                val capacity = inputs[0].toIntOrNull() ?: 0
                val batteryState = inputs[1].toDoubleOrNull() ?: 0.0
                onInputReceived(capacity, batteryState)
            }
        }
    }
}