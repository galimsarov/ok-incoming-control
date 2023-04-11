package ru.otus.otuskotlin.incomingControl.biz

import ru.otus.otuskotlin.incomingControl.biz.groups.operation
import ru.otus.otuskotlin.incomingControl.biz.groups.stubs
import ru.otus.otuskotlin.incomingControl.biz.workers.*
import ru.otus.otuskotlin.incomingControl.common.IctrlContext
import ru.otus.otuskotlin.incomingControl.common.models.IctrlCommand
import ru.otus.otuskotlin.incomingControl.cor.ICorExec
import ru.otus.otuskotlin.incomingControl.cor.rootChain

class IctrlCommodityProcessor {
    suspend fun exec(ctx: IctrlContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain: ICorExec<IctrlContext> = rootChain {
            initStatus("Инициализация статуса")

            operation("Создание материала", IctrlCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadName("Имитация ошибки валидации наименования")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Получить материал", IctrlCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
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
            }
            operation("Удалить материал", IctrlCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Поиск материалов", IctrlCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }

            }
        }.build()
    }
}