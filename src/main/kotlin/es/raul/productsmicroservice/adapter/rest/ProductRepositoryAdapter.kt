package es.raul.productsmicroservice.adapter.rest

import es.raul.productsmicroservice.adapter.persistence.ProductReactiveH2Repository
import es.raul.productsmicroservice.domain.model.GetProductsRequestParams
import es.raul.productsmicroservice.domain.port.persistence.ProductRepositoryPort
import es.raul.productsmicroservice.domain.model.Product
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal


@Component
class ProductRepositoryAdapter(
    @Autowired private val repository: ProductReactiveH2Repository,
    @Autowired private val databaseClient: DatabaseClient
) : ProductRepositoryPort {
    override fun findByCategory(params: GetProductsRequestParams): Flow<Product> {
        val baseSql = "SELECT sku, description, price, category FROM product"
        val category = params.category
        val column = params.sortBy
        val order = params.order
        val page = params.page
        val limit = params.limit
        val offset = page * limit
        val query = if (category != null) {
            val sqlWithWhere = "$baseSql WHERE category = :category ORDER by $column $order LIMIT :limit OFFSET :offset"
            databaseClient.sql(sqlWithWhere)
                .bind("category", category)
                .bind("limit", limit)
                .bind("offset", offset)
        } else {
            val sqlWithoutWhere = "$baseSql ORDER by $column $order LIMIT :limit OFFSET :offset"
            databaseClient.sql(sqlWithoutWhere)
                .bind("limit", limit)
                .bind("offset", offset)
        }
        val data = query
            .map { row ->
                // Taking into account the price is NOT NULL in the database schema
                val priceDouble = row.get("price", java.lang.Float::class.java)!!
                Product(
                    sku = row.get("sku", String::class.java) ?: "",
                    price = BigDecimal(priceDouble.toDouble()),
                    description = row.get("description", String::class.java) ?: "",
                    category = row.get("category", String::class.java) ?: ""
                )
            }
        return data.flow()
    }

    override fun countByCategory(category: String?): Flux<Long> {
        return repository.countByCategory(category)
    }


}