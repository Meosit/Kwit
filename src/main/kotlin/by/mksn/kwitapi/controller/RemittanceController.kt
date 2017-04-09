package by.mksn.kwitapi.controller

import by.mksn.kwitapi.entity.Remittance
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/remittance")
interface RemittanceController : PersonalCrudController<Remittance, Long> {
}