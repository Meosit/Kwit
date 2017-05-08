package by.mksn.kwit.controller

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails
import by.mksn.kwit.entity.support.SalaryInfo
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
interface UserController {

        @PostMapping("/register")
        fun register(@Valid @RequestBody registrationDetails: RegistrationDetails)

        @PostMapping("/logout")
        fun logout(principal: OAuth2Authentication)

        @PostMapping("/change-password")
        fun changePassword(@Valid @RequestBody passwordChangeDetails: PasswordChangeDetails)

        @GetMapping("/salary-info")
        fun getSalaryInfo(@Auth auth: UserDetails): SalaryInfo

        @PostMapping("/salary-info")
        fun setSalaryInfo(@Valid @RequestBody salaryInfo: SalaryInfo, @Auth auth: UserDetails)

}