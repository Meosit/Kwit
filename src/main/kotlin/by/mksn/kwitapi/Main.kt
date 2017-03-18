package by.mksn.kwitapi

import by.mksn.kwitapi.configuration.MainConfiguration
import org.springframework.boot.SpringApplication

fun main(args: Array<String>) {
    SpringApplication.run(MainConfiguration::class, *args)
}