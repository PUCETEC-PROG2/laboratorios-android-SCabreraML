package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ec.edu.puce.githubclient.ui.components.RepoItem



@Composable
fun RepoList () {
    Column (
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 5.dp)
    ){
        RepoItem(
            name = "Repositorio Django",
            avatarImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIKQOZPpk7J8Zy72VMx4HTBowLyJtsRaQ3rw&s",
            description = "Proyecto de Python de Sebastian",
            language = "Python"

        )

        RepoItem(
            name = "Repositorio de Android",
            avatarImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIKQOZPpk7J8Zy72VMx4HTBowLyJtsRaQ3rw&s",
            description = "Proyecto de Android de Sebastian",
            language = "Kotlin"

        )

        RepoItem(
            name = "Repositorio React",
            avatarImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIKQOZPpk7J8Zy72VMx4HTBowLyJtsRaQ3rw&s",
            description = "Proyecto de React de Sebastian",
            language = "Javascript"

        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    RepoList()
}