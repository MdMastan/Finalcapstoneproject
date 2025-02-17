package com.example.bookmyslot_tag.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyslot_tag.R
import com.example.bookmyslot_tag.model.Interviewer

class InterviewerAdapter(
    private val interviewerList: List<Interviewer>,
    private val onItemClick: (Interviewer) -> Unit
) : RecyclerView.Adapter<InterviewerAdapter.InterviewerViewHolder>() {

    inner class InterviewerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvInterviewerName)
        private val tvDate: TextView = view.findViewById(R.id.tvDate)
        private val tvTime: TextView = view.findViewById(R.id.tvTime)
        private val tvSubject: TextView = view.findViewById(R.id.tvSubject)
        private val tvStatus: TextView = view.findViewById(R.id.tvStatus)

        fun bind(interviewer: Interviewer) {
            tvName.text = interviewer.name
            tvDate.text = "Date: ${interviewer.date}"
            tvTime.text = "Time: ${interviewer.timeFrom} - ${interviewer.timeTo}"
            tvSubject.text = "Subject: ${interviewer.subject}"
            tvStatus.text = "Status: ${interviewer.status}"

            itemView.setOnClickListener { onItemClick(interviewer) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterviewerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_interviewer, parent, false)
        return InterviewerViewHolder(view)
    }

    override fun onBindViewHolder(holder: InterviewerViewHolder, position: Int) {
        holder.bind(interviewerList[position])
    }

    override fun getItemCount(): Int = interviewerList.size
}