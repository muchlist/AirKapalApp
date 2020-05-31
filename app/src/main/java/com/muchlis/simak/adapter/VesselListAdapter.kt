package com.muchlis.simak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muchlis.simak.R
import com.muchlis.simak.datas.output.VesselListDataResponse
import kotlinx.android.synthetic.main.item_vessel_list.view.*

class VesselListAdapter(
    private val context: Context?,
    private val itemPrint: List<VesselListDataResponse.Vessel>,
    private val itemClick: (VesselListDataResponse.Vessel) -> Unit
) : RecyclerView.Adapter<VesselListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_vessel_list, parent, false)
        return ViewHolder(
            view,
            itemClick
        )
    }

    override fun getItemCount(): Int = itemPrint.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemPrint[position])
    }


    class ViewHolder(view: View, val itemClick: (VesselListDataResponse.Vessel) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: VesselListDataResponse.Vessel) {

            itemView.tv_item_container_number.text = items.shipName
            itemView.tv_item_vessel.text = items.agent
            itemView.tv_item_activity.text =
                if (items.isInternational) "INTERNASIONAL" else "DOMESTIK"

            //onClick
            itemView.setOnClickListener { itemClick(items) }
        }


    }
}