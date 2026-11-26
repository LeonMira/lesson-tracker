package com.sonicth06.lessonstracker.ui.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonicth06.lessonstracker.R
import com.sonicth06.lessonstracker.data.Topic
import com.sonicth06.lessonstracker.ui.AppViewModelProvider
import com.sonicth06.lessonstracker.ui.components.ConfirmDeleteDialog
import com.sonicth06.lessonstracker.ui.components.ModeSwitch
import com.sonicth06.lessonstracker.ui.navigation.NavigationDestination

object TopicsDestination : NavigationDestination {
    override val route = "topics"
    override val titleRes = R.string.app_name
    const val subjectIdArg = "subjectId"
    val routeWithArgs = "$route/{$subjectIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(
    navigateBack: () -> Unit,
    onNavigateToTopicEntry: () -> Unit,
    isTeacher: Boolean,
    onModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopicsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(TopicsDestination.titleRes)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = { ModeSwitch(isTeacher = isTeacher, onModeChange = onModeChange) }
            )
        },
        floatingActionButton = {
            if (isTeacher) {
                FloatingActionButton(onClick = onNavigateToTopicEntry) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir tema"
                    )
                }
            }
        }
    ) { innerPadding ->
        TopicsBody(
            topicList = uiState.topicList,
            onDeleteTopic = viewModel::deleteTopic,
            onCoveredChange = viewModel::onCoveredChange,
            canEdit = isTeacher,
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun TopicsBody(
    topicList: List<Topic>,
    onDeleteTopic: (Topic) -> Unit,
    onCoveredChange: (Topic, Boolean) -> Unit,
    canEdit: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var topicToDelete by remember { mutableStateOf<Topic?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        if (topicList.isEmpty()) {
            Text(
                text = "No hay temas todavía.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = topicList, key = { it.id }) { topic ->
                    TopicItem(
                        topic = topic,
                        canEdit = canEdit,
                        onCoveredChange = { covered -> onCoveredChange(topic, covered) },
                        onDeleteClick = { topicToDelete = topic },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    topicToDelete?.let { topic ->
        ConfirmDeleteDialog(
            title = "Eliminar tema",
            message = "Se eliminará \"${topic.name}\". Esta acción no se puede deshacer.",
            onConfirm = {
                onDeleteTopic(topic)
                topicToDelete = null
            },
            onDismiss = { topicToDelete = null }
        )
    }
}

@Composable
private fun TopicItem(
    topic: Topic,
    canEdit: Boolean,
    onCoveredChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Checkbox(
                checked = topic.covered,
                onCheckedChange = onCoveredChange,
                enabled = canEdit
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = topic.name,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (topic.covered) TextDecoration.LineThrough else null
                )
                if (topic.description.isNotBlank()) {
                    Text(
                        text = topic.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (canEdit) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar tema"
                    )
                }
            }
        }
    }
}