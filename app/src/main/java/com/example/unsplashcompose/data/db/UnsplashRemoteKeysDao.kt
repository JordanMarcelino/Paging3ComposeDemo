package com.example.unsplashcompose.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unsplashcompose.data.model.UnsplashRemoteKeys
import com.example.unsplashcompose.util.Constant.UNSPLASH_REMOTE_KEYS_TABLE

@Dao
interface UnsplashRemoteKeysDao {

    @Query("SELECT * FROM $UNSPLASH_REMOTE_KEYS_TABLE WHERE ID =:id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(images: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM $UNSPLASH_REMOTE_KEYS_TABLE")
    suspend fun deleteRemoteKeys()
}