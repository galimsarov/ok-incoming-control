package ru.otus.otuskotlin.incomingControl.common.permissions

enum class IctrlUserPermissions {
    CREATE_OWN,

    READ_OWN,
    READ_GROUP,
    READ_PUBLIC,
    READ_CANDIDATE,

    UPDATE_OWN,
    UPDATE_CANDIDATE,
    UPDATE_PUBLIC,

    DELETE_OWN,
    DELETE_CANDIDATE,
    DELETE_PUBLIC,

    SEARCH_OWN,
    SEARCH_PUBLIC,
    SEARCH_REGISTERED,
    SEARCH_DRAFTS,

    OFFER_FOR_OWN,
}