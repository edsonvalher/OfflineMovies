package com.pirplecourse.offlinemovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pirplecourse.offlinemovies.data.MovieRepository;
import com.pirplecourse.offlinemovies.data.local.entity.MovieEntity;
import com.pirplecourse.offlinemovies.data.network.Resource;

import java.util.List;

//componente que lee del repositorio jugando entre datos locales o del API segun la interface
public class MovieViewModel extends ViewModel {

    private final LiveData<Resource<List<MovieEntity>>> popularMovies;

    private MovieRepository movieRepository;

    public MovieViewModel(){
        movieRepository = new MovieRepository();
        popularMovies = movieRepository.getPopularMovies();
    }
    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        return popularMovies;
    }
}
