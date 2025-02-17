package com.example.bookmyslot_tag.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.setFragmentResult
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

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var interviewerId: String
    private var pendingStatusUpdate: String? = null

    private lateinit var interviewer: Interviewer  // Ensuring interviewer data is available

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interviewer_details, container, false)

        // Initialize UI elements
        tvName = view.findViewById(R.id.tvDetailName)
        tvDate = view.findViewById(R.id.tvDetailDate)
        tvTime = view.findViewById(R.id.tvDetailTime)
        tvSubject = view.findViewById(R.id.tvDetailSubject)
        tvStatus = view.findViewById(R.id.tvDetailStatus)
        btnBookSlot = view.findViewById(R.id.btnBookSlot)
        btnReleaseSlot = view.findViewById(R.id.btnReleaseSlot)

        // Retrieve interviewer object safely
        interviewer = args.selectedInterviewer
        interviewerId = interviewer.name  // Unique identifier for SharedPreferences

        sharedPreferences = requireContext().getSharedPreferences("BookingPrefs", Context.MODE_PRIVATE)

        // Load saved status or use default
        val savedStatus = sharedPreferences.getString(interviewerId, null)
        if (savedStatus != null) {
            interviewer.status = savedStatus
        }

        updateUI(interviewer)

        btnBookSlot.setOnClickListener {
            if (interviewer.status == "Available") {
                interviewer.status = "Booked"
                saveStatus("Booked")
                updateUI(interviewer)
                sendResultToDashboard("Booked")  // Send update to dashboard
                showConfirmationDialog("Slot Booked Successfully!")
            } else {
                Toast.makeText(requireContext(), "Slot already booked!", Toast.LENGTH_SHORT).show()
            }
        }

        btnReleaseSlot.setOnClickListener {
            if (interviewer.status == "Booked") {
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to release this slot?")
                    .setPositiveButton("Yes") { _, _ ->
                        pendingStatusUpdate = "Available"
                        sendEmail("Released", interviewer)
                    }
                    .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                Toast.makeText(requireContext(), "Slot is not booked!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun saveStatus(status: String) {
        sharedPreferences.edit().putString(interviewerId, status).apply()
    }

    private fun updateUI(interviewer: Interviewer) {
        tvName.text = "Name: ${interviewer.name}"
        tvDate.text = "Date: ${interviewer.date}"
        tvTime.text = "Time: ${interviewer.timeFrom} - ${interviewer.timeTo}"
        tvSubject.text = "Subject: ${interviewer.subject}"
        tvStatus.text = "Status: ${interviewer.status}"

        btnBookSlot.isEnabled = interviewer.status == "Available"
        btnReleaseSlot.isEnabled = interviewer.status == "Booked"
    }

    // Email Intent Handling
    private val emailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK && pendingStatusUpdate != null) {
            saveStatus(pendingStatusUpdate!!)
            interviewer.status = pendingStatusUpdate!!  // Update local object
            updateUI(interviewer)
            sendResultToDashboard("Available")  // Send update to dashboard
            showConfirmationDialog("Slot Released Successfully!")
        } else {
            Toast.makeText(requireContext(), "Email canceled. Status remains the same.", Toast.LENGTH_SHORT).show()
        }
        pendingStatusUpdate = null
    }

    private fun sendEmail(action: String, interviewer: Interviewer) {
        val emailBody = "The interview slot with ${interviewer.name} on ${interviewer.date} from ${interviewer.timeFrom} to ${interviewer.timeTo} has been $action."
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, "Interview Slot $action")
            putExtra(Intent.EXTRA_TEXT, emailBody)
        }
        try {
            emailLauncher.launch(Intent.createChooser(intent, "Send Email"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No email app found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConfirmationDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // 🔄 Sending data back to Dashboard for real-time status update
    private fun sendResultToDashboard(updatedStatus: String) {
        setFragmentResult("updateStatus", Bundle().apply {
            putString("interviewerId", interviewerId)
            putString("newStatus", updatedStatus)
        })
    }
}
