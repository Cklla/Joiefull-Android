package com.openclassrooms.joiefull.ui.navigation

import androidx.window.core.layout.WindowSizeClass
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WindowLayoutTest {

    @Test
    fun `isMasterDetailLayout returns true when width is at the Expanded breakpoint`() {
        val windowSizeClass = WindowSizeClass(
            minWidthDp = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND,
            minHeightDp = 900,
        )

        assertTrue(isMasterDetailLayout(windowSizeClass))
    }

    @Test
    fun `isMasterDetailLayout returns true when width is above the Expanded breakpoint`() {
        val windowSizeClass = WindowSizeClass(
            minWidthDp = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND + 200,
            minHeightDp = 900,
        )

        assertTrue(isMasterDetailLayout(windowSizeClass))
    }

    @Test
    fun `isMasterDetailLayout returns false when width is just below the Expanded breakpoint`() {
        val windowSizeClass = WindowSizeClass(
            minWidthDp = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND - 1,
            minHeightDp = 900,
        )

        assertFalse(isMasterDetailLayout(windowSizeClass))
    }

    @Test
    fun `isMasterDetailLayout returns false for a phone-sized width`() {
        val windowSizeClass = WindowSizeClass(
            minWidthDp = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND,
            minHeightDp = 900,
        )

        assertFalse(isMasterDetailLayout(windowSizeClass))
    }
}