package com.sonicth06.lessonstracker.ui.home

import android.R.attr.enabled
import android.widget.CheckBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonicth06.lessonstracker.data.Topic

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(homeUiState.topicList, key = { it.id }) { topic ->
            TopicItem(
                topic = topic,
                onCheckedChange = { new ->
                    homeViewModel.onCoveredChange(topic, new)
                },
                enabled = true
            )
        }
    }
}

@Composable
fun TopicItem(
    topic: Topic,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = topic.covered,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
        Text(text = topic.name)
    }
}

@Preview(showBackground = true)
@Composable
fun TopicItemPreview() {
    TopicItem(
        topic = Topic(id = 1, subjectId = 1, name = "Variables"),
        onCheckedChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}