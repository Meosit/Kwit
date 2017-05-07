package by.mksn.kwit.controller

import by.mksn.kwit.entity.Transaction
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/transactions")
interface TransactionController : CrudController<Transaction, Long> {


}