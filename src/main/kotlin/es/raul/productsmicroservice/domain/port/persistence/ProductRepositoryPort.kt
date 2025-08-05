package es.raul.productsmicroservice.domain.port.persistence

import es.raul.productsmicroservice.domain.model.GetProductsRequestParams
import es.raul.productsmicroservice.domain.model.Product
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Flux

interface ProductRepositoryPort {
    fun findByCategory(params: GetProductsRequestParams): Flow<Product>
    fun countByCategory(category: String?): Flux<Long>
}