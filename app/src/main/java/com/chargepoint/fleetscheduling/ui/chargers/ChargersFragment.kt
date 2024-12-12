package com.chargepoint.fleetscheduling.ui.chargers

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chargepoint.fleetscheduling.R
import com.chargepoint.fleetscheduling.databinding.FragmentChargersBinding
import com.chargepoint.fleetscheduling.ui.FleetScheduleUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChargersFragment : Fragment() {

    private var _binding: FragmentChargersBinding? = null
    private lateinit var viewModel: ChargersViewModel
    private lateinit var adapter: ChargersAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChargersBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this).get(ChargersViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChargersAdapter(emptyList())

        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = adapter

        val fab: FloatingActionButton = view.findViewById(R.id.fab)

        // FAB Click Listener
        fab.setOnClickListener {
            FleetScheduleUtils.addChargerInputDialog(requireContext(), onInputReceived = { rate, chargerStatus ->
                viewModel.addItem(
                    ChargerItem(
                        (viewModel.chargersList.value?.size ?: 0) + 1,
                        rate,
                        chargerStatus ?: ChargerStatus.AVAILABLE
                    )
                )
            })
        }
        // Observe the LiveData from ViewModel
        viewModel.chargersList.observe(viewLifecycleOwner) { chargerList ->
            adapter.updateList(chargerList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}