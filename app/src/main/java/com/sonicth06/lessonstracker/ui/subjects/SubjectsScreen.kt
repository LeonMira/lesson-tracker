package com.sonicth06.lessonstracker.ui.subjects

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonicth06.lessonstracker.R
import com.sonicth06.lessonstracker.data.Subject
import com.sonicth06.lessonstracker.ui.AppViewModelProvider
import com.sonicth06.lessonstracker.ui.components.ConfirmDeleteDialog
import com.sonicth06.lessonstracker.ui.components.ModeSwitch
import com.sonicth06.lessonstracker.ui.navigation.NavigationDestination
import com.sonicth06.lessonstracker.ui.theme.LessonsTrackerTheme

object SubjectsDestination : NavigationDestination {
    override val route = "subjects"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(
    navigateToTopics: (Int) -> Unit,
    onNavigateToSubjectEntry: () -> Unit,
    isTeacher: Boolean,
    onModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubjectsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(SubjectsDestination.titleRes)) },
                actions = { ModeSwitch(isTeacher = isTeacher, onModeChange = onModeChange) }
            )
        },
        floatingActionButton = {
            if (isTeacher) {
                FloatingActionButton(onClick = onNavigateToSubjectEntry) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir asignatura"
                    )
                }
            }
        }
    ) { innerPadding ->
        SubjectsBody(
            subjects = uiState.subjects,
            onSubjectClick = navigateToTopics,
            onDeleteSubject = viewModel::deleteSubject,
            canEdit = isTeacher,
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun SubjectsBody(
    subjects: List<Subject>,
    onSubjectClick: (Int) -> Unit,
    onDeleteSubject: (Subject) -> Unit,
    canEdit: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var subjectToDelete by remember { mutableStateOf<Subject?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        if (subjects.isEmpty()) {
            Text(
                text = stringResource(R.string.no_subjects_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            SubjectsList(
                subjects = subjects,
                onSubjectClick = onSubjectClick,
                onDeleteClick = { subjectToDelete = it },
                canEdit = canEdit,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            )
        }
    }

    subjectToDelete?.let { subject ->
        ConfirmDeleteDialog(
            title = "Eliminar asignatura",
            message = "Se eliminará \"${subject.name}\" y todos sus temas. Esta acción no se puede deshacer.",
            onConfirm = {
                onDeleteSubject(subject)
                subjectToDelete = null
            },
            onDismiss = { subjectToDelete = null }
        )
    }
}

@Composable
private fun SubjectsList(
    subjects: List<Subject>,
    onSubjectClick: (Int) -> Unit,
    onDeleteClick: (Subject) -> Unit,
    canEdit: Boolean,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items = subjects, key = { it.id }) { subject ->
            SubjectItem(
                subject = subject,
                canEdit = canEdit,
                onDeleteClick = { onDeleteClick(subject) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSubjectClick(subject.id) }
            )
        }
    }
}

@Composable
private fun SubjectItem(
    subject: Subject,
    canEdit: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)
        ) {
            Text(
                text = subject.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = if (canEdit) 0.dp else 12.dp)
            )
            if (canEdit) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar asignatura"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubjectsBodyPreview() {
    LessonsTrackerTheme {
        SubjectsBody(
            subjects = listOf(
                Subject(1, "Subject 1", "Description 1"),
                Subject(2, "Subject 2", "Description 2"),
                Subject(3, "Subject 3", "Description 3")
            ),
            onSubjectClick = {},
            onDeleteSubject = {},
            canEdit = true
        )
    }
}