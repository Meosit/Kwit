package by.mksn.kwitapi.configuration.security

import by.mksn.kwitapi.DEFAULT_ENCODING
import by.mksn.kwitapi.DEFAULT_ROLE
import by.mksn.kwitapi.entity.User
import by.mksn.kwitapi.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.net.URLDecoder

open class JdbcUserDetailsService(
        val userService: UserService
) : UserDetailsService {

    companion object {
        val logger = LoggerFactory.getLogger(JdbcUserDetailsService::class.java)!!
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val decodedEmail = URLDecoder.decode(email, DEFAULT_ENCODING)
        val user = userService.findByEmail(decodedEmail)
        if (user == null || user.isDeleted) {
            logger.info("User with email $decodedEmail not found")
            throw UsernameNotFoundException("User with email $decodedEmail not found")
        }
        return CustomUserDetails(user)
    }

    class CustomUserDetails(user: User) : User(
            user.id,
            user.email,
            user.passwordHash,
            user.salaryDay,
            user.createdAt,
            user.isDeleted
    ), UserDetails {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority>
                = AuthorityUtils.createAuthorityList(DEFAULT_ROLE)

        override fun getUsername() = email

        override fun getPassword() = passwordHash

        override fun isEnabled() = !isDeleted

        override fun isCredentialsNonExpired() = true

        override fun isAccountNonExpired() = true

        override fun isAccountNonLocked() = true

    }

}