package com.chargepoint.fleetscheduling.ui.vehicles

enum class VehicleType(val type: String) {
    CAR("Car"),
    TRUCK("Truck"),
    MOTORCYCLE("Motorcycle"),
    BUS("Bus"),
    VAN("Van");
}

data class VehicleItem(
    val uID: Int,
    val batteryCapacity: Int,
    val currentBatteryLevel: Double,
    val type: VehicleType = VehicleType.TRUCK,
)
