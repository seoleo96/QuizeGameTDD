package com.seoleo.quizegametdd

import junit.framework.TestCase.assertEquals
import org.junit.Test

class QuizViewModelTest {

    @Test
    fun correctTwice(){

        val viewModel = QuizViewModel(repository = FakeRepository())
        var actual : UiState = viewModel.init()
        var expected : UIState = UiState.Question(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(text = "A"),
                ChoiceUiState.Question(text = "B"),
                ChoiceUiState.Question(text = "C"),
                ChoiceUiState.Question(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.choose(value = "A")
        expected = UiState.Answered(
            question = "question1",
            answers = listOf<ChoiceUiState>(
                ChoiceUiState.Correct(text = "A"),
                ChoiceUiState.NotAnswered(text = "B"),
                ChoiceUiState.NotAnswered(text = "C"),
                ChoiceUiState.NotAnswered(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(text = "E"),
                ChoiceUiState.Question(text = "F"),
                ChoiceUiState.Question(text = "G"),
                ChoiceUiState.Question(text = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.choose(value = "G")
        expected = UiState.Answered(
            question = "question2",
            answers = listOf<ChoiceUiState>(
                ChoiceUiState.NotAnswered(text = "E"),
                ChoiceUiState.NotAnswered(text = "F"),
                ChoiceUiState.Correct(text = "G"),
                ChoiceUiState.NotAnswered(text = "H"),
            )
        )

        assertEquals(expected, actual)
        actual = viewModel.next()
        expected = UiState.GameOver
        assertEquals(expected, actual)
    }

    @Test
    fun correctThenIncorrect(){
        val viewModel = QuizViewModel(repository = FakeRepository())
        val actual = viewModel.init()
        val expected = UiState.Question(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(text = "A"),
                ChoiceUiState.Question(text = "B"),
                ChoiceUiState.Question(text = "C"),
                ChoiceUiState.Question(text = "D"),
            )
        )
        assertEquals(expected, actual)
        actual = viewModel.choose("A")
        expected = UiState.Answered(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct(text = "A"),
                ChoiceUiState.NotChosen(text = "B"),
                ChoiceUiState.NotChosen(text = "C"),
                ChoiceUiState.NotChosen(text = "D"),
            )
        )
        assertEquals(expected, actual)
        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(text = "E"),
                ChoiceUiState.Question(text = "F"),
                ChoiceUiState.Question(text = "G"),
                ChoiceUiState.Question(text = "H"),
            )
        )
        assertEquals(expected, actual)
        actual = viewModel.choose("H")
        expected = UiState.Last(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.NotChosen(text = "E"),
                ChoiceUiState.NotChosen(text = "F"),
                ChoiceUiState.Correct(text = "G"),
                ChoiceUiState.Incorrect(text = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.GameOver
        assertEquals(expected, actual)
    }
}

class FakeRepository {
    private val list = listOf(
        QuestionAndChoices(
            question = "question1", choices = listOf(
                Choice(value = "A", correct = true),
                Choice(value = "B", correct = false),
                Choice(value = "C", correct = false),
                Choice(value = "D", correct = false)
            )
        ),
        QuestionAndChoices(
            question = "question2", choices = listOf(
                Choice(value = "E", correct = false),
                Choice(value = "F", correct = false),
                Choice(value = "G", correct = true),
                Choice(value = "H", correct = false)
            )
        )
    )

    private var index = 0

    override fun next() {
        index++
    }

    override fun questionAndChoices(): QuestionAndChoices {
        return list[index]
    }

    override fun isLastQuestion() : Boolean {
        return index == list.size - 1
    }
}
