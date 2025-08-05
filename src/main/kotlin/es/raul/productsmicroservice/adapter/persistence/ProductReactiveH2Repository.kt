package es.raul.productsmicroservice.adapter.persistence

import es.raul.productsmicroservice.domain.model.Product
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ProductReactiveH2Repository : CoroutineCrudRepository<Product, String> {
     @Query("SELECT count(sku) FROM product WHERE :category IS NULL OR category = :category")
    fun countByCategory(category: String?): Flux<Long>


}

