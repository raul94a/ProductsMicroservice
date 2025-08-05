package es.raul.productsmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = ["es.raul.productsmicroservice.adapter.persistence"])
@ComponentScan(basePackages = arrayOf("es.raul"))
class ProductsMicroserviceApplication

fun main(args: Array<String>) {
    runApplication<ProductsMicroserviceApplication>(*args)
}
