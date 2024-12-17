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

        // Sort vehicles based on charging required.
        // For current dataset: Truck_4 needs 80% charging (current battery state is 20%)
        // Schedule Truck_4 in beginning of queue, prioritize Truck_4 charging
        // Fixed: sortedWith, returns sorted list, the value was not captured in another variable
        val sortedVehicleItemsList = vehicleItemsList.sortedWith(Comparator { t1, t2 ->
            val t1ChargeNeeded = t1.batteryCapacity * (1 - t1.currentBatteryLevel / 100.0)
            val t2ChargeNeeded = t2.batteryCapacity * (1 - t2.currentBatteryLevel / 100.0)
            t2ChargeNeeded.compareTo(t1ChargeNeeded)
        })

        // Initialize the schedule map with charger and empty vehicle lists
        for (charger in chargerItemsList) {
            schedule[charger] = mutableListOf()
        }
        // Assign trucks based on charging required
        // Truck_4 is low on battery, assign it to first charger, Truck_2 is in queue
        val vehicleIterator = sortedVehicleItemsList.toMutableList().iterator()
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