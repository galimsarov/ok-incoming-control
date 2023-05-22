package ru.otus.otuskotlin.incomingControl.common.exceptions

import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityLock

class RepoConcurrencyException(expectedLock: IctrlCommodityLock, actualLock: IctrlCommodityLock?) : RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)