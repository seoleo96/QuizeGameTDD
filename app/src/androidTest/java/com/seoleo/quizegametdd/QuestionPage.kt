package com.seoleo.quizegametdd

import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*

class QuestionPage {

    fun checkQuestionVisible(question: String) {
        onView(
            allOf(
                withId(R.id.questionTextView),
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(matches(withText(question)))
    }

    fun checkAnswers(answers: List<String>) {
        answers.forEach { answer ->
            val answerView = onView(
                allOf(
                    withText(answer),
                    isAssignableFrom(Button::class.java),
                    withParent(isAssignableFrom(LinearLayout::class.java)),
                    withParent(withId(R.id.rootLayout))
                )
            )
            answerView.check(matches(isDisplayed()))
            answerView.check(matches(ButtonColorMatcher(Color.parseColor("#7A84DA"))))
        }
    }

    fun clickAnswer(value: String) {
        onView(
            allOf(
                withText(value),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).perform(click())
    }
}
