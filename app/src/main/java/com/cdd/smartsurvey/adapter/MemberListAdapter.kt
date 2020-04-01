package com.cdd.smartsurvey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cdd.smartsurvey.R
import com.cdd.smartsurvey.data.model.Member
import kotlinx.android.synthetic.main.list_item_member.view.*

class MemberListAdapter(private val context: Context,
                        private val dataSource: ArrayList<Member>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var txtNo: TextView
        lateinit var txtName: TextView
        lateinit var txtCardID: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_member, parent, false)

            holder = ViewHolder()
            holder.txtNo = view.findViewById(R.id.txtNo)
            holder.txtName = view.findViewById(R.id.txtName)
            holder.txtCardID = view.findViewById(R.id.txtCardID)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val member = getItem(position)
        holder.txtNo.text = "${position + 1}"
        holder.txtName.text = "${member.firstname} ${member.lastname}"
        holder.txtCardID.text = member.idcard

        return view
    }

    override fun getItem(position: Int): Member {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}