package com.elementsculmyca.ec19_app.DataSources.LocalServices;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM tb_events")
    List<EventLocalModel> getAll();


    @Query("SELECT COUNT(*) from tb_events")
    int countUsers();

    @Query("DELETE FROM tb_events")
    void deleteAll();

    @Insert
    void insertAll(EventLocalModel... articles);

    @Delete
    void delete(EventLocalModel articles);

}