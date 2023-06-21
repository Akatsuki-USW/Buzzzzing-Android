package com.onewx2m.remote

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A [CallAdapter.Factory] which supports suspend + [ApiResult] as the return type
 *
 * Adding this to [Retrofit] will enable you to return [ApiResult] from your service methods.
 *
 * ```kotlin
 * import com.pluu.retrofit.adapter.ApiResult
 * import retrofit2.http.GET
 *
 * data class User(val name: String)
 *
 * interface GitHubService {
 *   @GET("/users/Pluu")
 *   suspend fun getUser(): ApiResult<User>
 *
 *   @GET("/error")
 *   suspend fun tryNetworkError(): ApiResult<User>
 * }
 * ```
 */
class ResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)

        if (returnType !is ParameterizedType) {
            val name = parseTypeName(returnType)
            throw IllegalArgumentException(
                "Return type must be parameterized as " +
                        "$name<Foo> or $name<out Foo>"
            )
        }

        return when (rawType) {
            Call::class.java -> apiResultAdapter(returnType)
            else -> null
        }
    }

    private fun apiResultAdapter(
        returnType: ParameterizedType
    ): CallAdapter<Type, out Call<out Any>>? {
        val wrapperType = getParameterUpperBound(0, returnType)
        return when (getRawType(wrapperType)) {
            ApiResult::class.java -> {
                val bodyType = extractReturnType(wrapperType, returnType)
                ApiResultCallAdapter(bodyType)
            }

            else -> null
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun extractReturnType(
        wrapperType: Type,
        returnType: ParameterizedType
    ): Type {
        if (wrapperType !is ParameterizedType) {
            val name = parseTypeName(returnType)
            throw IllegalArgumentException(
                "Return type must be parameterized as $name<ResponseBody>"
            )
        }
        return getParameterUpperBound(0, wrapperType)
    }
}

private fun parseTypeName(type: Type) =
    type.toString()
        .split(".")
        .last()