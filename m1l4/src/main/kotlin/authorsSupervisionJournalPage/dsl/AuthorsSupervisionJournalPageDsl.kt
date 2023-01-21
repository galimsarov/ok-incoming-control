package ru.otus.otuskotlin.incomingcontrol.m1l4.authorsSupervisionJournalPage.dsl

import ru.otus.otuskotlin.incomingcontrol.m1l4.authorsSupervisionJournalPage.models.AuthorsSupervisionJournalPage
import ru.otus.otuskotlin.incomingcontrol.m1l4.authorsSupervisionJournalPage.models.CompositeLink
import java.util.*

class ObjectStatusContext {
    var statusId: Int = 0
    var changeDate: String = ""
}

class TargetContext {
    var uname: String = ""
    var uid: String = ""
}

class DrawingsContext {
    private val hiddenDrawings: MutableList<CompositeLink> = mutableListOf()

    val drawings: List<CompositeLink>
        get() = hiddenDrawings.toList()

    fun add(uid: String) {
        hiddenDrawings.add(CompositeLink(uid))
    }
}

class SpecificationContext {
    private val hiddenSpecifications: MutableList<CompositeLink> = mutableListOf()

    val specifications: List<CompositeLink>
        get() = hiddenSpecifications.toList()

    fun add(uid: String) {
        hiddenSpecifications.add(CompositeLink(uid))
    }
}

class SignerContext {
    var organizationUid: String = ""
    var personUid: String = ""
}

class ObjectInfoContext {
    var projectUid: String = ""
}

class AuthorsSupervisionJournalPageContext {
    private var uid: String = UUID.randomUUID().toString()
    var name: String = ""
    private var objectStatus: ObjectStatusContext = ObjectStatusContext()
    private var target: TargetContext = TargetContext()
    var pageNumber: String = ""
    var date: String = ""
    var deviations: String = ""
    var corrections: String = ""
    var hasDocumentPackChanges: Boolean = false
    private var drawings: List<CompositeLink> = emptyList()
    private var specifications: List<CompositeLink> = emptyList()
    private var signer: SignerContext = SignerContext()
    private var objectInfo: ObjectInfoContext = ObjectInfoContext()

    @AuthorsSupervisionJournalPageDsl
    fun objectStatus(block: ObjectStatusContext.() -> Unit) {
        val ctx = ObjectStatusContext().apply(block)

        objectStatus.statusId = ctx.statusId
        objectStatus.changeDate = ctx.changeDate
    }

    @AuthorsSupervisionJournalPageDsl
    fun target(block: TargetContext.() -> Unit) {
        val ctx = TargetContext().apply(block)

        target.uid = ctx.uid
        target.uname = ctx.uname
    }

    @AuthorsSupervisionJournalPageDsl
    fun drawings(block: DrawingsContext.() -> Unit) {
        val ctx = DrawingsContext().apply(block)

        drawings = ctx.drawings
    }

    @AuthorsSupervisionJournalPageDsl
    fun specifications(block: SpecificationContext.() -> Unit) {
        val ctx = SpecificationContext().apply(block)

        specifications = ctx.specifications
    }

    @AuthorsSupervisionJournalPageDsl
    fun signer(block: SignerContext.() -> Unit) {
        val ctx = SignerContext().apply(block)

        signer.organizationUid = ctx.organizationUid
        signer.personUid = ctx.personUid
    }

    @AuthorsSupervisionJournalPageDsl
    fun objectInfo(block: ObjectInfoContext.() -> Unit) {
        val ctx = ObjectInfoContext().apply(block)

        objectInfo.projectUid = ctx.projectUid
    }

    fun build() = AuthorsSupervisionJournalPage(
        uid = uid,
        name = name,
        statusId = objectStatus.statusId,
        changeDate = objectStatus.changeDate,
        targetUname = target.uname,
        targetUid = target.uid,
        pageNumber = pageNumber,
        date = date,
        deviations = deviations,
        corrections = corrections,
        hasDocumentPackChanges = hasDocumentPackChanges,
        drawings = drawings,
        specifications = specifications,
        organizationUid = signer.organizationUid,
        personUid = signer.personUid,
        projectUid = objectInfo.projectUid
    )
}

@AuthorsSupervisionJournalPageDsl1
fun buildAuthorsSupervisionJournalPage(block: AuthorsSupervisionJournalPageContext.() -> Unit) =
    AuthorsSupervisionJournalPageContext().apply(block).build()

@DslMarker
annotation class AuthorsSupervisionJournalPageDsl

@DslMarker
annotation class AuthorsSupervisionJournalPageDsl1