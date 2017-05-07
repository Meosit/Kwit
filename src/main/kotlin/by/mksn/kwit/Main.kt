package by.mksn.kwit

import by.mksn.kwit.configuration.MainConfiguration
import org.springframework.boot.SpringApplication

fun main(args: Array<String>) {
    SpringApplication.run(MainConfiguration::class.java, *args)
}