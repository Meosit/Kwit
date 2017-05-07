package by.mksn.kwit.controller

import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
interface AuthController {

        @PostMapping("/register")
        fun register(@Valid @RequestBody registrationDetails: RegistrationDetails)

        @PostMapping("/logout")
        fun logout(principal: OAuth2Authentication)

        @PostMapping("/change-password")
        fun changePassword(@Valid @RequestBody passwordChangeDetails: PasswordChangeDetails)


}