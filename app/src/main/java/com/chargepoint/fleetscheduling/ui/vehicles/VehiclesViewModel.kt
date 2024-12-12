package com.chargepoint.fleetscheduling.ui.vehicles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chargepoint.fleetscheduling.FleetScheduleLocalRepository

class VehiclesViewModel : ViewModel() {
    private val _vehicleItemsList = MutableLiveData<List<VehicleItem>>()
    val vehicleItemsList: LiveData<List<VehicleItem>> = _vehicleItemsList

    val repository: FleetScheduleLocalRepository = FleetScheduleLocalRepository.getInstance()

    init {
        // Simulate fetching vehicles data (e.g., from a repository)
        _vehicleItemsList.value = repository.fetchVehicles()
    }

    // Method to add new vehicle item
    fun addItem(newItem: VehicleItem) {
        repository.addVehicle(newItem)
        _vehicleItemsList.value = repository.fetchVehicles()
    }
}