package com.chargepoint.fleetscheduling.ui.vehicles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chargepoint.fleetscheduling.R

class VehicleItemsAdapter(private var vehicleItemList: List<VehicleItem>) :
    RecyclerView.Adapter<VehicleItemsAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val capacity: TextView = itemView.findViewById(R.id.capacity)
        val batteryState: TextView = itemView.findViewById(R.id.batteryState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item, parent, false)
        return CardViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val vehicleItem = vehicleItemList[position]
        holder.titleTextView.text = vehicleItem.type.name
        holder.capacity.text = vehicleItem.batteryCapacity.toString() + " kWH"
        holder.batteryState.text = vehicleItem.currentBatteryLevel.toString() + "%"
    }

    override fun getItemCount(): Int = vehicleItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<VehicleItem>) {
        vehicleItemList = newList
        notifyDataSetChanged()
    }
}
