package com.enerdia.interfaces;

import com.enerdia.json.peticiones.PeticionGeneracionDemandaReal;
import com.enerdia.json.peticiones.PeticionGeneracionDemandaRealTotalGeneracion;
import com.enerdia.json.peticiones.PeticionGeneracionNoRenovable;
import com.enerdia.json.peticiones.PeticionGeneracionRenovable;
import com.enerdia.json.peticiones.PeticionPrecioMedioAnualFinal;
import com.enerdia.json.peticiones.PeticionPrecioMedioAnualFinalContratacionLibre;
import com.enerdia.json.peticiones.PeticionPrecioMedioDiario;
import com.enerdia.json.peticiones.PeticionPrecioMedioMensual;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Interface_API_esios_ree {

    @GET("973")
    Call<PeticionPrecioMedioAnualFinal> precioMedioAnualFinal(@Query("start_date") String start_date,
                                                              @Query("end_date") String end_date,
                                                              @Header("authorization") String authorizationToken
    );

    @GET("947")
    Call<PeticionPrecioMedioAnualFinalContratacionLibre> precioMedioAnualFinalContratacionLibre(@Query("start_date") String start_date,
                                                                                                @Query("end_date") String end_date,
                                                                                                @Header("authorization") String authorizationToken
    );

    @GET("895")
    Call<PeticionPrecioMedioMensual> precioMedioMensual(@Query("start_date") String start_date,
                                                        @Query("end_date") String end_date,
                                                        @Header("authorization") String authorizationToken
    );

    @GET("805")
    Call<PeticionPrecioMedioDiario> precioMedioDiario(@Query("start_date") String start_date,
                                                      @Query("end_date") String end_date,
                                                      @Header("authorization") String authorizationToken);

    @GET("1293")
    Call<PeticionGeneracionDemandaReal> demandaReal(@Query("start_date") String start_date,
                                                    @Query("end_date") String end_date,
                                                    @Query("time_trunc") String time_trunc,
                                                    @Header("authorization") String authorizationToken);

    @GET("10351")
    Call<PeticionGeneracionRenovable> generacionTiempoRealRenovable(@Query("start_date") String start_date,
                                                                    @Query("end_date") String end_date,
                                                                    @Query("time_trunc") String time_trunc,
                                                                    @Header("authorization") String authorizationToken);

    @GET("10352")
    Call<PeticionGeneracionNoRenovable> generacionTiempoRealNoRenovables(@Query("start_date") String start_date,
                                                                         @Query("end_date") String end_date,
                                                                         @Query("time_trunc") String time_trunc,
                                                                         @Header("authorization") String authorizationToken);

    @GET("10004")
    Call<PeticionGeneracionDemandaRealTotalGeneracion> demandaRealSumaGeneracion(@Query("start_date") String start_date,
                                                                                 @Query("end_date") String end_date,
                                                                                 @Query("time_trunc") String time_trunc,
                                                                                 @Header("authorization") String authorizationToken);
}
