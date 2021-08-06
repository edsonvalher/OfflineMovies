package com.pirplecourse.offlinemovies.data.local.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pirplecourse.offlinemovies.data.local.entity.MovieEntity;

import java.util.List;

//OPERACIONES QUE SE LLEVARÁN A CABO
@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movies")
    LiveData<List<MovieEntity>> loadMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<MovieEntity> movieEntityList);
}
