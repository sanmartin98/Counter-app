package com.cornershop.counterstest.presentation.activity


import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.cornershop.counterstest.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class IncrementCounterTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun incrementCounterTest() {
        val buttonStart = onView(withId(R.id.buttonStart))
        SystemClock.sleep(3000)
        buttonStart.perform(click())
        SystemClock.sleep(3000)

        onView(withId(R.id.recicler_view_counter)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickOnViewChild(R.id.image_view_plus_counter)
            )
        )

        SystemClock.sleep(3000)

        val buttonAddCounter = onView(withId(R.id.button_add_counter))
        SystemClock.sleep(3000)
        buttonAddCounter.perform(click())
    }

    private fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) =
            click().perform(uiController, view.findViewById<View>(viewId))
    }
}
