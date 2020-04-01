package com.cdd.smartsurvey.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cdd.smartsurvey.R
import com.cdd.smartsurvey.data.model.Family
import kotlinx.android.synthetic.main.list_item_waiting.view.*

class WaitingRecyclerViewAdapter(val familyItemList: List<Family>,
                                 val clickListener: (View, Int, Family) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.list_item_waiting, parent, false)
        return FamilyViewHolder(view)
    }

    override fun getItemCount() = familyItemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FamilyViewHolder).bind(familyItemList[position], position, clickListener)
    }

    class FamilyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Family, position: Int, clickListener: (View, Int, Family) -> Unit) {
            itemView.txtCardID.text = item.idcard
            itemView.txtName.text = "${item.fname} ${item.lname}"
            itemView.setOnClickListener { clickListener(itemView, position, item) }
            itemView.btnUpload.setOnClickListener { clickListener(itemView.btnUpload, position, item) }
            itemView.btnDelete.setOnClickListener { clickListener(itemView.btnDelete, position, item) }
        }
    }

}