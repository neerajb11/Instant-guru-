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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GeminiAiService
import com.example.ui.theme.BitsatAmber
import com.example.ui.theme.BitsatAmberDark
import com.example.ui.theme.BitsatBlue
import com.example.ui.theme.BitsatBlueDark
import com.example.ui.theme.ChemistryEmerald
import com.example.ui.theme.LogicPurple
import com.example.ui.theme.PhysicsCoral

@Composable
fun BlueprintScreen(
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf(0) }
    val clipboardManager = LocalClipboardManager.current
    var copiedPrompt by remember { mutableStateOf(false) }

    val sections = listOf(
        "1. Tech Stack",
        "2. MVP Scope",
        "3. Architecture",
        "4. AI Tutor Prompt",
        "5. Content Strategy",
        "6. Dev Roadmap",
        "7. Free Tools"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Blueprint Banner
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = BitsatAmberDark.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Architecture,
                            contentDescription = null,
                            tint = BitsatAmberDark,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "EdTech Blueprint & Specs",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Production Guide for BITSAT Prep Platform (Mobile + Web)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Section Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedSection,
            edgePadding = 12.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            sections.forEachIndexed { index, title ->
                Tab(
                    selected = selectedSection == index,
                    onClick = { selectedSection = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedSection == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedSection) {
                0 -> {
                    // Tech Stack 2026
                    item {
                        TechCard(
                            badge = "Frontend (Mobile)",
                            title = "Kotlin + Jetpack Compose (or Flutter / React Native)",
                            details = "Fast 120Hz native animations, single codebase, offline Room persistence, photo doubt camera integration.",
                            tagColor = BitsatBlue
                        )
                    }
                    item {
                        TechCard(
                            badge = "Frontend (Web)",
                            title = "Next.js 15 (React 19) + Tailwind CSS + KaTeX",
                            details = "Instant Math formula rendering via KaTeX/MathJax, real BITSAT full-screen CBT simulator for desktop browser testing.",
                            tagColor = ChemistryEmerald
                        )
                    }
                    item {
                        TechCard(
                            badge = "AI Engine (Doubt Solver)",
                            title = "Google Gemini 3.5 Flash (via REST / Firebase Genkit)",
                            details = "Sub-second latency, multimodal image/equation OCR, ultra-low cost ($0.075 / 1M tokens), 1M context window.",
                            tagColor = BitsatAmberDark
                        )
                    }
                    item {
                        TechCard(
                            badge = "Backend & Database",
                            title = "Supabase or Firebase Cloud Firestore",
                            details = "PostgreSQL auth, real-time sync, Row-Level Security, generous free tier (50,000 MAUs, 500MB DB).",
                            tagColor = LogicPurple
                        )
                    }
                }

                1 -> {
                    // MVP Feature List
                    item {
                        MvpPhaseCard(
                            phase = "Phase 1: Immediate MVP (Weeks 1-4)",
                            items = listOf(
                                "AI Tutor with step-by-step solutions and ⚡ 30-Sec Speed Tricks",
                                "Text & Photo doubt input for Math, Physics, Chemistry, English, LR",
                                "High-yield chapter notes with formula sheets & key tricks",
                                "10-Minute BITSAT Speed Sprint CBT Mock (+3 / -1 marking)"
                            ),
                            isCurrent = true
                        )
                    }
                    item {
                        MvpPhaseCard(
                            phase = "Phase 2: Post-MVP (Weeks 5-8)",
                            items = listOf(
                                "Full 180-minute 130-question BITSAT Mock Tests with 12 Bonus Questions",
                                "Weak-area automated diagnosis & custom remediation quizzes",
                                "Bookmark & Revision notebook for tricky questions",
                                "All-India Leaderboard & percentile ranking"
                            ),
                            isCurrent = false
                        )
                    }
                    item {
                        MvpPhaseCard(
                            phase = "Phase 3: Scale & Monetization (Weeks 9-12)",
                            items = listOf(
                                "Target BITS Pilani CS Cutoff Predictor with historic score curves",
                                "Personalized daily study planner with AI calendar sync",
                                "Freemium tier (Free 5 doubts/day + $5/month unlimited pro)"
                            ),
                            isCurrent = false
                        )
                    }
                }

                2 -> {
                    // System Architecture
                    item {
                        ArchitectureDiagramCard()
                    }
                }

                3 -> {
                    // AI Tutor System Prompt
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Production System Prompt", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Button(
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(GeminiAiService.BITSAT_SYSTEM_PROMPT))
                                            copiedPrompt = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = BitsatBlue),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (copiedPrompt) Icons.Default.Check else Icons.Default.ContentCopy,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(if (copiedPrompt) "Copied" else "Copy Prompt", fontSize = 11.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = GeminiAiService.BITSAT_SYSTEM_PROMPT,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace,
                                        lineHeight = 16.sp,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                4 -> {
                    // Copyright-Free Content Strategy
                    item {
                        StrategyCard(
                            title = "1. NCERT Core & Open Syllabus",
                            description = "NCERT textbooks form the verbatim legal foundation of CBSE and BITSAT syllabus. Use NCERT concepts, definitions, and standard problem schemas freely."
                        )
                    }
                    item {
                        StrategyCard(
                            title = "2. Algorithmic Question Synthesis with Gemini",
                            description = "Use Gemini 3.5 Flash to generate fresh, original questions based on past exam archetypes by changing numeric values, chemical ligands, and circuit topologies."
                        )
                    }
                    item {
                        StrategyCard(
                            title = "3. Public Domain Memory-Based PYQs",
                            description = "BITSAT does not release official question papers. Memory-based recall questions discussed publicly in forums are fair-use for educational practice when rewritten."
                        )
                    }
                }

                5 -> {
                    // Dev Roadmap
                    item {
                        RoadmapTimeline()
                    }
                }

                6 -> {
                    // Free & Cost-Effective Tools
                    item {
                        ToolsGrid()
                    }
                }
            }
        }
    }
}

