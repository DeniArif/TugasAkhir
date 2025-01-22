package com.example.tugasakhir.service

import com.example.tugasakhir.model.Pasien
import retrofit2.Response
import retrofit2.http.*

interface PasienApiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("pasien/all")
    suspend fun getAllPasien(): List<Pasien>

    @GET("pasien/detail.php")
    suspend fun getPasienById(@Path("id") idPasien: String): Pasien

    @POST("pasien/insert.php")
    suspend fun insertPasien(@Body pasien: Pasien)

    @PUT("pasien/update.php")
    suspend fun updatePasien(@Path("id") idPasien: String, @Body pasien: Pasien)

    @DELETE("pasien/delete.php")
    suspend fun deletePasien(@Path("id") idPasien: String): Response<Void>
}
