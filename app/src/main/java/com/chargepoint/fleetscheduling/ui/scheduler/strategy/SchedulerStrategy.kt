package com.chargepoint.fleetscheduling.ui.scheduler.strategy

import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem

interface SchedulerStrategy {
     fun computeSchedule(
        vehicleItemsList: List<VehicleItem>,
        chargerItemsList: List<ChargerItem>,
        availableHours: Double
    ): Map<ChargerItem, List<VehicleItem>>
}