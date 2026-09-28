package com.example.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BitsatDataProvider
import com.example.data.GeminiAiService
import com.example.model.BitsatSubject
import com.example.model.ChatMessage
import com.example.model.Chapter
import com.example.model.Flashcard
import com.example.model.MockTestResult
import com.example.model.Question
import com.example.model.QuestionStatus
import com.example.model.SubjectScore
import com.example.model.WeakAreaItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppScreen {
    DASHBOARD,
    AI_TUTOR,
    NOTES_FLASHCARDS,
    MOCK_TEST,
    ANALYTICS,
    BLUEPRINT
}

data class MockExamUiState(
    val isRunning: Boolean = false,
    val isCompleted: Boolean = false,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<String, Int> = emptyMap(), // questionId -> optionIndex
    val markedForReviewIds: Set<String> = emptySet(),
    val timeRemainingSeconds: Int = 600, // 10 min sprint test
    val totalTimeSeconds: Int = 600,
    val result: MockTestResult? = null,
    val bonusQuestionsUnlocked: Boolean = false
)

data class FlashcardUiState(
    val currentSubject: BitsatSubject = BitsatSubject.PHYSICS,
    val currentCardIndex: Int = 0,
    val isFlipped: Boolean = false,
    val masteredIds: Set<String> = emptySet()
)

data class AiTutorUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val selectedSubject: BitsatSubject? = null,
    val attachedBitmap: Bitmap? = null,
    val isLoading: Boolean = false
)

class BitsatViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.DASHBOARD)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _mockExamState = MutableStateFlow(MockExamUiState())
    val mockExamState: StateFlow<MockExamUiState> = _mockExamState.asStateFlow()

    private val _flashcardState = MutableStateFlow(FlashcardUiState())
    val flashcardState: StateFlow<FlashcardUiState> = _flashcardState.asStateFlow()

    private val _aiTutorState = MutableStateFlow(AiTutorUiState())
    val aiTutorState: StateFlow<AiTutorUiState> = _aiTutorState.asStateFlow()

    private val _selectedChapter = MutableStateFlow<Chapter?>(null)
    val selectedChapter: StateFlow<Chapter?> = _selectedChapter.asStateFlow()

    private val _weakAreas = MutableStateFlow<List<WeakAreaItem>>(BitsatDataProvider.weakAreasSample)
    val weakAreas: StateFlow<List<WeakAreaItem>> = _weakAreas.asStateFlow()

    private var timerJob: Job? = null

    init {
        // Initialize AI Tutor with warm welcome
        val welcomeMessage = ChatMessage(
            id = "msg_welcome",
            isFromUser = false,
            text = "Welcome to your BITSAT Super Tutor! 🎯 I'm primed with the complete BITSAT syllabus across Physics, Chemistry, Maths, English, and Logical Reasoning. Ask me any conceptual doubt, request step-by-step solutions, or upload problem photos. I'll always give you the ⚡ 30-Second BITSAT Speed Shortcut to save time on exam day!",
            speedTip = "Tip: BITSAT has 130 questions in 180 minutes. High accuracy with zero wasted seconds is your ticket to BITS Pilani CS!"
        )
        _aiTutorState.update { it.copy(messages = listOf(welcomeMessage)) }
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // --- AI Tutor Actions ---

    fun updateInputText(newText: String) {
        _aiTutorState.update { it.copy(inputText = newText) }
    }

    fun selectTutorSubject(subject: BitsatSubject?) {
        _aiTutorState.update { it.copy(selectedSubject = subject) }
    }

    fun attachPhoto(bitmap: Bitmap?) {
        _aiTutorState.update { it.copy(attachedBitmap = bitmap) }
    }

    fun sendDoubt() {
        val state = _aiTutorState.value
        val text = state.inputText.trim()
        if (text.isEmpty() && state.attachedBitmap == null) return

        val userMessage = ChatMessage(
            id = "user_${System.currentTimeMillis()}",
            isFromUser = true,
            text = text.ifEmpty { "[Photo Doubt Uploaded]" },
            subjectTag = state.selectedSubject,
            imageDescription = if (state.attachedBitmap != null) "Attached Question Image" else null
        )

        _aiTutorState.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isLoading = true
            )
        }

        viewModelScope.launch {
            val response = GeminiAiService.solveDoubt(
                questionText = text,
                subject = state.selectedSubject,
                bitmap = state.attachedBitmap
            )

            val tutorMessage = ChatMessage(
                id = "tutor_${System.currentTimeMillis()}",
                isFromUser = false,
                text = response.explanation,
                subjectTag = state.selectedSubject,
                speedTip = response.speedTip,
                stepByStepPoints = response.stepByStepPoints
            )

            _aiTutorState.update {
                it.copy(
                    messages = it.messages + tutorMessage,
                    attachedBitmap = null,
                    isLoading = false
                )
            }
        }
    }

    fun askSuggestedPrompt(prompt: String, subject: BitsatSubject) {
        _aiTutorState.update {
            it.copy(inputText = prompt, selectedSubject = subject)
        }
        sendDoubt()
    }

    // --- Flashcards & Notes Actions ---

    fun selectChapter(chapter: Chapter?) {
        _selectedChapter.value = chapter
    }

    fun selectFlashcardSubject(subject: BitsatSubject) {
        _flashcardState.update {
            it.copy(
                currentSubject = subject,
                currentCardIndex = 0,
                isFlipped = false
            )
        }
    }

    fun flipFlashcard() {
        _flashcardState.update { it.copy(isFlipped = !it.isFlipped) }
    }

    fun nextFlashcard(totalCards: Int) {
        if (totalCards == 0) return
        _flashcardState.update {
            it.copy(
                currentCardIndex = (it.currentCardIndex + 1) % totalCards,
                isFlipped = false
            )
        }
    }

    fun previousFlashcard(totalCards: Int) {
        if (totalCards == 0) return
        _flashcardState.update {
            val newIdx = if (it.currentCardIndex - 1 < 0) totalCards - 1 else it.currentCardIndex - 1
            it.copy(
                currentCardIndex = newIdx,
                isFlipped = false
            )
        }
    }

    fun toggleMastery(cardId: String) {
        _flashcardState.update { state ->
            val set = state.masteredIds.toMutableSet()
            if (set.contains(cardId)) {
                set.remove(cardId)
            } else {
                set.add(cardId)
            }
            state.copy(masteredIds = set)
        }
    }

    // --- Mock Test CBT Actions ---

    fun startMockTest(durationMinutes: Int = 10, subjectFilter: BitsatSubject? = null) {
        val questions = if (subjectFilter != null) {
            BitsatDataProvider.mockQuestions.filter { it.subject == subjectFilter }
        } else {
            BitsatDataProvider.mockQuestions
        }

        val totalSeconds = durationMinutes * 60

        _mockExamState.value = MockExamUiState(
            isRunning = true,
            isCompleted = false,
            questions = questions,
            currentQuestionIndex = 0,
            selectedAnswers = emptyMap(),
            markedForReviewIds = emptySet(),
            timeRemainingSeconds = totalSeconds,
            totalTimeSeconds = totalSeconds,
            result = null,
            bonusQuestionsUnlocked = false
        )

        startTimer()
        navigateTo(AppScreen.MOCK_TEST)
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_mockExamState.value.isRunning && _mockExamState.value.timeRemainingSeconds > 0) {
                delay(1000)
                _mockExamState.update {
                    val remaining = it.timeRemainingSeconds - 1
                    if (remaining <= 0) {
                        it.copy(timeRemainingSeconds = 0)
                    } else {
                        it.copy(timeRemainingSeconds = remaining)
                    }
                }
                if (_mockExamState.value.timeRemainingSeconds <= 0) {
                    submitMockTest()
                    break
                }
            }
        }
    }

    fun selectQuestionIndex(index: Int) {
        val total = _mockExamState.value.questions.size
        if (index in 0 until total) {
            _mockExamState.update { it.copy(currentQuestionIndex = index) }
        }
    }

    fun answerCurrentQuestion(optionIndex: Int) {
        val state = _mockExamState.value
        val currentQ = state.questions.getOrNull(state.currentQuestionIndex) ?: return
        val currentMap = state.selectedAnswers.toMutableMap()
        currentMap[currentQ.id] = optionIndex

        // Check if all regular questions answered => trigger BITSAT bonus question unlock
        val allAnswered = state.questions.all { q -> currentMap.containsKey(q.id) }

        _mockExamState.update {
            it.copy(
                selectedAnswers = currentMap,
                bonusQuestionsUnlocked = allAnswered
            )
        }
    }

    fun clearCurrentResponse() {
        val state = _mockExamState.value
        val currentQ = state.questions.getOrNull(state.currentQuestionIndex) ?: return
        val currentMap = state.selectedAnswers.toMutableMap()
        currentMap.remove(currentQ.id)

        _mockExamState.update {
            it.copy(
                selectedAnswers = currentMap,
                bonusQuestionsUnlocked = false
            )
        }
    }

    fun toggleMarkForReview() {
        val state = _mockExamState.value
        val currentQ = state.questions.getOrNull(state.currentQuestionIndex) ?: return
        val marks = state.markedForReviewIds.toMutableSet()
        if (marks.contains(currentQ.id)) {
            marks.remove(currentQ.id)
        } else {
            marks.add(currentQ.id)
        }
        _mockExamState.update { it.copy(markedForReviewIds = marks) }
    }

    fun nextQuestion() {
        val state = _mockExamState.value
        if (state.currentQuestionIndex < state.questions.size - 1) {
            _mockExamState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex + 1) }
        }
    }

    fun previousQuestion() {
        val state = _mockExamState.value
        if (state.currentQuestionIndex > 0) {
            _mockExamState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex - 1) }
        }
    }

    fun getQuestionStatus(questionId: String): QuestionStatus {
        val state = _mockExamState.value
        val isAnswered = state.selectedAnswers.containsKey(questionId)
        val isMarked = state.markedForReviewIds.contains(questionId)

        return when {
            isAnswered && isMarked -> QuestionStatus.ANSWERED_AND_MARKED
            isAnswered -> QuestionStatus.ANSWERED
            isMarked -> QuestionStatus.MARKED_FOR_REVIEW
            else -> QuestionStatus.NOT_ANSWERED
        }
    }

    fun submitMockTest() {
        timerJob?.cancel()
        val state = _mockExamState.value
        val questions = state.questions
        val selected = state.selectedAnswers

        var correctCount = 0
        var incorrectCount = 0
        var unattemptedCount = 0

        val subjectMap = mutableMapOf<BitsatSubject, MutableList<Pair<Question, Boolean?>>>()
        BitsatSubject.values().forEach { sub -> subjectMap[sub] = mutableListOf() }

        for (q in questions) {
            val userOption = selected[q.id]
            if (userOption == null) {
                unattemptedCount++
                subjectMap[q.subject]?.add(Pair(q, null))
            } else if (userOption == q.correctOptionIndex) {
                correctCount++
                subjectMap[q.subject]?.add(Pair(q, true))
            } else {
                incorrectCount++
                subjectMap[q.subject]?.add(Pair(q, false))
            }
        }

        // BITSAT Marking Scheme: +3 for correct, -1 for incorrect, 0 for unattempted
        val totalScore = (correctCount * 3) - (incorrectCount * 1)
        val maxScore = questions.size * 3
        val attempted = correctCount + incorrectCount
        val accuracy = if (attempted > 0) (correctCount.toFloat() / attempted) * 100f else 0f
        val timeSpent = state.totalTimeSeconds - state.timeRemainingSeconds

        val breakdown = subjectMap.mapValues { entry ->
            val list = entry.value
            val subCorrect = list.count { it.second == true }
            val subIncorrect = list.count { it.second == false }
            val subUnattempted = list.count { it.second == null }
            val subScore = (subCorrect * 3) - (subIncorrect * 1)
            SubjectScore(
                correct = subCorrect,
                incorrect = subIncorrect,
                unattempted = subUnattempted,
                score = subScore,
                maxScore = list.size * 3
            )
        }

        val result = MockTestResult(
            testTitle = "BITSAT Speed Mock Sprint",
            totalQuestions = questions.size,
            attempted = attempted,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            totalScore = totalScore,
            maxScore = maxScore,
            accuracyPercentage = accuracy,
            timeTakenSeconds = timeSpent.toLong(),
            subjectBreakdown = breakdown
        )

        _mockExamState.update {
            it.copy(
                isRunning = false,
                isCompleted = true,
                result = result
            )
        }
    }

    fun restartMockExam() {
        startMockTest(10)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
