package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import data.RnMCharacter

@Composable
fun RnMListItem(character: data.RnMCharacter, onClick: () -> Unit) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(character.name)
    }
}

@Preview
@Composable
fun RnMListItemPreview() {
    RnMListItem(
        data.RnMCharacter(
            id = "1",
            name = "Rick Sanchez",
            gender = "gender",
            origin = "origin",
            species = "species",
            status = "status",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )
    ) {}
}
