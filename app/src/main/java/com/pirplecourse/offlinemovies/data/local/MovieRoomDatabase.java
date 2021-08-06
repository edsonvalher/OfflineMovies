package com.pirplecourse.offlinemovies.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pirplecourse.offlinemovies.data.local.DAO.MovieDAO;
import com.pirplecourse.offlinemovies.data.local.entity.MovieEntity;

//definicion del objeto DAO

@Database(entities = {MovieEntity.class},version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {
    //declarar objeto DAO

    public abstract MovieDAO getMovieDAO();

}
