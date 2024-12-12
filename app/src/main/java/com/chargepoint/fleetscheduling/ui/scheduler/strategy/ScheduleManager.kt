package com.chargepoint.fleetscheduling.ui.scheduler.strategy

import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem

class ScheduleManager (private var strategy: SchedulerStrategy) {
    fun setStrategy(strategy: SchedulerStrategy) {
        this.strategy = strategy
    }

    fun computeSchedule(
        vehicleItemsList: List<VehicleItem>,
        chargerItemsList: List<ChargerItem>,
        availableHours: Double
    ): Map<ChargerItem, List<VehicleItem>> {
        return strategy.computeSchedule(vehicleItemsList, chargerItemsList, availableHours)
    }
}