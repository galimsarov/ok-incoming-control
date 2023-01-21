package ru.otus.otuskotlin.incomingcontrol.m1l4.authorsSupervisionJournalPage.models

data class AuthorsSupervisionJournalPage(
    val uid: String,
    val name: String,
    val statusId: Int,
    val changeDate: String,
    val targetUname: String,
    val targetUid: String,
    val pageNumber: String,
    val date: String,
    val deviations: String,
    val corrections: String,
    val hasDocumentPackChanges: Boolean,
    val drawings: List<CompositeLink>,
    val specifications: List<CompositeLink>,
    val organizationUid: String,
    val personUid: String,
    val projectUid: String
)
