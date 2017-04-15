package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.RemittanceController
import by.mksn.kwitapi.entity.Remittance
import by.mksn.kwitapi.service.RemittanceService
import org.slf4j.LoggerFactory

open class RemittanceControllerImpl(
        remittanceService: RemittanceService
) : AbstractCrudController<Remittance>(remittanceService, logger), RemittanceController {
    companion object {
        private val logger = LoggerFactory.getLogger(RemittanceControllerImpl::class.java)!!
    }

}