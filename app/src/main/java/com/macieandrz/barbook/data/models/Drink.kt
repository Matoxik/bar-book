package com.macieandrz.barbook.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Drink(
    @Json(name = "dateModified")
    val dateModified: String?,

    @Json(name = "idDrink")
    val idDrink: String?,

    @Json(name = "strAlcoholic")
    val strAlcoholic: String?,

    @Json(name = "strCategory")
    val strCategory: String?,

    @Json(name = "strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,

    @Json(name = "strDrink")
    val strDrink: String,

    @Json(name = "strDrinkAlternate")
    val strDrinkAlternate: String?,

    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String?,

    @Json(name = "strGlass")
    val strGlass: String?,

    @Json(name = "strIBA")
    val strIBA: String?,

    @Json(name = "strImageAttribution")
    val strImageAttribution: String?,

    @Json(name = "strImageSource")
    val strImageSource: String?,

    @Json(name = "strIngredient1")
    val strIngredient1: String?,

    @Json(name = "strIngredient10")
    val strIngredient10: String?,

    @Json(name = "strIngredient11")
    val strIngredient11: String?,

    @Json(name = "strIngredient12")
    val strIngredient12: String?,

    @Json(name = "strIngredient13")
    val strIngredient13: String?,

    @Json(name = "strIngredient14")
    val strIngredient14: String?,

    @Json(name = "strIngredient15")
    val strIngredient15: String?,

    @Json(name = "strIngredient2")
    val strIngredient2: String?,

    @Json(name = "strIngredient3")
    val strIngredient3: String?,

    @Json(name = "strIngredient4")
    val strIngredient4: String?,

    @Json(name = "strIngredient5")
    val strIngredient5: String?,

    @Json(name = "strIngredient6")
    val strIngredient6: String?,

    @Json(name = "strIngredient7")
    val strIngredient7: String?,

    @Json(name = "strIngredient8")
    val strIngredient8: String?,

    @Json(name = "strIngredient9")
    val strIngredient9: String?,

    @Json(name = "strInstructions")
    val strInstructions: String?,

    @Json(name = "strInstructionsDE")
    val strInstructionsDE: String?,

    @Json(name = "strInstructionsES")
    val strInstructionsES: String?,

    @Json(name = "strInstructionsFR")
    val strInstructionsFR: String?,

    @Json(name = "strInstructionsIT")
    val strInstructionsIT: String?,

    @Json(name = "strInstructionsZH-HANS")
    val strInstructionsZHHANS: String?,

    @Json(name = "strInstructionsZH-HANT")
    val strInstructionsZHHANT: String?,

    @Json(name = "strMeasure1")
    val strMeasure1: String?,

    @Json(name = "strMeasure10")
    val strMeasure10: String?,

    @Json(name = "strMeasure11")
    val strMeasure11: String?,

    @Json(name = "strMeasure12")
    val strMeasure12: String?,

    @Json(name = "strMeasure13")
    val strMeasure13: String?,

    @Json(name = "strMeasure14")
    val strMeasure14: String?,

    @Json(name = "strMeasure15")
    val strMeasure15: String?,

    @Json(name = "strMeasure2")
    val strMeasure2: String?,

    @Json(name = "strMeasure3")
    val strMeasure3: String?,

    @Json(name = "strMeasure4")
    val strMeasure4: String?,

    @Json(name = "strMeasure5")
    val strMeasure5: String?,

    @Json(name = "strMeasure6")
    val strMeasure6: String?,

    @Json(name = "strMeasure7")
    val strMeasure7: String?,

    @Json(name = "strMeasure8")
    val strMeasure8: String?,

    @Json(name = "strMeasure9")
    val strMeasure9: String?,

    @Json(name = "strTags")
    val strTags: String?,

    @Json(name = "strVideo")
    val strVideo: String?
) : Parcelable
