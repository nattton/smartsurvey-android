package com.cdd.smartsurvey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cdd.smartsurvey.R
import com.cdd.smartsurvey.http.model.Community

class CommunityAdapter(private val context: Context,
                       private val dataSource: Array<Community>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var textCommunityName: TextView
        lateinit var textPercent: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_community, parent, false)

            holder = ViewHolder()
            holder.textCommunityName = view.findViewById(R.id.textCommunityName)
            holder.textPercent = view.findViewById(R.id.textPercent)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val community = getItem(position)

        holder.textCommunityName.text = community.community_name
        var percent = (community.survey_amount / community.family_amount.toInt()) * 100
        holder.textPercent.text = "${percent}%"
        return view
    }

    override fun getItem(position: Int): Community {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}