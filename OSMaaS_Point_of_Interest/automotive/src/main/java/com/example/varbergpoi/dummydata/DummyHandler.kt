package com.example.varbergpoi.dummydata
import com.example.varbergpoi.ObjectBox
import com.example.varbergpoi.R
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import timber.log.Timber
import android.content.res.AssetManager
import com.example.varbergpoi.dummydata.Category_.iconRes
import com.example.varbergpoi.dummydata.POIItem_.busNumber
import com.example.varbergpoi.dummydata.POIItem_.busTiming
import java.nio.charset.Charset





class DummyHandler {
    companion object {
        fun initDummyData(assetManager: AssetManager) {
            Timber.d("Enter initDummyData")

            val csvFileName = "POI.csv"
            val categoryBox = ObjectBox.boxStore.boxFor(Category::class.java)

            if (categoryBox.isEmpty) {
                val categories: MutableList<Category> = mutableListOf()

                try {
                    assetManager.open(csvFileName).bufferedReader(Charset.forName("UTF8")).use { reader ->
                        var line: String?
                        var isFirstLine = true  // Add this flag to track the first line
                        while (reader.readLine().also { line = it } != null) {
                            if (isFirstLine) {
                                isFirstLine = false
                                continue  // Skip the first line (header)
                            }
                            val data = line!!.split(",")
                            // Mapping the values in the CSV file and the corresponding resource identifiers in Android  for icons
                            val iconResMapping = mapOf(
                                "bubble_charger" to R.drawable.bubble_charger,
                                "bubble_restaurant" to R.drawable.bubble_restaurant,
                                "bubble_historical_site" to R.drawable.bubble_historical_site,
                                "bubble_park" to R.drawable.bubble_park

                            )

                            // Assuming CSV structure: Category,SubCategory,Title,Description,Lat,Lng,IconRes
                            val categoryTitle = data[0]
                            val subCategoryTitle = data[1]
                            val title = data[2]
                            val description = data[3]
                            val lat = data[4].toDouble()
                            val lng = data[5].toDouble()
                            val iconResKey = data[6]
                            val busStop = data[7]
                            val busNumber = data[8]
                            val busTiming  = data[9]
                            Timber.d("Enter initDummyData - myVariable: $iconResKey")
                            Timber.d("Enter initDummyData - busTiming: $busTiming")

                            // Check if the category already exists
                            val category = categories.find { it.title == categoryTitle }
                            if (category == null) {
                                val newCategory = Category(
                                    title = categoryTitle,
                                    iconRes = iconResMapping[iconResKey] ?: R.drawable.bubble_default
                                )
                                Timber.d("Enter initDummyData - myVariable: $newCategory")
                                categories.add(newCategory)

                                // Create subcategory and POIItem
                                val subCategory = SubCategory(title = subCategoryTitle)
                                val poiItem = POIItem(
                                    title = title,
                                    description = description,
                                    lat = lat,
                                    lng = lng,
                                    busStop = busStop,
                                    busNumber = busNumber,
                                    busTiming = busTiming

                                )

                                subCategory.points.add(poiItem)
                                newCategory.subCategories.add(subCategory)
                                Timber.d("Enter poiItem1 - myVariable: $poiItem")
                                Timber.d("Enter subCategory - myVariable: $subCategory")
                            } else {
                                // Category already exists, find it and add subcategory and POIItem
                                val subCategory = category.subCategories.find { it.title == subCategoryTitle }
                                if (subCategory == null) {
                                    val newSubCategory = SubCategory(title = subCategoryTitle)
                                    category.subCategories.add(newSubCategory)

                                    // Create POIItem
                                    val poiItem = POIItem(
                                        title = title,
                                        description = description,
                                        lat = lat,
                                        lng = lng,
                                        busStop = busStop,
                                        busNumber = busNumber,
                                        busTiming = busTiming
                                    )

                                    newSubCategory.points.add(poiItem)
                                    Timber.d("Enter poiItem2 - myVariable: $poiItem")

                                } else {
                                    // SubCategory already exists, find it and add POIItem
                                    val poiItem = POIItem(
                                        title = title,
                                        description = description,
                                        lat = lat,
                                        lng = lng,
                                        busStop = busStop,
                                        busNumber = busNumber,
                                        busTiming = busTiming
                                    )

                                    subCategory.points.add(poiItem)
                                    Timber.d("Enter poiItem3 - myVariable: $poiItem")

                                }
                            }
                        }
                    }

                    Timber.d("Populated objectbox from CSV")

                    // Put the categories into ObjectBox
                    categoryBox.put(categories)
                } catch (e: Exception) {
                    Timber.e(e, "Error reading CSV file")
                }
            }
        }
    }
}



@Entity
data class Category(
    @Id var id: Long = 0,
    var title: String,
    var iconRes: Int? = null
) {
    lateinit var subCategories: ToMany<SubCategory>
}

@Entity
data class SubCategory(@Id var id: Long = 0, var title: String) {
    lateinit var points: ToMany<POIItem>
}

@Entity
data class POIItem(
    @Id var id: Long = 0,
    var title: String,
    var description: String,
    var lat: Double,
    var lng: Double,
    var isFavorite: Boolean = false,
    var busStop: String,
    var busNumber: String,
    var busTiming: String
)