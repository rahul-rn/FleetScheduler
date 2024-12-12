package com.chargepoint.fleetscheduling.ui.scheduler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chargepoint.fleetscheduling.R
import com.chargepoint.fleetscheduling.databinding.FragmentSchedulerBinding
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.OptimizedFleetScheduler
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.ScheduleManager
import com.chargepoint.fleetscheduling.ui.scheduler.strategy.SequentialFleetScheduler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SchedulerFragment : Fragment() {

    private var _binding: FragmentSchedulerBinding? = null
    private lateinit var viewModel: SchedulerViewModel
    private lateinit var adapter: SchedulerAdapter
    private lateinit var scheduleManager: ScheduleManager
    private lateinit var headerTextView:TextView

    private var optimizedFleetScheduler = OptimizedFleetScheduler()
    private var sequentialFleetScheduler = SequentialFleetScheduler()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchedulerBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this).get(SchedulerViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SchedulerAdapter(emptyList())

        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = adapter

        scheduleManager = ScheduleManager(OptimizedFleetScheduler())
        viewModel.updateFleetSchedule(scheduleManager)

        // Observe the LiveData from ViewModel
        viewModel.schedulerItemsList.observe(viewLifecycleOwner) { schedulerItemsList ->
            adapter.updateList(schedulerItemsList)
        }

        headerTextView = view.findViewById(R.id.headerTextView)
        headerTextView.text = "Optimized Scheduling"



        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            changeScheduleStratey(fab)
        }
    }

    private fun changeScheduleStratey(fab: FloatingActionButton) {
        // Create a PopupMenu anchored to the FAB
        val popupMenu = PopupMenu(context, fab)
        popupMenu.menuInflater.inflate(R.menu.strategy_context_menu, popupMenu.menu)

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.optimized_scheduling -> {
                    // Set optimized scheduling strategy
                    scheduleManager.setStrategy(optimizedFleetScheduler)
                    viewModel.updateFleetSchedule(scheduleManager)
                    headerTextView.text = menuItem.title
                    true
                }

                R.id.sequential_scheduling -> {
                    // Set default scheduling strategy
                    scheduleManager.setStrategy(sequentialFleetScheduler)
                    viewModel.updateFleetSchedule(scheduleManager)
                    headerTextView.text = menuItem.title
                    true
                }
                else -> false
            }
        }
        // Show the menu
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}