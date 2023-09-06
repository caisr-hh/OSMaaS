package com.example.varbergpoi

import com.example.varbergpoi.dummydata.Category
import com.example.varbergpoi.dummydata.DummyHandler
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun isCategoriesFilled(){
        ObjectBox.init(null)
        DummyHandler.initDummyData()

        val categorySize = ObjectBox.boxStore.boxFor(Category::class.java).count()
        assertTrue(categorySize > 0)
    }
}