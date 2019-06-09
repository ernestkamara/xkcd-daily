package io.kamara.xkcd.daily.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic(comic: Comic)

    @Delete
    fun deleteComic(comic: Comic)

    @Query("SELECT * FROM comics")
    fun getAllComic(): LiveData<List<Comic>>


    @Query("SELECT * FROM comics WHERE comic_id = :comicId")
    fun findComicById(comicId: String): LiveData<Comic>

}