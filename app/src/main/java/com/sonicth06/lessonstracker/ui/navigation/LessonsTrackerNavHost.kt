package com.sonicth06.lessonstracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sonicth06.lessonstracker.ui.subjects.SubjectEntryDestination
import com.sonicth06.lessonstracker.ui.subjects.SubjectEntryScreen
import com.sonicth06.lessonstracker.ui.subjects.SubjectsDestination
import com.sonicth06.lessonstracker.ui.subjects.SubjectsScreen
import com.sonicth06.lessonstracker.ui.topics.TopicEntryDestination
import com.sonicth06.lessonstracker.ui.topics.TopicEntryScreen
import com.sonicth06.lessonstracker.ui.topics.TopicScreen
import com.sonicth06.lessonstracker.ui.topics.TopicsDestination

@Composable
fun LessonTrackerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = SubjectsDestination.route
) {
    var isTeacher by rememberSaveable { mutableStateOf(false) }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = SubjectsDestination.route) {
            SubjectsScreen(
                navigateToTopics = { subjectId ->
                    navController.navigate("${TopicsDestination.route}/$subjectId")
                },
                onNavigateToSubjectEntry = {
                    navController.navigate(SubjectEntryDestination.route)
                },
                isTeacher = isTeacher,
                onModeChange = { isTeacher = it }
            )
        }

        composable(route = SubjectEntryDestination.route) {
            SubjectEntryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = TopicsDestination.routeWithArgs,
            arguments = listOf(
                navArgument(TopicsDestination.subjectIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getInt(TopicsDestination.subjectIdArg) ?: 0
            TopicScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateToTopicEntry = {
                    navController.navigate("${TopicEntryDestination.route}/$subjectId")
                },
                isTeacher = isTeacher,
                onModeChange = { isTeacher = it }
            )
        }

        composable(
            route = TopicEntryDestination.routeWithArgs,
            arguments = listOf(
                navArgument(TopicEntryDestination.subjectIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            TopicEntryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}