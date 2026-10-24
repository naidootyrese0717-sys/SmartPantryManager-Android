# Smart Pantry Manager - ingredient library update

This Java Android app uses local SQLite storage. The update adds a searchable built-in ingredient library (80+ common ingredients across fruit, vegetables, protein/dairy, grains/pasta, oils/sauces/spices), multi-select ingredient adding, and 20 additional international recipe ideas. Existing custom ingredient add/edit/delete and exact ingredient-presence recipe matching are retained.

## Open and run
1. Make a backup copy of your current project folder first.
2. Open the `SmartPantryManager` folder in Android Studio.
3. Let Gradle sync finish, then select your phone and press Run.
4. On Pantry, press **+ Choose items**, search and tick ingredients, then press **Add selected ingredients to pantry**.
5. Go to Recipes. Only recipes for which every listed ingredient exists in the pantry with quantity greater than zero are shown. Add custom ingredients using **+ Add a custom ingredient**.

## Database migration
The database version is incremented from 1 to 2. Android's SQLiteOpenHelper will preserve existing pantry rows and refresh the built-in recipe collection on the first launch after installing this version. It does not clear the pantry.

## Notes
- The catalog is a useful starter library, not literally every food sold worldwide. Custom ingredients can still be added.
- Recipe matching checks that each required ingredient is present and has a positive quantity. It does not yet calculate recipe serving sizes or compare exact gram/ml amounts.
- This project archive did not include the Gradle wrapper files. Open it through Android Studio, which will use its configured Gradle installation, or copy the changed Java files into your existing project.
