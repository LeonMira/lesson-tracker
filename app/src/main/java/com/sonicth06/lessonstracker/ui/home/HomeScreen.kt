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
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.sonicth06.lessonstracker.data.Topic

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val topics = remember {
        mutableStateListOf(
            Topic(id = 1, subjectId = 1, name = "Variables"),
            Topic(id = 2, subjectId = 1, name = "Condicionales"),
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(topics, key = { it.id }) { topic ->
            TopicItem(
                topic = topic,
                onCheckedChange = { new ->
                    val position = topics.indexOfFirst { it.id == topic.id }
                    //Browses on the list for an item which fulfills the condition of the keys,
                    //which is the item which has the same id of the topic on this row
                    val copy = topic.copy(covered = new)
                    //It creates a copy of the item returned by the previous line, and assigns the
                    //boolean value contained in the checkbox to the `covered` property of that copy.
                    topics[position] = copy
                    //changes the old version of the topic for the copy, with the data of the checkbox
                    //updated
                    //compacted version:
                    //topics[topics.indexOfFirst { it.id == topic.id }] = topic.copy(covered = new)
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