package by.mksn.kwitapi.configuration.security

import by.mksn.kwitapi.DEFAULT_ENCODING
import by.mksn.kwitapi.DEFAULT_ROLE
import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.net.URLDecoder

class JdbcUserDetailsService(
        val userService: UserService
) : UserDetailsService {

    companion object {
        val logger = LoggerFactory.getLogger(JdbcUserDetailsService::class.java)!!
    }

    override fun loadUserByUsername(email: String): org.springframework.security.core.userdetails.UserDetails {
        val decodedEmail = URLDecoder.decode(email, DEFAULT_ENCODING)
        val user = userService.findByEmail(decodedEmail)
        if (user == null || user.isDeleted) {
            logger.info("User with email $decodedEmail not found")
            throw UsernameNotFoundException("User with email $decodedEmail not found")
        }
        return UserDetails(user)
    }

    class UserDetails(val user: User) : org.springframework.security.core.userdetails.UserDetails {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority>
                = AuthorityUtils.createAuthorityList(DEFAULT_ROLE)

        override fun getUsername() = user.email

        override fun getPassword() = user.passwordHash

        override fun isEnabled() = !user.isDeleted

        override fun isCredentialsNonExpired() = true

        override fun isAccountNonExpired() = true

        override fun isAccountNonLocked() = true

    }

}