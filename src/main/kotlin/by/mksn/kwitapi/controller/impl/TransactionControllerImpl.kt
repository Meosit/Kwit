package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.TransactionController
import by.mksn.kwitapi.entity.Transaction
import by.mksn.kwitapi.service.TransactionService
import org.slf4j.LoggerFactory

open class TransactionControllerImpl(
        transactionService: TransactionService
) : AbstractPersonalCrudController<Transaction>(transactionService, logger), TransactionController {

    companion object {
        private val logger = LoggerFactory.getLogger(TransactionControllerImpl::class.java)!!
    }

}