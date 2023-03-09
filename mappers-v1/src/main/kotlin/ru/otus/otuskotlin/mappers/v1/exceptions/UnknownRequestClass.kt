package ru.otus.otuskotlin.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) :
    RuntimeException("Class $clazz cannot be mapped to ru.otus.otuskotlin.incomingcontrol.common.IctrlContext")