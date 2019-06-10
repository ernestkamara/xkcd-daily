package io.kamara.xkcd.daily.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object(Doa) for the [Comic] class.
 */
@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComic(comic: Comic)

    @Query("DELETE FROM comics WHERE comic_id = :comicId")
    fun deleteComicById(comicId: String)

    @Query("SELECT * FROM comics")
    fun getAllComic(): LiveData<List<Comic>>


    @Query("SELECT * FROM comics WHERE comic_id = :comicId")
    fun findComicById(comicId: String): LiveData<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comics: List<Comic>)

    @Query("DELETE FROM comics")
    fun deleteAll()
}