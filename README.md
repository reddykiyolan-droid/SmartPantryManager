# Smart Pantry Manager

A Java Android project for managing pantry ingredients and suggesting recipes only when the pantry has every required ingredient in sufficient quantity.

## Technology
- Java and Android Studio
- SQLiteOpenHelper for local persistence
- Android ListView/ArrayAdapter for lists
- Intents for navigation between screens

## Implemented in the supplied source
- Add, view, edit, and delete pantry ingredients
- Quantity, unit, optional expiry date, and basic form validation
- 18 seeded recipes
- Strict matching logic intended to exclude recipes with missing ingredients, insufficient quantity, or incompatible units
- Pantry, suggested recipes, recipe detail, all recipes, and settings screens
- Persisted expiry-alert preference
- No maps or location services

## Open the project
1. Install Android Studio and an Android SDK compatible with the project configuration.
2. In Android Studio, choose **Open** and select this `SmartPantryManager` folder (the folder containing `settings.gradle`).
3. Allow Gradle sync to finish. If Android Studio asks to install a missing SDK, follow its prompt.
4. Select or create an Android emulator, or connect an Android phone with USB debugging enabled.
5. Press **Run**.

## Suggested manual checks (after the app builds)
1. Add ingredients and verify they remain after closing/reopening the app.
2. Add all required ingredients for a recipe and confirm it appears in Suggested Recipes.
3. Remove one required ingredient or reduce its quantity and confirm that recipe is no longer suggested.
4. Try blank names and invalid quantities and verify validation.
5. Edit and delete a pantry item.
6. Open Recipe Collection and each recipe detail screen.
7. Change the setting, leave the screen, return, and verify it persists.

## Before submitting
**This source has not yet been confirmed by a successful Android Studio build or live-device test.** Build and test it yourself, fix any errors, and capture genuine screenshots from the running app. Do not describe unperformed tests as completed.

The assignment also requires a public GitHub repository, a genuine development history (follow the brief's commit requirement), a 5–7 minute narrated demonstration of the working app, and a report containing actual app screenshots. The visual preview supplied separately is not a substitute for a live-app demonstration.
