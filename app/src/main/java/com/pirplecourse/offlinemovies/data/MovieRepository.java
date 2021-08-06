package com.pirplecourse.offlinemovies.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.google.gson.Gson;
import com.pirplecourse.offlinemovies.app.MyApp;
import com.pirplecourse.offlinemovies.data.local.DAO.MovieDAO;
import com.pirplecourse.offlinemovies.data.local.MovieRoomDatabase;
import com.pirplecourse.offlinemovies.data.local.entity.MovieEntity;
import com.pirplecourse.offlinemovies.data.network.NetworkBoundResource;
import com.pirplecourse.offlinemovies.data.network.Resource;
import com.pirplecourse.offlinemovies.data.remote.ApiConstantes;
import com.pirplecourse.offlinemovies.data.remote.MovieApiService;
import com.pirplecourse.offlinemovies.data.remote.RequestInterceptor;
import com.pirplecourse.offlinemovies.data.remote.model.MoviesResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private final MovieApiService movieApiService;
    private final MovieDAO movieDAO;

    public MovieRepository() {
        //Datos locales > ROOM
        MovieRoomDatabase movieRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                MovieRoomDatabase.class,
                "db_movies"
        ).build();
        movieDAO = movieRoomDatabase.getMovieDAO();

        //RequestInterceptor: incluir en la cabecera de la url de la paticion el token o apikey
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient interceptorCliente = okHttpClientBuilder.build();

        //Remote > retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstantes.BASE_URL)
                .client(interceptorCliente)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieApiService = retrofit.create(MovieApiService.class);
    }

    //obtiene listado de peliculas diferenciando cuando es por internet API externa o sin internet LOCAL
    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        //List<MovieEntity> = devuelve local
        //MoviesResponse = devuelve la API
        return new NetworkBoundResource<List<MovieEntity>,MoviesResponse>(){

            @Override
            protected void saveCallResult(@NonNull @NotNull MoviesResponse item) {
                //este metodo se encarga de almacenar la respuesta del servidor en base de datos local actualizando db
                movieDAO.saveMovies(item.getResults());

            }

            @NonNull
            @NotNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                //este metodo devuelve los datos que dispongamos en ROOM base de datos local
                return movieDAO.loadMovies();
            }

            @NonNull
            @NotNull
            @Override
            protected Call<MoviesResponse> createCall() {
                //este metodo realiza la llamada a la API externa
                return movieApiService.loadPopularMovies();
            }
        }.getAsLiveData();
    }
}
