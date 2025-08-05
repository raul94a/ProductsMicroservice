package es.raul.productsmicroservice.domain.model

data class GetProductsRequestParams(
    val limit: Int,
    val page: Int,
    val category: String?,
    val sortBy: String?,
    val order: String?
)
