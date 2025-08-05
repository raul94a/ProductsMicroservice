package es.raul.productsmicroservice.domain.service

import es.raul.productsmicroservice.domain.port.persistence.ProductRepositoryPort
import es.raul.productsmicroservice.domain.port.rest.ProductUseCase
import es.raul.productsmicroservice.adapter.rest.ProductDto
import es.raul.productsmicroservice.domain.model.CategoryDiscount
import es.raul.productsmicroservice.domain.model.GetProductsRequestParams
import es.raul.productsmicroservice.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.RoundingMode

@Service
class ProductService(
    @Autowired private val productRepositoryPort: ProductRepositoryPort
) : ProductUseCase {

    val defaultPageSize = 10

    override suspend fun getProducts(
        params: GetProductsRequestParams
    ): Page<ProductDto> = withContext(Dispatchers.Default) {
        val category = params.category
        val sortBy = params.sortBy
        var order = params.order
        val page = params.page
        val size = params.limit

        order = validateOrder(order)
        val (pageArg, sizeArg) = validatePagination(page,size)
        val pageable = PageRequest.of(pageArg, sizeArg, buildSort(sortBy))
        val sort = validateSort(sortBy)
        val productsFlow: Flow<Product> = productRepositoryPort
                .findByCategory(params.copy(sortBy=sort, page = pageArg, limit = sizeArg, order = order))

        val products: List<Product> = productsFlow.toList()
        val totalObjects: Long = productRepositoryPort.countByCategory(category).awaitSingle()

        val productResponses = products.map { product ->
            val (discountedPrice, discountPercent) = CategoryDiscount.getDiscount(product)
            ProductDto(
                sku = product.sku,
                description = product.description,
                category = product.category,
                originalPrice = product.price.setScale(2,RoundingMode.HALF_UP),
                discountedPrice = discountedPrice,
                discountPercentage = discountPercent
            )
        }

        PageImpl(productResponses, pageable, totalObjects)
    }

    private fun buildSort(sortBy: String?): Sort {

        return when (sortBy?.lowercase()) {
            "sku" -> Sort.by("sku")
            "price" -> Sort.by("price")
            "description" -> Sort.by("description")
            "category" -> Sort.by("category")
            else -> Sort.by("sku")
        }
    }
    private fun validateSort(sortBy: String?): String {
        val defaultSort = "sku"
        if (sortBy == null){
            return defaultSort
        }
        return when (sortBy?.lowercase()) {
            "sku" -> "sku"
            "price" -> "price"
            "description" -> "description"
            "category" ->"category"
            else -> defaultSort
        }
    }


    private fun validatePagination(page: Int,size: Int): Pair<PageNumber,PageSize> {
        var pageArg = page
        var sizeArg = size
        if(page < 0){
            pageArg = 0
        }
        if (size <= 0){
            sizeArg = defaultPageSize
        }
        return Pair(pageArg,sizeArg)
    }

    private fun validateOrder(order:String?): String {
        val defaultOrder = "asc"
        val allowedOrders = listOf("desc","asc")
        if (order != null && allowedOrders.contains(order.lowercase())){
            return order
        }
        return defaultOrder
    }

}

typealias PageNumber = Int
typealias PageSize = Int