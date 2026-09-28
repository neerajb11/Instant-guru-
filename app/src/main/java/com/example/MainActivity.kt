package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.BlueprintScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MockTestScreen
import com.example.ui.screens.NotesAndFlashcardsScreen
import com.example.ui.theme.BitsatAmber
import com.example.ui.theme.BitsatAmberDark
import com.example.ui.theme.BitsatBlue
import com.example.ui.theme.BitsatPrepTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BitsatViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BitsatPrepTheme {
                BitsatMainApp()
            }
        }
    }
}

sealed class BottomNavItem(
    val screen: AppScreen,
    val title: String,
    val icon: ImageVector,
    val testTag: String
) {
    object Home : BottomNavItem(AppScreen.DASHBOARD, "Home", Icons.Default.Home, "nav_home")
    object AiTutor : BottomNavItem(AppScreen.AI_TUTOR, "AI Tutor", Icons.Default.AutoAwesome, "nav_ai_tutor")
    object Notes : BottomNavItem(AppScreen.NOTES_FLASHCARDS, "Notes", Icons.AutoMirrored.Filled.MenuBook, "nav_notes")
    object MockTest : BottomNavItem(AppScreen.MOCK_TEST, "CBT Mock", Icons.Default.Timer, "nav_mock")
    object Analytics : BottomNavItem(AppScreen.ANALYTICS, "Stats", Icons.Default.Assessment, "nav_analytics")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitsatMainApp(
    viewModel: BitsatViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val mockExamState by viewModel.mockExamState.collectAsState()

    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.AiTutor,
        BottomNavItem.Notes,
        BottomNavItem.MockTest,
        BottomNavItem.Analytics
    )

    // Hide Top Bar and Bottom Nav during active mock test to give full exam immersion
    val isTestRunning = currentScreen == AppScreen.MOCK_TEST && mockExamState.isRunning

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isTestRunning) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = when (currentScreen) {
                                AppScreen.DASHBOARD -> "BITSAT Super Tutor"
                                AppScreen.AI_TUTOR -> "AI Speed Tutor"
                                AppScreen.NOTES_FLASHCARDS -> "Syllabus & Flashcards"
                                AppScreen.MOCK_TEST -> "CBT Mock Simulator"
                                AppScreen.ANALYTICS -> "Performance & Cutoffs"
                                AppScreen.BLUEPRINT -> "EdTech Product Blueprint"
                            },
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    navigationIcon = {
                        Surface(
                            color = BitsatBlue.copy(alpha = 0.12f),
                            shape = CircleShape,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "BITS Pilani",
                                tint = BitsatBlue,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (currentScreen == AppScreen.BLUEPRINT) {
                                    viewModel.navigateTo(AppScreen.DASHBOARD)
                                } else {
                                    viewModel.navigateTo(AppScreen.BLUEPRINT)
                                }
                            },
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .testTag("top_blueprint_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Architecture,
                                contentDescription = "Developer Blueprint",
                                tint = if (currentScreen == AppScreen.BLUEPRINT) BitsatAmberDark else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        },
        bottomBar = {
            if (!isTestRunning) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    navItems.forEach { item ->
                        val selected = currentScreen == item.screen
                        NavigationBarItem(
                            selected = selected,
                            onClick = { viewModel.navigateTo(item.screen) },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BitsatBlue,
                                selectedTextColor = BitsatBlue,
                                indicatorColor = BitsatBlue.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag(item.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.DASHBOARD -> DashboardScreen(viewModel = viewModel)
                AppScreen.AI_TUTOR -> AiTutorScreen(viewModel = viewModel)
                AppScreen.NOTES_FLASHCARDS -> NotesAndFlashcardsScreen(viewModel = viewModel)
                AppScreen.MOCK_TEST -> MockTestScreen(viewModel = viewModel)
                AppScreen.ANALYTICS -> AnalyticsScreen(viewModel = viewModel)
                AppScreen.BLUEPRINT -> BlueprintScreen()
            }
        }
    }
}
