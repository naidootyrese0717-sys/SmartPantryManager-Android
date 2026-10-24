# Smart Pantry Manager — Written Report Template

> Replace all bracketed fields with your own details and actual evidence. Use the institution's cover page, Times New Roman 12 pt, 1.5 line spacing, Harvard referencing, and signed originality declaration. Do not present placeholder screenshots or invented challenges as real.

## Cover page
- Student name: [Your name]
- Student ITS number: [Your student number]
- Module: Mobile App Development 700
- Date submitted: [Date]
- GitHub repository: [Public repository URL]

## Table of contents
1. Introduction
2. System design
3. Screenshots and core-function evidence
4. Key code snippets
5. Challenges and solutions
6. Conclusion and reflection
7. References

## 1. Introduction
Smart Pantry Manager helps users record ingredients they already have and reduce food waste by suggesting recipes only when all required ingredients are available.

## 2. System design
**Screen flow:** Pantry → Add/Edit Ingredient → Pantry; Pantry → Suggested Recipes → Recipe Detail; Pantry → Settings.

**Data model:** `pantry(id, name, qty, unit, expiry)` and `recipes(id, name, ingredients, steps)`. The recipe collection is seeded on first database creation. Pantry data is stored locally in SQLite.

## 3. Screenshots and core-function evidence
Insert actual screenshots from your running emulator/device with captions:
- Pantry list populated
- Add ingredient form
- Validation error
- Edit ingredient result
- Delete confirmation/result
- Suggested recipes with full matches
- Recipe detail
- Settings
- Strict-match test before and after removing a required ingredient
- Data still present after closing and reopening the app

## 4. Key code snippets
Discuss short excerpts from your own code:
1. SQLite table creation and recipe seeding (`DatabaseHelper.java`)
2. Create/update/delete database operations (`DatabaseHelper.java`)
3. Strict matching loop (`DatabaseHelper.java`, `suggestions()`)
4. Form validation (`MainActivity.java`, `form()`)
5. Navigation and data passed with Intent extras (`MainActivity.java`)

## 5. Challenges and solutions
Describe 2–3 real challenges you encountered, how you investigated them, and the actual solution. Do not use generic invented examples.

## 6. Conclusion and reflection
Explain what you learned about Java, Android layouts, Activities/Intents, SQLite persistence, CRUD, and strict matching. Identify improvements you would genuinely make next.

## 7. References
Use Harvard style consistently. Add only sources you actually consulted. Suggested official documentation to consult:
- Android Developers (n.d.) *Save data using SQLite*. Available at: https://developer.android.com/training/data-storage/sqlite (Accessed: [date]).
- Android Developers (n.d.) *Intents and intent filters*. Available at: https://developer.android.com/guide/components/intents-filters (Accessed: [date]).
