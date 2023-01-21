package ru.otus.otuskotlin.incomingcontrol.m1l4.authorsSupervisionJournalPage

import org.junit.Test
import ru.otus.otuskotlin.incomingcontrol.m1l4.authorsSupervisionJournalPage.dsl.buildAuthorsSupervisionJournalPage

class AuthorsSupervisionJournalPageTestCase {
    @Test
    fun `test authors supervision journal page`() {
        val authorsSupervisionJournalPage =
            buildAuthorsSupervisionJournalPage {
                name = "JEAN"
                objectStatus {
                    statusId = 1
                    changeDate = "2023-01-21"
                }
                target {
                    uname = "construction"
                    uid = "construction uid"
                }
                pageNumber = "CLAUDE"
                date = "2023-01-23"
                deviations = "no"
                corrections = "no"
                hasDocumentPackChanges = true
                drawings {
                    add("drawing uid")
                }
                specifications {
                    add("specification uid")
                }
                signer {
                    organizationUid = "organization uid"
                    personUid = "person uid"
                }
                objectInfo {
                    projectUid = "project uid"
                }
            }
        println(authorsSupervisionJournalPage)
    }
}