package com.chargepoint.fleetscheduling.ui.scheduler

import com.chargepoint.fleetscheduling.ui.chargers.ChargerStatus

data class SchedulerItem(
    val chargerUid: Int,
    val chargerStatus: ChargerStatus,
    var chargingState: Double,
    var currentChargingVehicle: String,
    val vehiclesList:List<String>,
)
