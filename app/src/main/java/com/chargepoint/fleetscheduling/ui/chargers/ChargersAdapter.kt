package com.chargepoint.fleetscheduling.ui.chargers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chargepoint.fleetscheduling.R

class ChargersAdapter(private var chargerItemList: List<ChargerItem>) :
    RecyclerView.Adapter<ChargersAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val status: TextView = itemView.findViewById(R.id.status)
        val rate: TextView = itemView.findViewById(R.id.rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.charger_item, parent, false)
        return CardViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardItem = chargerItemList[position]
        holder.titleTextView.text = "Charger_" + cardItem.uID.toString()
        holder.status.text = cardItem.status.name
        holder.rate.text = "$ " + cardItem.rate.toString() + " kWH"
    }

    override fun getItemCount(): Int = chargerItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ChargerItem>) {
        chargerItemList = newList
        notifyDataSetChanged()
    }
}