@Composable
private fun TechCard(badge: String, title: String, details: String, tagColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = tagColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = badge,
                    color = tagColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = details,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun MvpPhaseCard(phase: String, items: List<String>, isCurrent: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) BitsatBlue.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
        ),
        border = if (isCurrent) androidx.compose.foundation.BorderStroke(1.5.dp, BitsatBlue) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = phase, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (isCurrent) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = ChemistryEmerald, shape = RoundedCornerShape(4.dp)) {
                        Text(
                            text = "BUILT IN APP",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach { item ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "• ", color = BitsatBlue, fontWeight = FontWeight.Bold)
                    Text(text = item, fontSize = 12.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun ArchitectureDiagramCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "High-Level System Architecture", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(12.dp))

            ArchBox(label = "Clients", text = "Android Native (Compose) + Web (Next.js 15 PWA)", color = BitsatBlue)
            ArrowDown()
            ArchBox(label = "API Gateway", text = "Cloudflare Workers / Vercel Edge Serverless", color = LogicPurple)
            ArrowDown()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ArchBox(
                    label = "AI Brain",
                    text = "Gemini 3.5 Flash\n(Multimodal & Math)",
                    color = BitsatAmberDark,
                    modifier = Modifier.weight(1f)
                )
                ArchBox(
                    label = "Database",
                    text = "Supabase / Room\n(User, Quizzes, Notes)",
                    color = ChemistryEmerald,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ArchBox(label: String, text: String, color: Color, modifier: Modifier = Modifier.fillMaxWidth()) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.12f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = color)
            Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun ArrowDown() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "↓", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}

@Composable
private fun StrategyCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 12.sp, lineHeight = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun RoadmapTimeline() {
    val steps = listOf(
        Pair("Weeks 1 - 2", "Project setup, Syllabus database schema, Material 3 UI scaffold, Gemini 3.5 Flash doubt solver integration."),
        Pair("Weeks 3 - 4", "Formula sheets, interactive flashcards, 10-minute BITSAT Speed Sprint CBT engine with +3 / -1 scoring."),
        Pair("Weeks 5 - 6", "Full 130-question CBT simulator, 12 Bonus Questions unlocking logic, instant test review with speed shortcuts."),
        Pair("Weeks 7 - 8", "Weak-area analytics radar, student revision notebook, Photo camera doubt OCR parsing."),
        Pair("Weeks 9 - 10", "Web PWA deployment via Next.js 15, cloud sync across Android and Desktop browsers."),
        Pair("Weeks 11 - 12", "All-India percentile ranking, user testing with BITS aspirants, Play Store launch.")
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        steps.forEach { (time, desc) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Surface(
                        color = BitsatBlue.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = time,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = BitsatBlue,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = desc, fontSize = 12.sp, lineHeight = 16.sp, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ToolsGrid() {
    val tools = listOf(
        Triple("Google Gemini 3.5 Flash", "Free Tier / Ultra Low Cost", "Super fast STEM reasoning & photo OCR"),
        Triple("Supabase / Firestore", "Free 50K Users", "Authentication, database & realtime updates"),
        Triple("KaTeX / MathJax", "100% Free & Open Source", "LaTeX mathematical formula rendering"),
        Triple("Cloudflare Pages & Workers", "Free 100K Req/Day", "Edge hosting & zero-cost serverless backend"),
        Triple("Vercel", "Free Hobby Tier", "Instant Next.js web application deployment")
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        tools.forEach { (name, tier, use) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Surface(color = ChemistryEmerald.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp)) {
                            Text(
                                text = tier,
                                color = ChemistryEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = use, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
