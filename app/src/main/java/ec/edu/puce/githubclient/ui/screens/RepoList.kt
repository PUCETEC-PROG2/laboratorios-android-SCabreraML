package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Column

@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: () -> Unit = {}
) {

    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    var repoToDelete by remember {
        mutableStateOf<Repository?>(null)
    }

    var repoToEdit by remember {
        mutableStateOf<Repository?>(null)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToForm,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir repositorio"
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            errorMsg?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (!isLoading && errorMsg == null) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(repos) { repo ->

                        RepoItem(
                            repository = repo,
                            onEdit = {
                                repoToEdit = it
                            },
                            onDelete = {
                                repoToDelete = it
                            }
                        )
                    }
                }
            }

            repoToDelete?.let { repo ->

                AlertDialog(
                    onDismissRequest = {
                        repoToDelete = null
                    },
                    title = {
                        Text("Eliminar repositorio")
                    },
                    text = {
                        Text(
                            "¿Está seguro de eliminar ${repo.name}?"
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.deleteRepo(repo.name)
                                repoToDelete = null
                            }
                        ) {
                            Text("Sí")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                repoToDelete = null
                            }
                        ) {
                            Text("No")
                        }
                    }
                )
            }

            repoToEdit?.let { repo ->

                var newName by remember {
                    mutableStateOf(repo.name)
                }

                var newDescription by remember {
                    mutableStateOf(repo.description ?: "")
                }

                AlertDialog(
                    onDismissRequest = {
                        repoToEdit = null
                    },
                    title = {
                        Text("Editar repositorio")
                    },
                    text = {
                        Column {

                            OutlinedTextField(
                                value = newName,
                                onValueChange = {
                                    newName = it
                                },
                                label = {
                                    Text("Nombre")
                                }
                            )

                            OutlinedTextField(
                                value = newDescription,
                                onValueChange = {
                                    newDescription = it
                                },
                                label = {
                                    Text("Descripción")
                                }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {

                                viewModel.updateRepo(
                                    repoName = repo.name,
                                    newName = newName,
                                    newDescription = newDescription
                                )

                                repoToEdit = null
                            }
                        ) {
                            Text("Guardar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                repoToEdit = null
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    RepoList()
}