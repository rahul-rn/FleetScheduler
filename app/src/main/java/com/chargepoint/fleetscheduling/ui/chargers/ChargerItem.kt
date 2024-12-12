package com.chargepoint.fleetscheduling.ui.chargers
enum class ChargerStatus {
    NOT_AVAILABLE,
    AVAILABLE,
    COMPLETE,
    CHARGING,
    BROKEN;
}
data class ChargerItem(val uID: Int, val rate: Double, val status: ChargerStatus)
