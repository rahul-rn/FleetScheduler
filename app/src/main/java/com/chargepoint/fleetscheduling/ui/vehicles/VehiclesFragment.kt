package com.chargepoint.fleetscheduling.ui.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chargepoint.fleetscheduling.R
import com.chargepoint.fleetscheduling.databinding.FragmentVehiclesBinding
import com.chargepoint.fleetscheduling.ui.FleetScheduleUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VehiclesFragment : Fragment() {

    private var _binding: FragmentVehiclesBinding? = null
    private lateinit var viewModel: VehiclesViewModel
    private lateinit var adapter: VehicleItemsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(VehiclesViewModel::class.java)
        _binding = FragmentVehiclesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VehicleItemsAdapter(emptyList())

        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = adapter

        val fab: FloatingActionButton = view.findViewById(R.id.fab)

        // FAB Click Listener
        fab.setOnClickListener {
            FleetScheduleUtils.addVehicleInputDialog(
                requireContext(),
                onInputReceived = { capacity, batteryState ->
                    viewModel.addItem(
                        VehicleItem(
                            uID = (viewModel.vehicleItemsList.value?.size ?: 0) + 1,
                            batteryCapacity = capacity,
                            currentBatteryLevel = batteryState
                        )
                    )
                })
        }

        // Observe the LiveData from ViewModel
        viewModel.vehicleItemsList.observe(viewLifecycleOwner) { vehicleItemsList ->
            adapter.updateList(vehicleItemsList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}