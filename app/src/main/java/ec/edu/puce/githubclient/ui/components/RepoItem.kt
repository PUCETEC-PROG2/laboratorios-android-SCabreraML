package ec.edu.puce.githubclient.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.puce.githubclient.models.GithubUser
import ec.edu.puce.githubclient.models.Repository

@Composable
fun RepoItem(
    repository: Repository,
    onEdit: (Repository) -> Unit,
    onDelete: (Repository) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                AsyncImage(
                    model = repository.owner.avatarUrl,
                    contentDescription = "Imagen de ${repository.name}",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = repository.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    repository.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    repository.language?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {

                Button(
                    onClick = {
                        onEdit(repository)
                    }
                ) {
                    Text("Editar")
                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Button(
                    onClick = {
                        onDelete(repository)
                    }
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {

    val repository = Repository(
        id = "12345",
        name = "Repositorio Django",
        description = "Descripción de prueba",
        language = "Python",
        owner = GithubUser(
            id = "1234",
            login = "smcabreral",
            avatarUrl = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"
        )
    )

    RepoItem(
        repository = repository,
        onEdit = {},
        onDelete = {}
    )
}