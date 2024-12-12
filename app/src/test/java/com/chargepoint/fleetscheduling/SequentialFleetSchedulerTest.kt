package com.chargepoint.fleetscheduling

import org.junit.jupiter.api.Assertions.*
import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.chargers.ChargerStatus
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.SequentialFleetScheduler
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleType
import org.junit.jupiter.api.Test

class SequentialFleetSchedulerTest {

    private val scheduler = SequentialFleetScheduler()

    @Test
    fun testSequentialScheduling() {
        // Arrange
        val chargers = listOf(
            ChargerItem(100, 120.0, ChargerStatus.CHARGING),
            ChargerItem(222, 120.0, ChargerStatus.CHARGING),
            ChargerItem(333, 120.0, ChargerStatus.CHARGING)
        )
        val vehicles = listOf(
            VehicleItem(0, 160, 20.0),
            VehicleItem(1, 150, 30.0, VehicleType.VAN),
            VehicleItem(2, 150, 30.0, VehicleType.CAR),
        )
        val availableHours = 10.0

        // Act
        val schedule = scheduler.computeSchedule(vehicles, chargers, availableHours)

        // Assert
        assertEquals(2, schedule[chargers[0]]?.size) // Each charger gets 2 vehicles
        assertEquals(2, schedule[chargers[1]]?.size)
        assertEquals(vehicles.size, schedule.values.sumOf { it.size }) // Total vehicles distributed
    }
//
//    @Test
//    fun `test computeSchedule handles more chargers than vehicles`() {
//        // Arrange
//        val chargers = listOf(
//            ChargerItem("Charger1"),
//            ChargerItem("Charger2"),
//            ChargerItem("Charger3")
//        )
//        val vehicles = listOf(
//            VehicleItem("Vehicle1"),
//            VehicleItem("Vehicle2")
//        )
//        val availableHours = 5.0
//
//        // Act
//        val schedule = scheduler.computeSchedule(vehicles, chargers, availableHours)
//
//        // Assert
//        assertEquals(1, schedule[chargers[0]]?.size) // First charger gets a vehicle
//        assertEquals(1, schedule[chargers[1]]?.size) // Second charger gets a vehicle
//        assertEquals(0, schedule[chargers[2]]?.size) // Third charger gets none
//        assertEquals(vehicles.size, schedule.values.sumOf { it.size }) // Total vehicles distributed
//    }
//
//    @Test
//    fun `test computeSchedule handles more vehicles than chargers`() {
//        // Arrange
//        val chargers = listOf(
//            ChargerItem("Charger1")
//        )
//        val vehicles = listOf(
//            VehicleItem("Vehicle1"),
//            VehicleItem("Vehicle2"),
//            VehicleItem("Vehicle3")
//        )
//        val availableHours = 10.0
//
//        // Act
//        val schedule = scheduler.computeSchedule(vehicles, chargers, availableHours)
//
//        // Assert
//        assertEquals(vehicles.size, schedule[chargers[0]]?.size) // All vehicles assigned to single charger
//        assertEquals(vehicles.size, schedule.values.sumOf { it.size }) // Total vehicles distributed
//    }
//
//    @Test
//    fun `test computeSchedule returns empty schedule when no chargers available`() {
//        // Arrange
//        val chargers = emptyList<ChargerItem>()
//        val vehicles = listOf(
//            VehicleItem("Vehicle1"),
//            VehicleItem("Vehicle2")
//        )
//        val availableHours = 5.0
//
//        // Act
//        val schedule = scheduler.computeSchedule(vehicles, chargers, availableHours)
//
//        // Assert
//        assertTrue(schedule.isEmpty()) // No chargers means no schedule
//    }
//
//    @Test
//    fun `test computeSchedule returns empty schedule when no vehicles available`() {
//        // Arrange
//        val chargers = listOf(
//            ChargerItem("Charger1"),
//            ChargerItem("Charger2")
//        )
//        val vehicles = emptyList<VehicleItem>()
//        val availableHours = 5.0
//
//        // Act
//        val schedule = scheduler.computeSchedule(vehicles, chargers, availableHours)
//
//        // Assert
//        assertTrue(schedule.values.all { it.isEmpty() }) // No vehicles assigned to any charger
//    }
}
