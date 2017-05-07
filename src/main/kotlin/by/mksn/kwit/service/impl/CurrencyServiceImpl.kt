package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.Currency
import by.mksn.kwit.repository.CurrencyRepository
import by.mksn.kwit.service.CurrencyService
import by.mksn.kwit.service.exception.ServiceException
import by.mksn.kwit.support.wrapJPACall
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
class CurrencyServiceImpl(
        private val currencyRepository: CurrencyRepository
) : CurrencyService {

    override fun create(currency: Currency): Currency = wrapJPACall {
        currency.id = null
        currencyRepository.save(currency)
    }

    override fun findAll(): Iterable<Currency> =
            wrapJPACall { currencyRepository.findAll() }

    override fun findById(id: Long): Currency? =
            wrapJPACall { currencyRepository.findOne(id) }

    override fun findAll(pageable: Pageable): Page<Currency> =
            wrapJPACall { currencyRepository.findAll(pageable) }

    override fun findByCode(code: String): Currency? =
            wrapJPACall { currencyRepository.findByCode(code) }

    override fun update(id: Long, currency: Currency): Currency = wrapJPACall {
        currency.id = id
        currencyRepository.save(currency)
    }

    override fun delete(id: Long) = wrapJPACall { currencyRepository.delete(id) }
}