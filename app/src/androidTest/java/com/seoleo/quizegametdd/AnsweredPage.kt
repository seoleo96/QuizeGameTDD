package com.seoleo.quizegametdd

import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.*

class AnsweredPage {

    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.actionButton),
                withText("next"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(matches(isDisplayed()))
    }

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

    fun checkAnswerCorrect(text: String) {
        onView(
            allOf(
                withText(text),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(matches(ButtonColorMatcher(Color.parseColor("#80E38A"))))
    }

    fun checkChoicesNotAvailable(choices: List<String>) {
        choices.forEach { text ->
            onView(
                CoreMatchers.allOf(
                    withText(text),
                    isAssignableFrom(Button::class.java),
                    withParent(isAssignableFrom(LinearLayout::class.java)),
                    withParent(withId(R.id.rootLayout))
                )
            ).check(matches(ButtonColorMatcher(Color.parseColor("#6E7292"))))
        }
    }

    fun clickChoice(text: String) {
        onView(
            allOf(
                withText(text),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).perform(click())
    }

    fun clickNext() {
        onView(
            allOf(
                withId(R.id.actionButton),
                withText("next"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).perform(click())
    }

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.actionButton),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(matches(not(isDisplayed())))
    }

    fun checkAnswerIncorrect(text: String) {
        onView(
            allOf(
                withText(text),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        ).check(matches(ButtonColorMatcher(Color.parseColor("#E63B3B"))))
    }
}
