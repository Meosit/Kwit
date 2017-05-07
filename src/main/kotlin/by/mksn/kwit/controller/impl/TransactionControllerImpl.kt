package by.mksn.kwit.controller.impl

import by.mksn.kwit.controller.TransactionController
import by.mksn.kwit.entity.Transaction
import by.mksn.kwit.service.TransactionService
import org.slf4j.LoggerFactory

open class TransactionControllerImpl(
        transactionService: TransactionService
) : AbstractPersonalCrudController<Transaction>(transactionService, logger), TransactionController {

    companion object {
        private val logger = LoggerFactory.getLogger(TransactionControllerImpl::class.java)!!
    }

}