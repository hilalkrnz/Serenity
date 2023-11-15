package com.example.serenity.data.network

import com.example.serenity.data.dto.AddToCartBody
import com.example.serenity.data.dto.BaseResponse
import com.example.serenity.data.dto.CategoriesResponse
import com.example.serenity.data.dto.ClearAllProductBody
import com.example.serenity.data.dto.ClearProductBody
import com.example.serenity.data.dto.ProductDetailResponse
import com.example.serenity.data.dto.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SerenityService {

    @GET("get_products.php")
    suspend fun getAllProduct(): Response<ProductResponse>

    @GET("get_product_detail.php")
    suspend fun getProductById(@Query("id") id: Int): Response<ProductDetailResponse>

    @GET("get_categories.php")
    suspend fun getCategories() : Response<CategoriesResponse>

    @GET("get_products_by_category.php")
    suspend fun getProductsByCategory(@Query("category") category: String): Response<ProductResponse>

    @POST("add_to_cart.php")
    suspend fun addProductToCart(@Body addToCartBody: AddToCartBody): Response<BaseResponse>

    @POST("delete_from_cart.php")
    suspend fun deleteProductFromCart(@Body clearProductBody: ClearProductBody): Response<BaseResponse>

    @POST("clear_cart.php")
    suspend fun clearAllProductFromCart(@Body clearAllBody: ClearAllProductBody): Response<BaseResponse>

    @GET("get_cart_products.php")
    suspend fun getProductsFromCart(@Query("userId") userId: String): Response<ProductResponse>

    @GET("search_product.php")
    suspend fun searchProduct(@Query("query") searchProduct: String): Response<ProductResponse>
}