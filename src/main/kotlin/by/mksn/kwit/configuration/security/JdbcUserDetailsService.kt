package by.mksn.kwit.configuration.security

import by.mksn.kwit.repository.UserRepository
import by.mksn.kwit.support.DEFAULT_ENCODING
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.net.URLDecoder

class JdbcUserDetailsService(
        val userRepository: UserRepository
) : UserDetailsService {

    companion object {
        val logger = LoggerFactory.getLogger(JdbcUserDetailsService::class.java)!!
    }

    override fun loadUserByUsername(email: String): org.springframework.security.core.userdetails.UserDetails {
        val decodedEmail = URLDecoder.decode(email, DEFAULT_ENCODING)
        val user = userRepository.findByEmail(decodedEmail)
        if (user == null || user.isDeleted) {
            logger.info("User with email $decodedEmail not found")
            throw UsernameNotFoundException("User with email $decodedEmail not found")
        }
        return UserDetails(user)
    }
}

