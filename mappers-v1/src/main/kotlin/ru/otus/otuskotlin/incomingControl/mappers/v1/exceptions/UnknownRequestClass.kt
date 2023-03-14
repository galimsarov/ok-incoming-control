package ru.otus.otuskotlin.incomingControl.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) :
    RuntimeException("Class $clazz cannot be mapped to IctrlContext")