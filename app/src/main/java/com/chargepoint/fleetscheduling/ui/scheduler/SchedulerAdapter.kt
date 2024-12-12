package com.chargepoint.fleetscheduling.ui.scheduler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chargepoint.fleetscheduling.R

class SchedulerAdapter(private var schedulerItemList: List<SchedulerItem>) :
    RecyclerView.Adapter<SchedulerAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val status: TextView = itemView.findViewById(R.id.status)
        val currentChargingVehicle: TextView = itemView.findViewById(R.id.currentChargingVehicle)
        val chargingQueue: TextView = itemView.findViewById(R.id.chargingQueue)
        val chargingStatus: ProgressBar = itemView.findViewById(R.id.chargingStatus)
        val chargingStatusText: TextView = itemView.findViewById(R.id.chargingStatusText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.scheduler_item, parent, false)
        return CardViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardItem = schedulerItemList[position]
        holder.title.text = "Charger_" + String.format("%03d", cardItem.chargerUid)
        holder.chargingStatus.isIndeterminate = false
        val chargingStatus = cardItem.chargingState
        holder.chargingStatus.progress = chargingStatus.toInt()
        holder.chargingStatusText.text = "$chargingStatus%"
        holder.status.text = cardItem.chargerStatus.name
        holder.currentChargingVehicle.text = cardItem.currentChargingVehicle
        holder.chargingQueue.text = cardItem.vehiclesList.joinToString(separator = ", ")
    }

    override fun getItemCount(): Int = schedulerItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<SchedulerItem>) {
        schedulerItemList = newList
        notifyDataSetChanged()
    }
}
