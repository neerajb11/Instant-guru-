package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BitsatSubject
import com.example.model.MockTestResult
import com.example.model.Question
import com.example.model.QuestionStatus
import com.example.ui.theme.BitsatAmber
import com.example.ui.theme.BitsatAmberDark
import com.example.ui.theme.BitsatBlue
import com.example.ui.theme.ChemistryEmerald
import com.example.ui.theme.EnglishIndigo
import com.example.ui.theme.LogicPurple
import com.example.ui.theme.MathsBlue
import com.example.ui.theme.PhysicsCoral
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BitsatViewModel

@Composable
fun MockTestScreen(
    viewModel: BitsatViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.mockExamState.collectAsState()
    var showSubmitConfirmation by remember { mutableStateOf(false) }

    if (state.isCompleted && state.result != null) {
        MockTestResultView(
            result = state.result!!,
            questions = state.questions,
            selectedAnswers = state.selectedAnswers,
            onRetake = { viewModel.restartMockExam() },
            onDone = { viewModel.navigateTo(AppScreen.DASHBOARD) }
        )
    } else if (state.questions.isNotEmpty()) {
        val currentQuestion = state.questions.getOrNull(state.currentQuestionIndex) ?: state.questions.first()
        val selectedOption = state.selectedAnswers[currentQuestion.id]
        val isMarked = state.markedForReviewIds.contains(currentQuestion.id)

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // CBT Top Bar: Subject, Countdown Timer, Submit Action
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "BITSAT CBT Simulation",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${currentQuestion.subject.title} • Q${state.currentQuestionIndex + 1} of ${state.questions.size}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Countdown Timer Pill
                    val minutes = state.timeRemainingSeconds / 60
                    val seconds = state.timeRemainingSeconds % 60
                    val timerColor = if (state.timeRemainingSeconds < 120) PhysicsCoral else BitsatBlue

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = timerColor.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Timer",
                                    tint = timerColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = String.format("%02d:%02d", minutes, seconds),
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp,
                                    color = timerColor
                                )
                            }
                        }

                        Button(
                            onClick = { showSubmitConfirmation = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PhysicsCoral),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("submit_test_button")
                        ) {
                            Text("Submit", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // BITSAT Bonus Question Banner (Signature Feature)
            if (state.bonusQuestionsUnlocked) {
                Surface(
                    color = BitsatAmber.copy(alpha = 0.2f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LockOpen,
                            contentDescription = null,
                            tint = BitsatAmberDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "🎉 BITSAT BONUS UNLOCKED! All questions attempted — 12 bonus questions (+36 marks) accessible!",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BitsatAmberDark
                        )
                    }
                }
            }

            // Question Palette Strip
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.questions) { index, q ->
                    val status = viewModel.getQuestionStatus(q.id)
                    val isSelected = index == state.currentQuestionIndex

                    val paletteBg = when (status) {
                        QuestionStatus.ANSWERED -> ChemistryEmerald
                        QuestionStatus.MARKED_FOR_REVIEW -> LogicPurple
                        QuestionStatus.ANSWERED_AND_MARKED -> LogicPurple
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val textColor = when (status) {
                        QuestionStatus.ANSWERED, QuestionStatus.MARKED_FOR_REVIEW, QuestionStatus.ANSWERED_AND_MARKED -> Color.White
                        else -> MaterialTheme.colorScheme.onSurface
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(paletteBg)
                            .border(
                                width = if (isSelected) 2.5.dp else 1.dp,
                                color = if (isSelected) BitsatBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                            .clickable { viewModel.selectQuestionIndex(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            fontSize = 12.sp,
                            color = textColor
                        )
                    }
                }
            }

            // Question Body Area
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Question Header Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = when (currentQuestion.subject) {
                                        BitsatSubject.PHYSICS -> PhysicsCoral
                                        BitsatSubject.CHEMISTRY -> ChemistryEmerald
                                        BitsatSubject.MATHEMATICS -> MathsBlue
                                        BitsatSubject.ENGLISH -> EnglishIndigo
                                        BitsatSubject.LOGICAL_REASONING -> LogicPurple
                                    }.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "${currentQuestion.subject.title} • ${currentQuestion.chapterName}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Text(
                                    text = "+3 / -1 Marks",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BitsatAmberDark
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Q${state.currentQuestionIndex + 1}. ${currentQuestion.questionText}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                // Options (A, B, C, D)
                itemsIndexed(currentQuestion.options) { optionIdx, optionText ->
                    val isChecked = selectedOption == optionIdx
                    val optionLabel = listOf("A", "B", "C", "D").getOrElse(optionIdx) { "$optionIdx" }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.answerCurrentQuestion(optionIdx) }
                            .testTag("option_${currentQuestion.id}_$optionIdx"),
                        shape = RoundedCornerShape(14.dp),
                        color = if (isChecked) BitsatBlue.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isChecked) 2.dp else 1.dp,
                            color = if (isChecked) BitsatBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        if (isChecked) BitsatBlue else MaterialTheme.colorScheme.surfaceVariant,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = optionLabel,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isChecked) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = optionText,
                                fontSize = 14.sp,
                                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Bottom Actions Bar (CBT Standard Controls)
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { viewModel.toggleMarkForReview() },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = if (isMarked) LogicPurple else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isMarked) "Unmark" else "Review",
                            fontSize = 11.sp
                        )
                    }

                    TextButton(
                        onClick = { viewModel.clearCurrentResponse() }
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clear", fontSize = 11.sp)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(
                            onClick = { viewModel.previousQuestion() },
                            enabled = state.currentQuestionIndex > 0,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Prev", modifier = Modifier.size(16.dp))
                        }

                        Button(
                            onClick = { viewModel.nextQuestion() },
                            colors = ButtonDefaults.buttonColors(containerColor = BitsatBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("save_next_button")
                        ) {
                            Text("Save & Next", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    } else {
        // Fallback / Start Mock Screen
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = null,
                tint = BitsatBlue,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "BITSAT Speed Mock Simulator", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(
                text = "Simulate real computer-based exam conditions with +3 / -1 marking and 80 seconds per question pacing.",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.startMockTest(10) },
                colors = ButtonDefaults.buttonColors(containerColor = BitsatBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start Mock Test Now", fontWeight = FontWeight.Bold)
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitConfirmation) {
        val answeredCount = state.selectedAnswers.size
        val totalCount = state.questions.size

        AlertDialog(
            onDismissRequest = { showSubmitConfirmation = false },
            title = { Text("Submit Mock Test?", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "You have attempted $answeredCount out of $totalCount questions.\n\n" +
                    "Marking scheme: +3 for each correct, -1 for each incorrect.\n" +
                    "Are you sure you want to finalize your test and view detailed solutions?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirmation = false
                        viewModel.submitMockTest()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PhysicsCoral)
                ) {
                    Text("Yes, Submit Test")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showSubmitConfirmation = false }) {
                    Text("Continue Test")
                }
            }
        )
    }
}

