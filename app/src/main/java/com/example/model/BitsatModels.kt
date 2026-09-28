package com.example.model

enum class BitsatSubject(
    val id: String,
    val title: String,
    val questionCount: Int,
    val totalMarks: Int,
    val shortName: String
) {
    PHYSICS("physics", "Physics", 30, 90, "PHY"),
    CHEMISTRY("chemistry", "Chemistry", 30, 90, "CHEM"),
    MATHEMATICS("maths", "Mathematics", 40, 120, "MATH"),
    ENGLISH("english", "English Proficiency", 10, 30, "ENG"),
    LOGICAL_REASONING("logic", "Logical Reasoning", 20, 60, "LR");

    companion object {
        const val TOTAL_REGULAR_QUESTIONS = 130
        const val TOTAL_REGULAR_MARKS = 390
        const val BONUS_QUESTIONS = 12
        const val BONUS_MARKS = 36
        const val DURATION_MINUTES = 180
    }
}

data class Chapter(
    val id: String,
    val subject: BitsatSubject,
    val name: String,
    val weightage: String, // e.g. "High Yield", "Medium", "Core"
    val expectedQuestions: Int,
    val summary: String,
    val keyFormulas: List<String>,
    val speedTricks: List<String>
)

data class Flashcard(
    val id: String,
    val subject: BitsatSubject,
    val chapterName: String,
    val frontPrompt: String,
    val backAnswer: String,
    val formulaOrTrick: String,
    val isMastered: Boolean = false
)

data class Question(
    val id: String,
    val subject: BitsatSubject,
    val chapterName: String,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val speedShortcut: String,
    val difficulty: String = "BITSAT Medium" // Easy, Medium, High Speed
)

enum class QuestionStatus {
    NOT_VISITED,
    NOT_ANSWERED,
    ANSWERED,
    MARKED_FOR_REVIEW,
    ANSWERED_AND_MARKED
}

data class MockTestResult(
    val testTitle: String,
    val totalQuestions: Int,
    val attempted: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val totalScore: Int,
    val maxScore: Int,
    val accuracyPercentage: Float,
    val timeTakenSeconds: Long,
    val subjectBreakdown: Map<BitsatSubject, SubjectScore>
)

data class SubjectScore(
    val correct: Int,
    val incorrect: Int,
    val unattempted: Int,
    val score: Int,
    val maxScore: Int
)

data class ChatMessage(
    val id: String,
    val isFromUser: Boolean,
    val text: String,
    val subjectTag: BitsatSubject? = null,
    val speedTip: String? = null,
    val stepByStepPoints: List<String> = emptyList(),
    val imageDescription: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class WeakAreaItem(
    val subject: BitsatSubject,
    val topicName: String,
    val accuracy: Int, // Percentage
    val priority: String, // "Critical", "Moderate", "Watchlist"
    val recommendedAction: String
)
