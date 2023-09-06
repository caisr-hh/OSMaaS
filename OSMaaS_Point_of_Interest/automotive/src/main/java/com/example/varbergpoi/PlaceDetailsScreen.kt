package com.example.varbergpoi

import android.content.Intent
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import androidx.car.app.CarContext
import androidx.car.app.CarToast
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarColor
import androidx.car.app.model.CarIcon
import androidx.car.app.model.ForegroundCarColorSpan
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.varbergpoi.dummydata.POIItem
import java.util.Locale

/** A screen that displays a the details for a given place.  */
class PlaceDetailsScreen(carContext: CarContext, private val item: POIItem) :
    Screen(carContext),
    DefaultLifecycleObserver {

    // Loaded asynchronously from the network.
    private var mPhoto: Bitmap? = null
    private var mGeocoder: Geocoder? = null
    override fun onCreate(owner: LifecycleOwner) {
        mGeocoder = Geocoder(carContext)
    }

    override fun onGetTemplate(): Template {
        val paneBuilder = Pane.Builder()
        val mDetails = listOf(
            "Detail one",
            "Detail two",
        )

        // If we don't have any places yet, show a loading progress indicator.
        if (mDetails == null) {
            paneBuilder.setLoading(true)
        } else {
            //Row One
            val row1Builder = Row.Builder().setTitle(carContext.getString(R.string.address_title))

            // Add the address, split in multiple lines.
            val addressLines = listOf(
                "Fredsgatan 5",
                "302 46 Halmstad",
            )
            for (line in addressLines) {
                row1Builder.addText(line)
            }
            if (mPhoto != null) {
                row1Builder.setImage(
                    CarIcon.Builder(IconCompat.createWithBitmap(mPhoto!!)).build(),
                    Row.IMAGE_TYPE_LARGE
                )
            }
            paneBuilder.addRow(row1Builder.build())
            var hasSecondRow = false

            //Row Two
            val row2Builder = Row.Builder().setTitle(carContext.getString(R.string.phone_title))

            // Add the phone number.
            val phoneNumber: String = "+46 700 51 55 36"
            if (phoneNumber != null) {
                hasSecondRow = true
                row2Builder.addText(phoneNumber)
            }

            if (hasSecondRow) {
                paneBuilder.addRow(row2Builder.build())
            }

            //Row Three
            val row3Builder = Row.Builder()
                .setTitle(carContext.getString(R.string.ratings_text))

            // Add the place's ratings.
            val ratings: Double = 3.8
            if (ratings >= 0) {
                hasSecondRow = true
                row3Builder.addText(getRatingsString(ratings))
            }
            if (hasSecondRow) {
                paneBuilder.addRow(row3Builder.build())
            }

            //Row Four
            val row4Builder = Row.Builder()
                .setTitle(carContext.getString(R.string.about))

            // Add a description.
            if (item.description.isNotEmpty()) {
                hasSecondRow = true
                row4Builder.addText(item.description)
            }
            if (hasSecondRow) {
                paneBuilder.addRow(row4Builder.build())
            }

            // Add a button with a navigate action.
            paneBuilder.addAction(
                Action.Builder()
                    .setTitle(carContext.getString(R.string.navigate))
                    .setOnClickListener { onClickNavigate() }
                    .setFlags(Action.FLAG_PRIMARY)
                    .build()
            ).addAction(
                Action.Builder()
                    .setTitle(carContext.getString(R.string.phone_button_text))
                    .setOnClickListener { openDialer() }
                    .build()
            )
        }

        val poiBox = ObjectBox.boxStore.boxFor(POIItem::class.java)
        var isFavorite = poiBox.get(item.id).isFavorite

        val settings = Action.Builder()
            .setIcon(
                CarIcon.Builder(
                    IconCompat.createWithResource(
                        carContext,
                        if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                    )
                ).build()
            )
            .setOnClickListener {
                item.isFavorite = !item.isFavorite
                poiBox.put(item)
                invalidate()
            }
            .build()

        return PaneTemplate.Builder(paneBuilder.build())
            .setTitle(item.title)
            .setHeaderAction(Action.BACK)
            .setActionStrip(
                ActionStrip.Builder()
                    .addAction(settings)
                    .build()
            )
            .build()
    }

    private fun onClickNavigate() {
        val latitude = item.lat
        val longitude = item.lng

        val mapIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            carContext.startActivity(mapIntent)
        } catch (e: Exception) {
            CarToast.makeText(
                carContext,
                carContext.getString(R.string.navigation_error_message),
                CarToast.LENGTH_LONG
            ).show()
        }
    }

    private fun openDialer() {
        val phoneNumber: String = "+46 700 51 55 36"
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phoneNumber}")
        }

        try {
            carContext.startCarApp(intent)
        } catch (e: Exception) {
            CarToast.makeText(
                carContext,
                carContext.getString(R.string.dialing_error_message),
                CarToast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val FULL_STAR = "\u2605"

        private fun getAddressLines(address: Address): List<CharSequence> {
            val list: MutableList<CharSequence> = ArrayList()

            // First line: [street number address].
            var separator = " "
            var s = append(address.subThoroughfare, address.thoroughfare, separator)
            if (s != null) {
                list.add(s)
            }

            // Second line: [city, state, postal code].
            separator = ", "
            s = append(
                append(address.locality, address.adminArea, separator),
                address.postalCode,
                separator
            )
            if (s != null) {
                list.add(s)
            }
            return list
        }

        private fun append(a: String?, b: String?, separator: String): String? {
            return if (a == null) b else if (b == null) a else a + if (a.isEmpty()) b else separator + b
        }

        private fun getRatingsString(ratings: Double): CharSequence {
            val ss = SpannableString(FULL_STAR + " " + String.format(Locale.US, "%.1f", ratings))

            colorize(ss, CarColor.YELLOW, 0, 1)
            return ss
        }

        private fun colorize(s: SpannableString, color: CarColor?, index: Int, length: Int) {
            s.setSpan(
                ForegroundCarColorSpan.create(color!!),
                index,
                index + length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}
