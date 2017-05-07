package by.mksn.kwit.controller

import by.mksn.kwit.entity.Remittance
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/remittances")
interface RemittanceController : CrudController<Remittance, Long> {
}