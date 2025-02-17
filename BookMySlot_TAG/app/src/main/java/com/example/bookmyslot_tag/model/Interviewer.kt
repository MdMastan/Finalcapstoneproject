package com.example.bookmyslot_tag.model

import java.io.Serializable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Interviewer(
    val name: String,
    val date: String,
    val timeFrom: String,
    val timeTo: String,
    val subject: String,
    var status: String
) : Parcelable
