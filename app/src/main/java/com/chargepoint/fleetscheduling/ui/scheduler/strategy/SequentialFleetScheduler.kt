package com.chargepoint.fleetscheduling.ui.scheduler.strategy

import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem


class SequentialFleetScheduler : SchedulerStrategy {

    override fun computeSchedule(
        vehicleItemsList: List<VehicleItem>,
        chargerItemsList: List<ChargerItem>,
        availableHours: Double
    ): Map<ChargerItem, List<VehicleItem>> {
        val schedule: MutableMap<ChargerItem, MutableList<VehicleItem>> = mutableMapOf()

        // Initialize the schedule map with charger and empty vehicle lists
        for (charger in chargerItemsList) {
            schedule[charger] = mutableListOf()
        }
        val vehicleIterator = vehicleItemsList.toMutableList().iterator()

        // Assign trucks to chargers
        val maxVehiclePerCharger = vehicleItemsList.size / chargerItemsList.size
        for (charger in chargerItemsList) {
            while (vehicleIterator.hasNext()) {
                val assignedVehicles = schedule[charger]
                if (assignedVehicles != null && assignedVehicles.size <= maxVehiclePerCharger) {
                    assignedVehicles.add(vehicleIterator.next())

                    // Remove vehicle as it assigned to charger
                    vehicleIterator.remove()
                } else {
                    break
                }
            }
        }
        return schedule
    }
}