package by.mksn.kwitapi.controller

import by.mksn.kwitapi.controller.util.PasswordChangeDetails
import by.mksn.kwitapi.controller.util.RegistrationDetails
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
interface AuthController {

        @PutMapping("/register")
        fun register(@Valid @RequestBody registrationDetails: RegistrationDetails)

        @GetMapping("/revoke-token")
        fun logout(request: HttpServletRequest)

        @PostMapping("/change-password")
        fun changePassword(@Valid @RequestBody passwordChangeDetails: PasswordChangeDetails)

}