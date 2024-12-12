package com.chargepoint.fleetscheduling.ui.scheduler.strategy

import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem


class OptimizedFleetScheduler : SchedulerStrategy {

    override fun computeSchedule(
        vehicleItemsList: List<VehicleItem>,
        chargerItemsList: List<ChargerItem>,
        availableHours: Double
    ): Map<ChargerItem, List<VehicleItem>> {
        val schedule: MutableMap<ChargerItem, MutableList<VehicleItem>> = mutableMapOf()

        // Sort the vehicleItemsList based on the charge needed in descending order
        vehicleItemsList.sortedWith(Comparator { t1, t2 ->
            val t1ChargeNeeded = t1.batteryCapacity * (1 - t1.currentBatteryLevel / 100.0)
            val t2ChargeNeeded = t2.batteryCapacity * (1 - t2.currentBatteryLevel / 100.0)
            t2ChargeNeeded.compareTo(t1ChargeNeeded)
        })

        // Initialize the schedule map with charger and empty vehicle lists
        for (charger in chargerItemsList) {
            schedule[charger] = mutableListOf()
        }
        // Assign trucks to chargers
        val vehicleIterator = vehicleItemsList.toMutableList().iterator()
        for (charger in chargerItemsList) {
            var remainingHours = availableHours
            while (vehicleIterator.hasNext()) {
                val vehicle = vehicleIterator.next()

                val chargeNeeded =
                    vehicle.batteryCapacity * (1 - vehicle.currentBatteryLevel / 100.0)
                val maxChargeDeliverable = charger.rate * remainingHours

                if (maxChargeDeliverable >= chargeNeeded) {
                    schedule[charger]?.add(vehicle)
                    remainingHours -= chargeNeeded / charger.rate

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