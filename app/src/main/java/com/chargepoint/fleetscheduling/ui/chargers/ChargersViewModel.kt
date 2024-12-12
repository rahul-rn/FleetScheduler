package com.chargepoint.fleetscheduling.ui.chargers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chargepoint.fleetscheduling.FleetScheduleLocalRepository

class ChargersViewModel : ViewModel() {

    private val _chargersList = MutableLiveData<List<ChargerItem>>()
    val chargersList: LiveData<List<ChargerItem>> = _chargersList

    val repository:FleetScheduleLocalRepository = FleetScheduleLocalRepository.getInstance()

    init {
        // Simulate fetching data (e.g., from a repository)
        _chargersList.value = repository.fetchChargers()
    }

    // Method to add a value
    fun addItem(newItem: ChargerItem) {
        repository.addCharger(chargerItem = newItem)
        _chargersList.value = repository.fetchChargers()
    }

    // Update UI/User when any of the charger is available
    // Assumption - Charger will emit signals that charging is complete
    fun updateChargingCompletion() {
        val chargersList = repository.fetchChargers()
        chargersList.forEach { chargerItem ->
            if (chargerItem.status == ChargerStatus.COMPLETE) {
                _chargersList.value = chargersList
            }
        }
    }
}