package io.kamara.xkcd.daily.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ComicTest {
    private val gson = Gson()
    private lateinit var comic: Comic

    @Before
    fun setUp() {
        val jsonReader = javaClass.classLoader.getResource("test_comic.json").readText()
        val comicType = object : TypeToken<Comic>() {}.type
        comic = gson.fromJson(jsonReader, comicType)
    }

    @Test
    fun test_should_parse_comic() {
        Assert.assertThat<Comic>(comic, IsNull.notNullValue())
        Assert.assertThat(comic.title, `is`("Estimation"))
        Assert.assertThat(comic.img, `is`("https://imgs.xkcd.com/comics/estimation.png"))
        Assert.assertThat(comic.num, `is`("612"))
    }
}