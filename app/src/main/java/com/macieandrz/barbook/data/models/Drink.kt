package com.macieandrz.barbook.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
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
    val strDrinkAlternate: Any?,

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
    val strIngredient10: Any?,

    @Json(name = "strIngredient11")
    val strIngredient11: Any?,

    @Json(name = "strIngredient12")
    val strIngredient12: Any?,

    @Json(name = "strIngredient13")
    val strIngredient13: Any?,

    @Json(name = "strIngredient14")
    val strIngredient14: Any?,

    @Json(name = "strIngredient15")
    val strIngredient15: Any?,

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
    val strIngredient8: Any?,

    @Json(name = "strIngredient9")
    val strIngredient9: Any?,

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
    val strInstructionsZHHANS: Any?,

    @Json(name = "strInstructionsZH-HANT")
    val strInstructionsZHHANT: Any?,

    @Json(name = "strMeasure1")
    val strMeasure1: String?,

    @Json(name = "strMeasure10")
    val strMeasure10: Any?,

    @Json(name = "strMeasure11")
    val strMeasure11: Any?,

    @Json(name = "strMeasure12")
    val strMeasure12: Any?,

    @Json(name = "strMeasure13")
    val strMeasure13: Any?,

    @Json(name = "strMeasure14")
    val strMeasure14: Any?,

    @Json(name = "strMeasure15")
    val strMeasure15: Any?,

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
    val strMeasure8: Any?,

    @Json(name = "strMeasure9")
    val strMeasure9: Any?,

    @Json(name = "strTags")
    val strTags: String?,

    @Json(name = "strVideo")
    val strVideo: Any?
)
