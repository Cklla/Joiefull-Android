package com.openclassrooms.joiefull.ui.navigation

import androidx.window.core.layout.WindowSizeClass

// Bascule en vue tablette/paysage (liste + détails côte à côte) à partir du seuil "Expanded"
fun isMasterDetailLayout(windowSizeClass: WindowSizeClass): Boolean {
    return windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
}