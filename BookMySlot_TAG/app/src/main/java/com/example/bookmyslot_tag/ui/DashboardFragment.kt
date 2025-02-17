package com.example.bookmyslot_tag.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmyslot_tag.R
import com.example.bookmyslot_tag.adapter.InterviewerAdapter
import com.example.bookmyslot_tag.model.Interviewer

class DashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var interviewerAdapter: InterviewerAdapter
    private val interviewerList = mutableListOf<Interviewer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        populateInterviewerList()
        interviewerAdapter = InterviewerAdapter(interviewerList) { selectedInterviewer ->
            val action = DashboardFragmentDirections
                .actionDashboardFragmentToInterviewerDetailFragment(selectedInterviewer)
            findNavController().navigate(action)
        }
        recyclerView.adapter = interviewerAdapter

        return view
    }

    private fun populateInterviewerList() {
        interviewerList.add(Interviewer("Michael Johnson", "2025-03-01", "09:00 AM", "10:00 AM", "C++", "Available"))
        interviewerList.add(Interviewer("Emma Williams", "2025-03-02", "10:30 AM", "11:30 AM", "Swift", "Booked"))
        interviewerList.add(Interviewer("Robert Miller", "2025-03-03", "01:00 PM", "02:00 PM", "JavaScript", "Available"))
        interviewerList.add(Interviewer("Sophia Wilson", "2025-03-04", "02:30 PM", "03:30 PM", "Go", "Available"))
        interviewerList.add(Interviewer("David Anderson", "2025-03-05", "03:45 PM", "04:45 PM", "Ruby", "Booked"))
        interviewerList.add(Interviewer("Olivia Martinez", "2025-03-06", "09:15 AM", "10:15 AM", "PHP", "Available"))
        interviewerList.add(Interviewer("James Thomas", "2025-03-07", "11:00 AM", "12:00 PM", "C#", "Booked"))
        interviewerList.add(Interviewer("Isabella Garcia", "2025-03-08", "01:45 PM", "02:45 PM", "Rust", "Available"))
        interviewerList.add(Interviewer("William Robinson", "2025-03-09", "04:00 PM", "05:00 PM", "Perl", "Available"))
        interviewerList.add(Interviewer("Mia Clark", "2025-03-10", "08:30 AM", "09:30 AM", "Scala", "Booked"))
        interviewerList.add(Interviewer("Benjamin Lewis", "2025-03-11", "10:00 AM", "11:00 AM", "Dart", "Available"))
        interviewerList.add(Interviewer("Charlotte Walker", "2025-03-12", "12:30 PM", "01:30 PM", "TypeScript", "Available"))
        interviewerList.add(Interviewer("Ethan Hall", "2025-03-13", "03:00 PM", "04:00 PM", "Kotlin", "Booked"))
        interviewerList.add(Interviewer("Ava Allen", "2025-03-14", "05:00 PM", "06:00 PM", "Python", "Available"))
        interviewerList.add(Interviewer("Alexander Young", "2025-03-15", "09:45 AM", "10:45 AM", "R", "Available"))
        interviewerList.add(Interviewer("Emily King", "2025-03-16", "11:15 AM", "12:15 PM", "Matlab", "Booked"))
        interviewerList.add(Interviewer("Daniel Wright", "2025-03-17", "02:00 PM", "03:00 PM", "Shell", "Available"))
        interviewerList.add(Interviewer("Harper Scott", "2025-03-18", "04:30 PM", "05:30 PM", "HTML/CSS", "Available"))
        interviewerList.add(Interviewer("Henry Green", "2025-03-19", "07:00 AM", "08:00 AM", "SQL", "Booked"))
        interviewerList.add(Interviewer("Abigail Adams", "2025-03-20", "08:45 AM", "09:45 AM", "Haskell", "Available"))
        interviewerList.add(Interviewer("Matthew Nelson", "2025-03-21", "10:30 AM", "11:30 AM", "Lua", "Available"))
        interviewerList.add(Interviewer("Ella Carter", "2025-03-22", "12:45 PM", "01:45 PM", "Objective-C", "Booked"))
        interviewerList.add(Interviewer("Liam Baker", "2025-03-23", "03:15 PM", "04:15 PM", "F#", "Available"))
        interviewerList.add(Interviewer("Madison Gonzalez", "2025-03-24", "05:45 PM", "06:45 PM", "Fortran", "Available"))
        interviewerList.add(Interviewer("Noah Perez", "2025-03-25", "09:00 AM", "10:00 AM", "COBOL", "Booked"))
        interviewerList.add(Interviewer("Lily Rivera", "2025-03-26", "10:30 AM", "11:30 AM", "VB.NET", "Available"))
        interviewerList.add(Interviewer("Mason Torres", "2025-03-27", "01:00 PM", "02:00 PM", "Julia", "Available"))
        interviewerList.add(Interviewer("Scarlett Ramirez", "2025-03-28", "02:30 PM", "03:30 PM", "Ada", "Booked"))
        interviewerList.add(Interviewer("Lucas Flores", "2025-03-29", "03:45 PM", "04:45 PM", "Erlang", "Available"))
        interviewerList.add(Interviewer("Grace Lee", "2025-03-30", "09:15 AM", "10:15 AM", "Prolog", "Available"))
        interviewerList.add(Interviewer("Jackson Diaz", "2025-03-31", "11:00 AM", "12:00 PM", "Racket", "Booked"))
        interviewerList.add(Interviewer("Sofia Hernandez", "2025-04-01", "01:45 PM", "02:45 PM", "Scheme", "Available"))
        interviewerList.add(Interviewer("Aiden Martinez", "2025-04-02", "04:00 PM", "05:00 PM", "Elixir", "Available"))
        interviewerList.add(Interviewer("Chloe Robinson", "2025-04-03", "08:30 AM", "09:30 AM", "D", "Booked"))
        interviewerList.add(Interviewer("Carter Wilson", "2025-04-04", "10:00 AM", "11:00 AM", "Smalltalk", "Available"))
        interviewerList.add(Interviewer("Zoe Scott", "2025-04-05", "12:30 PM", "01:30 PM", "Tcl", "Available"))
        interviewerList.add(Interviewer("Logan Hall", "2025-04-06", "03:00 PM", "04:00 PM", "Lisp", "Booked"))
        interviewerList.add(Interviewer("Penelope Adams", "2025-04-07", "05:00 PM", "06:00 PM", "Modula-2", "Available"))
        interviewerList.add(Interviewer("Gabriel Wright", "2025-04-08", "09:45 AM", "10:45 AM", "OCaml", "Available"))

    }
}