package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Currency
import by.mksn.kwitapi.repository.CurrencyRepository
import by.mksn.kwitapi.service.CurrencyService
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CurrencyServiceImpl(
        private val currencyRepository: CurrencyRepository
) : CurrencyService {

    override fun create(currency: Currency): Currency = wrapJPACall {
        currency.id = null
        currencyRepository.save(currency)
    }

    override fun findById(id: Long): Currency? =
            wrapJPACall { currencyRepository.findOne(id) }

    override fun findAll(pageable: Pageable): List<Currency> =
            wrapJPACall { currencyRepository.findAll(pageable).content }

    override fun findByCode(code: String): Currency? =
            wrapJPACall { currencyRepository.findByCode(code) }

    override fun update(id: Long, currency: Currency): Currency = wrapJPACall {
        currency.id = id
        currencyRepository.save(currency)
    }

    override fun delete(id: Long) = wrapJPACall { currencyRepository.delete(id) }
}