package io.kamara.xkcd.daily.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.kamara.xkcd.daily.utils.Constants

@Entity(tableName = Constants.TABLE_COMICS)
data class Comic(
    @ColumnInfo(name = "comic_id") @PrimaryKey @NonNull val num: String,
    @ColumnInfo(name = "comic_title") val title: String?,
    @ColumnInfo(name = "comic_news") val news: String?,
    @ColumnInfo(name = "comic_image_url") val img: String?,
    @ColumnInfo(name = "comic_transcript") val transcript: String?,
    @ColumnInfo(name = "comic_published_day") val day: String?,
    @ColumnInfo(name = "comic_published_month") val month: String?,
    @ColumnInfo(name = "comic_published_year") val year: String?,
    @ColumnInfo(name = "comic_link_url") val link: String?,
    @ColumnInfo(name = "comic_alt") val alt: String?,
    @ColumnInfo(name = "comic_safe_title") val safeTitle: String?,
    @ColumnInfo(name = "comic_favorite_flag") var isFavorite: String? = "0")