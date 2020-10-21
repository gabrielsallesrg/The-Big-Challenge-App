package com.gsrg.tbc.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.gsrg.tbc.R
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        BaristaSleepInteractions.sleep(1000)
        val recyclerView = onView(withId(R.id.recyclerView))
        for (i in 0..5) {
            recyclerView.perform(actionOnItemAtPosition<ViewHolder>(i, click()))
            Espresso.pressBackUnconditionally()
        }
        val refreshButton = onView(withId(R.id.refreshButton))
        refreshButton.perform(click())
        BaristaSleepInteractions.sleep(300)
    }
}
