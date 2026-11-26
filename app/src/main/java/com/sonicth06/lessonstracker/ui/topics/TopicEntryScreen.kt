package com.sonicth06.lessonstracker.ui.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonicth06.lessonstracker.R
import com.sonicth06.lessonstracker.ui.AppViewModelProvider
import com.sonicth06.lessonstracker.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TopicEntryDestination : NavigationDestination {
    override val route = "topic_entry"
    override val titleRes = R.string.app_name
    const val subjectIdArg = "subjectId"
    val routeWithArgs = "$route/{$subjectIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicEntryScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopicsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val entryUiState = viewModel.entryUiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Nuevo tema") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = entryUiState.name,
                onValueChange = viewModel::updateEntryName,
                label = { Text(text = "Nombre del tema") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = entryUiState.description,
                onValueChange = viewModel::updateEntryDescription,
                label = { Text(text = "Descripción (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveTopic()
                        onNavigateBack()
                    }
                },
                enabled = entryUiState.isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar")
            }
        }
    }
}

