package com.cdd.smartsurvey.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cdd.smartsurvey.R
import com.cdd.smartsurvey.sqlite.model.SurveyMetric
import kotlinx.android.synthetic.main.list_item_survey.view.*

class SurveyMetricRecyclerViewAdapter(val surveyMetricList: List<SurveyMetric>,
                                      val clickListener: (View, Int, SurveyMetric) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.list_item_survey, parent, false)
        return SurveyViewHolder(view)
    }

    override fun getItemCount() = surveyMetricList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SurveyViewHolder).bind(surveyMetricList[position], position, clickListener)
    }

    class SurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SurveyMetric, position: Int, clickListener: (View, Int, SurveyMetric) -> Unit) {
            itemView.txtView.text = item.metric_display
            itemView.setOnClickListener { clickListener(itemView, position, item) }
        }
    }

}