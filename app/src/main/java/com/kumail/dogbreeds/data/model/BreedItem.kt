package com.kumail.dogbreeds.data.model

/**
 * Created by kumailhussain on 26/10/2021.
 */
data class BreedItem(
    val breed: String,
    val subBreed: List<String>
)

fun Map<String, List<String>>.toListOfBreedItems(): List<BreedItem> {
    return this.map { BreedItem(it.key, it.value) }
}