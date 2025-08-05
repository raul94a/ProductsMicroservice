package es.raul.productsmicroservice.adapter.rest

import es.raul.productsmicroservice.domain.model.GetProductsRequestParams
import es.raul.productsmicroservice.domain.port.rest.ProductUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    @Autowired private val productUseCase: ProductUseCase
) {

    @GetMapping
    @Operation(summary = "Get products", description = "Retrieve a paginated list of products with optional filtering and sorting")
    suspend fun getProducts(
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "sku") sortBy: String?,
        @RequestParam(required = false, defaultValue = "asc") order: String?
    ): ResponseEntity<Page<ProductDto>> {
        val params = GetProductsRequestParams(
            category = category,
            sortBy = sortBy,
            order = order,
            page = page,
            limit = size
        )
        val products = productUseCase.getProducts(params)
        return ResponseEntity.ok(products)
    }

}