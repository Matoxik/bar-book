package com.macieandrz.barbook.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilteredDrink(
    @Json(name = "idDrink")
    val idDrink: String,
    @Json(name = "strDrink")
    val strDrink: String,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String
)

// Extension function to convert FilteredDrink to Drink
fun FilteredDrink.toDrink(): Drink {
    return Drink(
        dateModified = null,
        idDrink = this.idDrink,
        strAlcoholic = null,
        strCategory = null,
        strCreativeCommonsConfirmed = null,
        strDrink = this.strDrink,
        strDrinkAlternate = null,
        strDrinkThumb = this.strDrinkThumb,
        strGlass = null,
        strIBA = null,
        strImageAttribution = null,
        strImageSource = null,
        strIngredient1 = null,
        strIngredient2 = null,
        strIngredient3 = null,
        strIngredient4 = null,
        strIngredient5 = null,
        strIngredient6 = null,
        strIngredient7 = null,
        strIngredient8 = null,
        strIngredient9 = null,
        strIngredient10 = null,
        strIngredient11 = null,
        strIngredient12 = null,
        strIngredient13 = null,
        strIngredient14 = null,
        strIngredient15 = null,
        strInstructions = null,
        strInstructionsDE = null,
        strInstructionsES = null,
        strInstructionsFR = null,
        strInstructionsIT = null,
        strInstructionsZHHANS = null,
        strInstructionsZHHANT = null,
        strMeasure1 = null,
        strMeasure2 = null,
        strMeasure3 = null,
        strMeasure4 = null,
        strMeasure5 = null,
        strMeasure6 = null,
        strMeasure7 = null,
        strMeasure8 = null,
        strMeasure9 = null,
        strMeasure10 = null,
        strMeasure11 = null,
        strMeasure12 = null,
        strMeasure13 = null,
        strMeasure14 = null,
        strMeasure15 = null,
        strTags = null,
        strVideo = null
    )
}