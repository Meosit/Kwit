package by.mksn.kwitapi.configuration.security

import by.mksn.kwitapi.entity.User
import by.mksn.kwitapi.entity.support.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils


class UserDetails(private val user: User) : org.springframework.security.core.userdetails.UserDetails {

    val userId = user.id!!

    val isAdmin = user.role == UserRole.ADMIN

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
            = if (user.role != null) {
        AuthorityUtils.createAuthorityList(user.role.name)
    } else mutableListOf<GrantedAuthority>()

    override fun getUsername() = user.email

    override fun getPassword() = user.passwordHash

    override fun isEnabled() = !(user.isDeleted)

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

}