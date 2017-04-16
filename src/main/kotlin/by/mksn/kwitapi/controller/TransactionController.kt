package by.mksn.kwitapi.controller

import by.mksn.kwitapi.entity.Transaction
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
interface TransactionController : CrudController<Transaction, Long> {
}