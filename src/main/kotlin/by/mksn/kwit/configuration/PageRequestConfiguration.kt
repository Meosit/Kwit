package by.mksn.kwit.configuration

import by.mksn.kwit.support.MAX_PAGE_SIZE
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class PageRequestConfiguration : WebMvcConfigurerAdapter() {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        val resolver = PageableHandlerMethodArgumentResolver()
        resolver.setOneIndexedParameters(true)
        resolver.setFallbackPageable(PageRequest(0, Int.MAX_VALUE))
        resolver.setMaxPageSize(MAX_PAGE_SIZE)
        argumentResolvers.add(resolver)
        super.addArgumentResolvers(argumentResolvers)
    }

}