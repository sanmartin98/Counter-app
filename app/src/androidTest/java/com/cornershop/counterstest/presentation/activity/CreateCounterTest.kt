package com.cornershop.counterstest.presentation.activity


import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import com.cornershop.counterstest.R
import org.junit.Rule
import org.junit.Test

@LargeTest
class CreateCounterTest {

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    @Test
    fun createCounterTest() {
        val buttonStart = onView(withId(R.id.buttonStart)).check(matches(isDisplayed()))
        buttonStart.perform(click())

        val buttonAddCounter = onView(withId(R.id.button_add_counter)).check(matches(isDisplayed()))
        buttonAddCounter.perform(click())

        val textInputCreateCounter = onView(withId(R.id.text_input_create_counter)).check(matches(isDisplayed()))
        textInputCreateCounter.perform(replaceText("Cups of coffee"), closeSoftKeyboard())
        SystemClock.sleep(1000)

        val textViewSave = onView(withId(R.id.text_view_save_counter)).check(matches(isDisplayed()))
        textViewSave.perform(click())
        SystemClock.sleep(5000)
        buttonAddCounter.perform(click())
    }
}
