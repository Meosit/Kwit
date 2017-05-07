package by.mksn.kwit.controller.impl

import by.mksn.kwit.controller.RemittanceController
import by.mksn.kwit.entity.Remittance
import by.mksn.kwit.service.RemittanceService
import org.slf4j.LoggerFactory

open class RemittanceControllerImpl(
        remittanceService: RemittanceService
) : AbstractPersonalCrudController<Remittance>(remittanceService, logger), RemittanceController {
    companion object {
        private val logger = LoggerFactory.getLogger(RemittanceControllerImpl::class.java)!!
    }

}