package ru.syntez

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class IntegrationArtemisMain

fun main(args: Array<String>) {
    SpringApplication.run(IntegrationArtemisMain::class.java, *args)
}
