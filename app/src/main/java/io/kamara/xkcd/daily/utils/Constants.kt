package io.kamara.xkcd.daily.utils

/**
 * Constants used throughout the app
 */
class Constants{
    companion object{
        const val DATABASE_NAME = "comics-db"
        const val TABLE_COMICS = "comics"
        const val TABLE_COLUMN_COMIC_ID = "comic_id"
        const val ARG_COMIC_ID = "comic_id"
        const val MOCK_COMICS_DATA_FILENAME = "comics.json"
        const val IMAGE_LOADING_CROSS_FADE_DURATION = 1000
        const val FAVORITE_ADDED = "1"
        const val FAVORITE_REMOVED = "0"
        const val UI_ANIMATION_DELAY = 300
        const val ARG_COMIC_IMAGE_URL = "image_url"
    }
}