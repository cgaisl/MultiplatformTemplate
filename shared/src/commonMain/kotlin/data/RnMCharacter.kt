package data

import at.cgaisl.multiplatformtemplate.db.RnMCharacter as DatabaseRnMCharacter

// Rick and Morty Character
data class RnMCharacter(
    val id: String,
    val name: String,
    val image: String,
    val origin: String,
    val species: String,
    val gender: String,
    val status: String,
)

fun DatabaseRnMCharacter.toCharacter(): RnMCharacter = RnMCharacter(
    id = id,
    name = name,
    image = image,
    origin = origin,
    species = species,
    gender = gender,
    status = status,
)
