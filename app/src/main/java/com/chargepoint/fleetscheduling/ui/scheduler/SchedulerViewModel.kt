package com.chargepoint.fleetscheduling.ui.scheduler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chargepoint.fleetscheduling.FleetScheduleLocalRepository
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.ScheduleManager

class SchedulerViewModel : ViewModel() {

    private val _schedulerItemsList = MutableLiveData<List<SchedulerItem>>()
    val schedulerItemsList: LiveData<List<SchedulerItem>> = _schedulerItemsList
    val repository = FleetScheduleLocalRepository.getInstance()

    fun updateFleetSchedule(scheduleManager: ScheduleManager)  {
        val fleetMap = scheduleManager.computeSchedule(
            repository.fetchVehicles(),
            repository.fetchChargers(),
            8.0
        )
        val schedulerList: MutableList<SchedulerItem> = mutableListOf()

        for ((key, value) in fleetMap) {
            if(value.size > 0) {
                val vehicleItem = value.get(0)
                val chargingVehicleBatteryState = vehicleItem.currentBatteryLevel
                val vehiclesList: MutableList<String> = mutableListOf()
                var currentChargingVehicle = vehicleItem.type.name + String.format("_%03d", vehicleItem.uID)
                for (i in 1..<value.size) {
                    val item = value.get(i)
                    vehiclesList.add(item.type.name + String.format("_%03d", item.uID))
                }
                schedulerList.add(
                    SchedulerItem(
                        key.uID,
                        key.status,
                        chargingVehicleBatteryState,
                        currentChargingVehicle,
                        vehiclesList
                    )
                )
            }
        }
        _schedulerItemsList.value = schedulerList
    }
}