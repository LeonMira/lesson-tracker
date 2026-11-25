package com.sonicth06.lessonstracker.ui.subjects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonicth06.lessonstracker.R
import com.sonicth06.lessonstracker.data.Subject
import com.sonicth06.lessonstracker.ui.AppViewModelProvider
import com.sonicth06.lessonstracker.ui.navigation.NavigationDestination

object SubjectsDestination : NavigationDestination {
    override val route = "subjects"
    override val titleRes = R.string.app_name
    //consultar porque R.string.app_name
}

@Composable
fun SubjectsScreen(
    navigateToTopics: (Int) -> Unit,
    viewModel: SubjectsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(modifier = modifier) { innerPadding ->
        SubjectsBody(
            subjectList = uiState.subjectList,
            onSubjectClick = navigateToTopics,
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun SubjectsBody(
    subjectList: List<Subject>,
    onSubjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (subjectList.isEmpty()) {
            Text(
                //text = stringResource(R.string.no_subjects_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
                modifier = Modifier.padding(contentPadding)
            )
        } else {
    }
}



