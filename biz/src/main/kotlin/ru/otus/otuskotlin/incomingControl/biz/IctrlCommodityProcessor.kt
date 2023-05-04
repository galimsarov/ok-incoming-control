package ru.otus.otuskotlin.incomingControl.biz

import ru.otus.otuskotlin.incomingControl.biz.general.initRepo
import ru.otus.otuskotlin.incomingControl.biz.general.operation
import ru.otus.otuskotlin.incomingControl.biz.general.prepareResult
import ru.otus.otuskotlin.incomingControl.biz.general.stubs
import ru.otus.otuskotlin.incomingControl.biz.repo.repoCreate
import ru.otus.otuskotlin.incomingControl.biz.repo.repoPrepareCreate
import ru.otus.otuskotlin.incomingControl.biz.validation.*
import ru.otus.otuskotlin.incomingControl.biz.workers.*
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.IctrlCorSettings
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommodityId
import ru.otus.otuskotlin.incomingControl.cor.ICorExec
import ru.otus.otuskotlin.incomingControl.cor.chain
import ru.otus.otuskotlin.incomingControl.cor.rootChain
import ru.otus.otuskotlin.incomingControl.cor.worker

class IctrlCommodityProcessor(private val settings: IctrlCorSettings = IctrlCorSettings()) {
    suspend fun exec(ctx: IctrlContext) =
        BusinessChain.exec(ctx.apply { settings = this@IctrlCommodityProcessor.settings })

    companion object {
        private val BusinessChain: ICorExec<IctrlContext> = rootChain {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание материала", IctrlCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadName("Имитация ошибки валидации наименования")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в commodityValidating") { commodityValidating = commodityRequest.deepCopy() }
                    worker("Очистка id") { commodityValidating.id = IctrlCommodityId.NONE }
                    worker("Очистка наименования") { commodityValidating.name = commodityValidating.name.trim() }
                    worker("Очистка описания") {
                        commodityValidating.description = commodityValidating.description.trim()
                    }
                    worker("Очистка наименования производителя") {
                        commodityValidating.manufacturer = commodityValidating.manufacturer.trim()
                    }
                    worker("Очистка количества") {
                        commodityValidating.receiptQuantity = commodityValidating.receiptQuantity.trim()
                    }
                    validateNameNotEmpty("Проверка, что наименование не пустое")
                    validateNameHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")
                    validateManufacturerNotEmpty("Проверка, что наименование производителя не пустое")
                    validateManufacturerHasContent("Проверка символов")
                    validateQuantityNotEmpty("Проверка, что количество не пустое")
                    validateQuantityHasContent("Проверка цифр")

                    finishCommodityValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание материала в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить материал", IctrlCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в commodityValidating") { commodityValidating = commodityRequest.deepCopy() }
                    worker("Очистка id") { commodityValidating.id = IctrlCommodityId(commodityValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishCommodityValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить материал", IctrlCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadName("Имитация ошибки валидации наименования")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в commodityValidating") { commodityValidating = commodityRequest.deepCopy() }
                    worker("Очистка id") {
                        commodityValidating.id = IctrlCommodityId(commodityValidating.id.asString().trim())
                    }
                    worker("Очистка наименования") { commodityValidating.name = commodityValidating.name.trim() }
                    worker("Очистка описания") {
                        commodityValidating.description = commodityValidating.description.trim()
                    }
                    worker("Очистка наименования производителя") {
                        commodityValidating.manufacturer = commodityValidating.manufacturer.trim()
                    }
                    worker("Очистка количества") {
                        commodityValidating.receiptQuantity = commodityValidating.receiptQuantity.trim()
                    }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateNameNotEmpty("Проверка на непустое наименование")
                    validateNameHasContent("Проверка на наличие содержания в наименовании")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")
                    validateManufacturerNotEmpty("Проверка, что наименование производителя не пустое")
                    validateManufacturerHasContent("Проверка символов")
                    validateQuantityNotEmpty("Проверка, что количество не пустое")
                    validateQuantityHasContent("Проверка цифр")

                    finishCommodityValidation("Успешное завершение процедуры валидации")
                }


            }
            operation("Удалить материал", IctrlCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { commodityValidating = commodityRequest.deepCopy() }
                    worker("Очистка id") { commodityValidating.id = IctrlCommodityId(commodityValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishCommodityValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск материалов", IctrlCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { commodityFilterValidating = commodityFilterRequest.copy() }

                    finishCommodityFilterValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}