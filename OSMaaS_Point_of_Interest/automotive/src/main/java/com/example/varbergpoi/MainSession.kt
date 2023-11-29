package com.example.varbergpoi

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.ScreenManager
import androidx.car.app.Session
import androidx.car.app.model.CarIcon
import androidx.car.app.model.GridItem
import androidx.car.app.model.GridTemplate
import androidx.car.app.model.ItemList
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import com.example.varbergpoi.dummydata.Category
import com.example.varbergpoi.dummydata.POIItem
import com.example.varbergpoi.dummydata.POIItem_
import timber.log.Timber


class MainSession : Session() {
    override fun onCreateScreen(intent: Intent): Screen {
        return if (hasLocationPermission())
            MainScreen(carContext)
        else {
            Timber.d("Permission Read main screen")
            carContext.getCarService(ScreenManager::class.java).push(MainScreen(carContext))
            LocationPermissionScreen(carContext)
        }
    }


    private fun hasLocationPermission() =
        (carContext.checkSelfPermission(ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
}

class MainScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        Timber.d("Reading main screen")
        val categoryBox = ObjectBox.boxStore.boxFor(Category::class.java)
        Timber.d("Reading categoryBox")
        val poiBox = ObjectBox.boxStore.boxFor(POIItem::class.java)
        Timber.d("Reading poiBox")

        val gridItemListBuilder = ItemList.Builder()

        categoryBox.all.forEach { data ->
            val gridItemBuilder = GridItem.Builder()
                .setTitle(data.title)
                .setOnClickListener {
                    if (data.subCategories.isNotEmpty())
                        screenManager.push(
                            SubCategoryListScreen(
                                carContext,
                                data.title,
                                data.subCategories
                            )
                        )
                }
                .setImage(
                    CarIcon.Builder(
                        IconCompat.createWithResource(
                            carContext,
                            data.iconRes ?: R.drawable.bubble_restaurant
                        )
                    ).build()
                )
                .build()

            gridItemListBuilder.addItem(gridItemBuilder)
        }

        val favorites = poiBox.query(POIItem_.isFavorite.equal(true)).build().find()

        if (favorites.isNotEmpty()) {
            val gridItemBuilder = GridItem.Builder()
                .setTitle(carContext.getString(R.string.category_title_favorite))
                .setOnClickListener {
                    screenManager.push(
                        PlaceListScreen(
                            carContext, favorites, carContext.getString(R.string.your_favorites_text)
                        )
                    )
                }
                .setImage(
                    CarIcon.Builder(
                        IconCompat.createWithResource(carContext, R.drawable.bubble_heart)
                    ).build()).build()
            gridItemListBuilder.addItem(gridItemBuilder)
        }

        return GridTemplate.Builder()
            .setTitle( carContext.getString(R.string.header_title))
            .setSingleList(gridItemListBuilder.build())
            .build()
    }

    // Data class to hold POI information
    data class PoiData(val title: String, val iconResId: Int)
}
