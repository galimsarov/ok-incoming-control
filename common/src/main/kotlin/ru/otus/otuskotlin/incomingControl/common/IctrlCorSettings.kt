package ru.otus.otuskotlin.incomingControl.common

import ru.otus.otuskotlin.incomingControl.common.repo.ICommodityRepository

data class IctrlCorSettings(
    val repoStub: ICommodityRepository = ICommodityRepository.NONE,
    val repoTest: ICommodityRepository = ICommodityRepository.NONE,
    val repoProd: ICommodityRepository = ICommodityRepository.NONE,
) {
    companion object {
        val NONE = IctrlCorSettings()
    }
}
