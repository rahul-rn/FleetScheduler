package com.chargepoint.fleetscheduling

import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.chargers.ChargerStatus
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleType

// Local repository, replace this class with remote fetching repository
class FleetScheduleLocalRepository {
    private var MAX_AVAILABLE_HOURS = 8

    private var chargerItemsList: MutableList<ChargerItem> = mutableListOf()
    private var vehicleItemsList: MutableList<VehicleItem> = mutableListOf()

    init {
        chargerItemsList = mutableListOf(
            ChargerItem(1, 20.0, ChargerStatus.CHARGING),
            ChargerItem(2, 20.0, ChargerStatus.CHARGING),
            ChargerItem(3, 20.0, ChargerStatus.CHARGING)
        )

        vehicleItemsList = mutableListOf(
            VehicleItem(1, 100, 50.0),
            VehicleItem(2, 100, 30.0),
            VehicleItem(3, 100, 70.0),
            VehicleItem(4, 100, 20.0),
            VehicleItem(5, 100, 30.0, VehicleType.VAN),
            VehicleItem(6, 100, 30.0, VehicleType.CAR)
        )
    }


    // Function to fetch chargers from the local source
    fun fetchChargers(): List<ChargerItem> {
        return chargerItemsList
    }

    // Function to fetch vehicles from the local source
    fun fetchVehicles(): List<VehicleItem> {
        return vehicleItemsList
    }

    // Get availabler hours on charging station
    fun availableHours(): Int {
        return MAX_AVAILABLE_HOURS
    }

    // Function to add charger to the local database
    fun addCharger(chargerItem: ChargerItem) {
        chargerItemsList.add(chargerItem)
    }

    // Function to add vehicle to the local database
    fun addVehicle(vehicleItem: VehicleItem) {
        vehicleItemsList.add(vehicleItem)
    }

    companion object {
        @Volatile
        private var INSTANCE: FleetScheduleLocalRepository? = null

        fun getInstance(
        ): FleetScheduleLocalRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FleetScheduleLocalRepository().also { INSTANCE = it }
            }
        }
    }
}