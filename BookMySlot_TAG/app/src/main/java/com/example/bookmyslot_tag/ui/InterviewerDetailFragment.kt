package com.example.bookmyslot_tag.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.bookmyslot_tag.R
import com.example.bookmyslot_tag.model.Interviewer

class InterviewerDetailFragment : Fragment() {

    private val args: InterviewerDetailFragmentArgs by navArgs()

    private lateinit var tvName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvSubject: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnBookSlot: Button
    private lateinit var btnReleaseSlot: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interviewer_details, container, false)

        tvName = view.findViewById(R.id.tvDetailName)
        tvDate = view.findViewById(R.id.tvDetailDate)
        tvTime = view.findViewById(R.id.tvDetailTime)
        tvSubject = view.findViewById(R.id.tvDetailSubject)
        tvStatus = view.findViewById(R.id.tvDetailStatus)
        btnBookSlot = view.findViewById(R.id.btnBookSlot)
        btnReleaseSlot = view.findViewById(R.id.btnReleaseSlot)

        val interviewer = args.selectedInterviewer

        tvName.text = interviewer.name
        tvDate.text = "Date: ${interviewer.date}"
        tvTime.text = "Time: ${interviewer.timeFrom} - ${interviewer.timeTo}"
        tvSubject.text = "Subject: ${interviewer.subject}"
        tvStatus.text = "Status: ${interviewer.status}"

        btnBookSlot.setOnClickListener {
            if (interviewer.status == "Available") {
                interviewer.status = "Booked"
                tvStatus.text = "Status: Booked"
                sendEmail("Booked", interviewer)
                showConfirmationDialog("Slot Booked Successfully!")
            } else {
                Toast.makeText(requireContext(), "Slot already booked!", Toast.LENGTH_SHORT).show()
            }
        }

        btnReleaseSlot.setOnClickListener {
            if (interviewer.status == "Booked") {
                interviewer.status = "Available"
                tvStatus.text = "Status: Available"
                sendEmail("Released", interviewer)
                showConfirmationDialog("Slot Released Successfully!")
            } else {
                Toast.makeText(requireContext(), "Slot is not booked!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun sendEmail(action: String, interviewer: Interviewer) {
        val emailBody = "The interview slot with ${interviewer.name} on ${interviewer.date} from ${interviewer.timeFrom} to ${interviewer.timeTo} has been $action."
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, "Interview Slot $action")
            putExtra(Intent.EXTRA_TEXT, emailBody)
        }
        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    private fun showConfirmationDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}