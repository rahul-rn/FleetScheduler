package com.chargepoint.fleetscheduling

import com.chargepoint.fleetscheduling.ui.chargers.ChargerItem
import com.chargepoint.fleetscheduling.ui.chargers.ChargerStatus
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.OptimizedFleetScheduler
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.SequentialFleetScheduler
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleItem
import com.chargepoint.fleetscheduling.ui.vehicles.VehicleType
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.jupiter.api.Assertions

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SchedulerUnitTest {
    private val sequentialFleetScheduler = SequentialFleetScheduler()
    private val optimizedFleetScheduler = OptimizedFleetScheduler()

    private lateinit var chargers:List<ChargerItem>
    private lateinit var vehicles:List<VehicleItem>

    private val availableHours = 10.0

    @Before
    fun setup(){
        // Arrange
        chargers = listOf(
            ChargerItem(100, 120.0, ChargerStatus.CHARGING),
            ChargerItem(222, 120.0, ChargerStatus.CHARGING),
            ChargerItem(333, 120.0, ChargerStatus.CHARGING)
        )
        vehicles = listOf(
            VehicleItem(0, 160, 20.0),
            VehicleItem(1, 150, 30.0, VehicleType.VAN),
            VehicleItem(2, 150, 30.0, VehicleType.CAR),
        )
    }

    @Test
    fun testSequentialScheduling() {
        // Act
        val schedule = sequentialFleetScheduler.computeSchedule(vehicles, chargers, availableHours)

        // Assert
        Assertions.assertEquals(2, schedule[chargers[0]]?.size) // Each charger gets 2 vehicles
        Assertions.assertEquals(1, schedule[chargers[1]]?.size)
        Assertions.assertEquals(
            vehicles.size,
            schedule.values.sumOf { it.size }) // Total vehicles distributed
    }

    @Test
    fun testOptimizedScheduling() {
        val schedule = optimizedFleetScheduler.computeSchedule(vehicles, chargers, availableHours)

        // Assert
        assertTrue(schedule[chargers[0]]!!.size > 0) // Each charger should have at most one vehicle
        assertEquals(0, schedule[chargers[0]]?.firstOrNull()?.uID) // Vehicle with the highest need is prioritized
        assertEquals(1, schedule[chargers[0]]?.get(1)?.uID) // Next highest need
    }
}