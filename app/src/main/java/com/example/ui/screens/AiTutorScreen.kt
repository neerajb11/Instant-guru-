package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.model.ChatMessage
import com.example.ui.theme.BitsatAmber
import com.example.ui.theme.BitsatAmberDark
import com.example.ui.theme.BitsatBlue
import com.example.ui.theme.ChemistryEmerald
import com.example.ui.theme.EnglishIndigo
import com.example.ui.theme.LogicPurple
import com.example.ui.theme.MathsBlue
import com.example.ui.theme.PhysicsCoral
import com.example.viewmodel.BitsatViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AiTutorScreen(
    viewModel: BitsatViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.aiTutorState.collectAsState()
    val listState = rememberLazyListState()
    var showPhotoOptions by remember { mutableStateOf(false) }

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header Strip
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = BitsatBlue.copy(alpha = 0.12f),
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "AI Tutor",
                            tint = BitsatBlue,
                            modifier = Modifier.padding(6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "BITSAT AI Super Tutor",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Step-by-step solver + 30-sec speed shortcuts",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Subject Selector Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = state.selectedSubject == null,
                        onClick = { viewModel.selectTutorSubject(null) },
                        label = { Text("All", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BitsatBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                    BitsatSubject.values().forEach { subject ->
                        FilterChip(
                            selected = state.selectedSubject == subject,
                            onClick = {
                                viewModel.selectTutorSubject(
                                    if (state.selectedSubject == subject) null else subject
                                )
                            },
                            label = { Text(subject.shortName, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = when (subject) {
                                    BitsatSubject.PHYSICS -> PhysicsCoral
                                    BitsatSubject.CHEMISTRY -> ChemistryEmerald
                                    BitsatSubject.MATHEMATICS -> MathsBlue
                                    BitsatSubject.ENGLISH -> EnglishIndigo
                                    BitsatSubject.LOGICAL_REASONING -> LogicPurple
                                },
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Quick Suggestion Chips (when chat is short)
        if (state.messages.size <= 2) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Try asking high-yield BITSAT doubts:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SuggestedDoubtChip(
                        text = "King's rule shortcut for Integrals",
                        subject = BitsatSubject.MATHEMATICS,
                        onClick = {
                            viewModel.askSuggestedPrompt(
                                "Give me the fastest shortcut to evaluate definite integrals of the form sin^n(x)/(sin^n(x)+cos^n(x)) in BITSAT.",
                                BitsatSubject.MATHEMATICS
                            )
                        }
                    )
                    SuggestedDoubtChip(
                        text = "MOT Bond Order trick for 15 electrons",
                        subject = BitsatSubject.CHEMISTRY,
                        onClick = {
                            viewModel.askSuggestedPrompt(
                                "What is the 10-second trick to calculate bond order and magnetic behavior in Molecular Orbital Theory for BITSAT?",
                                BitsatSubject.CHEMISTRY
                            )
                        }
                    )
                    SuggestedDoubtChip(
                        text = "Rolling motion on inclined plane speed trick",
                        subject = BitsatSubject.PHYSICS,
                        onClick = {
                            viewModel.askSuggestedPrompt(
                                "How to quickly determine which body reaches bottom first in pure rolling on an incline without calculating acceleration?",
                                BitsatSubject.PHYSICS
                            )
                        }
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            items(state.messages, key = { it.id }) { message ->
                ChatMessageItem(message = message)
            }

            if (state.isLoading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = BitsatBlue
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Super Tutor is analyzing steps & formulating speed tricks...",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        // Photo Upload Options Dialog/Card
        if (showPhotoOptions) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Photo Doubt Solver (Text + Photo)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        IconButton(onClick = { showPhotoOptions = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(18.dp))
                        }
                    }
                    Text(
                        text = "Select a sample question photo or test multimodal problem analysis:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val sampleBitmap = createDoubtBitmap("Physics Circuit: Find equivalent resistance between terminals A and B.")
                                    viewModel.attachPhoto(sampleBitmap)
                                    viewModel.updateInputText("Find equivalent resistance in this circuit for BITSAT")
                                    viewModel.selectTutorSubject(BitsatSubject.PHYSICS)
                                    showPhotoOptions = false
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 2.dp
                        ) {
                            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null, tint = PhysicsCoral)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Physics Photo", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val sampleBitmap = createDoubtBitmap("Organic: Benzene + CH3Cl / AlCl3 -> A -> KMnO4 -> B. Identify B.")
                                    viewModel.attachPhoto(sampleBitmap)
                                    viewModel.updateInputText("Identify product B and give reaction mechanism with speed tip")
                                    viewModel.selectTutorSubject(BitsatSubject.CHEMISTRY)
                                    showPhotoOptions = false
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 2.dp
                        ) {
                            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null, tint = ChemistryEmerald)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Chemistry Photo", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Photo Attachment Preview
        if (state.attachedBitmap != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Photo Attached",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove photo",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(14.dp)
                                .clickable { viewModel.attachPhoto(null) }
                        )
                    }
                }
            }
        }

        // Bottom Input Row
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showPhotoOptions = !showPhotoOptions },
                    modifier = Modifier.testTag("tutor_photo_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Attach photo doubt",
                        tint = if (state.attachedBitmap != null) BitsatAmberDark else MaterialTheme.colorScheme.primary
                    )
                }

                OutlinedTextField(
                    value = state.inputText,
                    onValueChange = { viewModel.updateInputText(it) },
                    placeholder = {
                        Text(
                            text = "Type BITSAT doubt (Math, Phy, Chem, LR)...",
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("tutor_input_field"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BitsatBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = { viewModel.sendDoubt() },
                    modifier = Modifier
                        .background(BitsatBlue, CircleShape)
                        .size(42.dp)
                        .testTag("tutor_send_button"),
                    enabled = !state.isLoading && (state.inputText.isNotBlank() || state.attachedBitmap != null)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send doubt",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    val isUser = message.isFromUser

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        if (!isUser) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = BitsatBlue,
                    shape = CircleShape,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(3.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "BITSAT Super Tutor",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = BitsatBlue
                )
                if (message.subjectTag != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = message.subjectTag.shortName,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        Surface(
            color = if (isUser) BitsatBlue else MaterialTheme.colorScheme.surface,
            contentColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            tonalElevation = if (isUser) 0.dp else 2.dp,
            modifier = Modifier
                .widthIn(max = 340.dp)
                .border(
                    width = if (isUser) 0.dp else 1.dp,
                    color = if (isUser) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (message.imageDescription != null) {
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "📷 ${message.imageDescription}",
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                // Step-by-Step Points Callout (for AI replies)
                if (message.stepByStepPoints.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Step-by-Step Solution Breakdown:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            message.stepByStepPoints.forEach { pt ->
                                Text(
                                    text = pt,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                // ⚡ BITSAT Speed Shortcut Callout Box
                if (message.speedTip != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(BitsatAmber.copy(alpha = 0.15f))
                            .border(1.dp, BitsatAmberDark.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Speed Hack",
                                tint = BitsatAmberDark,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "BITSAT 30-Sec Speed Shortcut",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    color = BitsatAmberDark
                                )
                                Text(
                                    text = message.speedTip,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 15.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestedDoubtChip(
    text: String,
    subject: BitsatSubject,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = BitsatAmberDark,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

// Utility to create a simulated question photo bitmap for testing photo doubts
private fun createDoubtBitmap(caption: String): Bitmap {
    val bitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    val paint = Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 18f
        isAntiAlias = true
    }
    canvas.drawText("BITSAT QUESTION SNAPSHOT", 20f, 40f, paint)
    paint.textSize = 14f
    paint.color = android.graphics.Color.DKGRAY
    val words = caption.split(" ")
    var y = 80f
    var line = ""
    for (word in words) {
        if (line.length + word.length > 30) {
            canvas.drawText(line, 20f, y, paint)
            y += 24f
            line = word
        } else {
            line = if (line.isEmpty()) word else "$line $word"
        }
    }
    if (line.isNotEmpty()) {
        canvas.drawText(line, 20f, y, paint)
    }
    return bitmap
}
