package ru.otus.otuskotlin.api.v1

import com.fasterxml.jackson.databind.ObjectMapper

val apiV1Mapper = ObjectMapper().apply {
//    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}