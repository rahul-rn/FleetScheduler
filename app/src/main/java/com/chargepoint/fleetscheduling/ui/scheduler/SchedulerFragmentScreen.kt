package com.chargepoint.fleetscheduling.ui.scheduler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.OptimizedFleetScheduler
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.ScheduleManager
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.SequentialFleetScheduler

@Composable
fun SchedulerFragmentScreen(schedulerViewModel: SchedulerViewModel, scheduleManager: ScheduleManager) {
    val schedulerItemList by schedulerViewModel.schedulerItemsList.observeAsState()
    val sequentialScheduler = (scheduleManager.getStrategy() is SequentialFleetScheduler)
    SchedulerScreen(
        headerText = if(sequentialScheduler) "Sequential Scheduler" else "Optimized Scheduling",
        schedulerItems = schedulerItemList ?: listOf(),

        onSwitchStrategy = {
            if (sequentialScheduler) {
                scheduleManager.setStrategy(OptimizedFleetScheduler())
            } else {
                scheduleManager.setStrategy(SequentialFleetScheduler())
            }
            schedulerViewModel.updateFleetSchedule(scheduleManager)
        }
    )
}