package es.raul.productsmicroservice.domain.port.rest

import es.raul.productsmicroservice.adapter.rest.ProductDto
import es.raul.productsmicroservice.domain.model.GetProductsRequestParams
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component


@Component
interface ProductUseCase {
    suspend fun getProducts(params: GetProductsRequestParams): Page<ProductDto>
}

