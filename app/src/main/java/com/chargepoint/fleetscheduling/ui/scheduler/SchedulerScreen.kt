package com.chargepoint.fleetscheduling.ui.scheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SchedulerScreen(
    headerText: String,
    schedulerItems: List<SchedulerItem>,
    onSwitchStrategy: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE)) // Background color similar to the header
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header TextView
            Text(
                text = headerText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFEEEEEE))
            )

            // RecyclerView equivalent
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Use available vertical space
                    .padding(horizontal = 8.dp)
            ) {
                    items(schedulerItems) { item ->
                        SchedulerCard(
                            item
                        )
                    }

            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onSwitchStrategy,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF303F9F) // Holo blue dark
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Replace with the appropriate icon
                contentDescription = "Switch Scheduler strategy",
                tint = Color.White
            )
        }
    }
}