@Composable
private fun MockTestResultView(
    result: MockTestResult,
    questions: List<Question>,
    selectedAnswers: Map<String, Int>,
    onRetake: () -> Unit,
    onDone: () -> Unit
) {
    var filterSubject by remember { mutableStateOf<BitsatSubject?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header scorecard
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BitsatBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "BITSAT Mock Test Report",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${result.totalScore} / ${result.maxScore}",
                        color = Color.White,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Score calculated strictly per BITSAT scheme (+3 / -1)",
                        color = BitsatAmber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ResultMetric(label = "Accuracy", value = "${String.format("%.1f", result.accuracyPercentage)}%")
                        ResultMetric(label = "Correct (+3)", value = "${result.correctCount}", valueColor = ChemistryEmerald)
                        ResultMetric(label = "Incorrect (-1)", value = "${result.incorrectCount}", valueColor = PhysicsCoral)
                        ResultMetric(label = "Left (0)", value = "${result.unattemptedCount}")
                    }
                }
            }
        }

        // Action Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onRetake,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Retake Mock")
                }
                Button(
                    onClick = onDone,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = BitsatBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Back to Dashboard")
                }
            }
        }

        // Solutions & Speed Shortcuts Section
        item {
            Text(
                text = "Detailed Solutions & Speed Shortcuts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        itemsIndexed(questions) { index, q ->
            val userOption = selectedAnswers[q.id]
            val isCorrect = userOption == q.correctOptionIndex
            val isAttempted = userOption != null

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Q${index + 1} • ${q.subject.title}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        val (badgeText, badgeBg, badgeTextColor) = when {
                            !isAttempted -> Triple("Unattempted (0)", Color.Gray.copy(alpha = 0.2f), Color.DarkGray)
                            isCorrect -> Triple("Correct (+3)", ChemistryEmerald.copy(alpha = 0.15f), ChemistryEmerald)
                            else -> Triple("Incorrect (-1)", PhysicsCoral.copy(alpha = 0.15f), PhysicsCoral)
                        }

                        Surface(color = badgeBg, shape = RoundedCornerShape(6.dp)) {
                            Text(
                                text = badgeText,
                                color = badgeTextColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = q.questionText, fontSize = 14.sp, fontWeight = FontWeight.Medium)

                    Spacer(modifier = Modifier.height(10.dp))

                    // Correct Option display
                    val optLabel = listOf("A", "B", "C", "D").getOrElse(q.correctOptionIndex) { "Correct" }
                    Text(
                        text = "Correct Option: ($optLabel) ${q.options.getOrNull(q.correctOptionIndex) ?: ""}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ChemistryEmerald
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Step-by-step Solution: ${q.explanation}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = BitsatAmber.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = BitsatAmberDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "⚡ BITSAT Speed Shortcut: ${q.speedShortcut}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultMetric(label: String, value: String, valueColor: Color = Color.White) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Color.White.copy(alpha = 0.75f), fontSize = 11.sp)
        Text(text = value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 17.sp)
    }
}
