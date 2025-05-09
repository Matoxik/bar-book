## Main Features

### Menu
- Cocktail data is dynamically fetched from an API in JSON format ([TheCocktailDB API](https://www.thecocktaildb.com/api.php)).
- The drink list is presented as a grid of thumbnails, allowing for quick browsing of available options.
- Users can search for cocktails by name using a dedicated search feature.
- Advanced filters are available via a sidebar, enabling users to narrow down results by alcohol type, ingredients, or category. Filtering is performed through API queries that return JSON responses-the final list is the intersection of results from the three selected filters (e.g., only alcoholic drinks, with rum, classified as a shot).

### Drink Detail View
- Displays a cocktail photo, the list of required ingredients, and preparation instructions.

### Shopping List
- The app allows users to generate a shopping list based on selected cocktails and ingredients the user already has.
- The completed list can be sent via SMS using a Floating Action Button (FAB).
- Ingredient quantities on the list automatically scale depending on the number of servings the user plans to prepare.

### Challenge Mode
- A competition mode is available, challenging users to drink a selected beverage as quickly as possible, with results stored locally in a Room database.
- When a best time is set, a confetti animation is triggered.
- Each drink has its own separate leaderboard.
- Timing is managed using a built-in stopwatch.
- Users can clear data from a selected leaderboard using a FAB.
- Navigation is handled via the home screen and a bottom navigation bar, providing quick access to all key sections.

---

## Application Architecture

The app is designed using the **MVVM (Model-View-ViewModel)** architecture, ensuring a clear separation between the user interface layer (UI, located in the `Pages` folder) and business logic.

- UI-related data that needs to persist across app refreshes is stored in **ViewModels**.
- Communication between the UI layer and ViewModels is handled using **Kotlin Flow** streams.
- Access to both API-fetched and locally stored data is managed through a **Repository** layer.


![123](https://github.com/user-attachments/assets/94c96ef1-f7a0-4a5e-a9fd-646fcc3dbc4a)

![456](https://github.com/user-attachments/assets/415b0bc9-087f-4d2a-b8bd-2949f9861df9)

