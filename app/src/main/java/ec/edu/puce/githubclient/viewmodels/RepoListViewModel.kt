package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel: ViewModel (){

    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos () {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                _repos.value= RetrofitClient.apiServices.getRepositories()

            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false            }
        }
    }

    fun deleteRepo(
        repoName: String
    ) {
        viewModelScope.launch {
            try {

                RetrofitClient.apiServices.deleteRepository(
                    owner = "smcabreral",
                    repo = repoName
                )

                fetchRepos()

            } catch (e: Exception) {
                _errorMsg.value =
                    "Error al eliminar: ${e.localizedMessage}"
            }
        }
    }

    fun updateRepo(
        repoName: String,
        newName: String,
        newDescription: String
    ) {
        viewModelScope.launch {
            try {

                RetrofitClient.apiServices.updateRepository(
                    owner = "smcabreral",
                    repo = repoName,
                    repository = RepositoryPayload(
                        name = newName,
                        description = newDescription
                    )
                )

                fetchRepos()

            } catch (e: Exception) {
                _errorMsg.value =
                    "Error al actualizar: ${e.localizedMessage}"
            }
        }
    }
}