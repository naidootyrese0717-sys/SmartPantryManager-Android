package za.co.smartpantry;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context c) { super(c, "smart_pantry.db", null, 2); }
    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,qty REAL NOT NULL,unit TEXT NOT NULL,expiry TEXT)");
        db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,ingredients TEXT NOT NULL,steps TEXT NOT NULL)");
        seed(db);
    }
    private void seed(SQLiteDatabase db) {
        String[][] data = {
          {"Tomato Egg Scramble","egg,tomato,salt","1. Chop tomato. 2. Beat eggs with salt. 3. Cook together in a pan until set."},
          {"Cheese Omelette","egg,cheese,salt","1. Beat eggs with salt. 2. Cook in a pan. 3. Add cheese and fold."},
          {"Banana Pancakes","banana,egg,flour","1. Mash banana. 2. Mix with egg and flour. 3. Cook small pancakes in a pan."},
          {"Garlic Tomato Pasta","pasta,tomato,garlic,oil,salt","1. Boil pasta. 2. Cook garlic and tomato in oil. 3. Toss together and season."},
          {"Simple Fried Rice","rice,egg,carrot,oil,salt","1. Dice carrot. 2. Fry carrot in oil. 3. Add cooked rice and egg; stir until cooked."},
          {"Potato Hash","potato,onion,oil,salt","1. Dice potato and onion. 2. Fry until tender and golden. 3. Season and serve."},
          {"Peanut Banana Toast","bread,peanut butter,banana","1. Toast bread. 2. Spread peanut butter. 3. Top with sliced banana."},
          {"Cheesy Potato Bake","potato,cheese,milk,salt","1. Slice potato thinly. 2. Layer with milk and cheese. 3. Bake until tender."},
          {"Carrot Soup","carrot,onion,garlic,water,salt","1. Chop vegetables. 2. Simmer in water until soft. 3. Blend carefully and season."},
          {"Tuna Pasta","pasta,tuna,mayonnaise,salt","1. Boil pasta and cool. 2. Mix tuna and mayonnaise. 3. Combine and season."},
          {"Apple Oat Bowl","apple,oats,milk,cinnamon","1. Chop apple. 2. Cook oats in milk. 3. Stir in apple and cinnamon."},
          {"Bean Tomato Stew","beans,tomato,onion,garlic,salt","1. Cook onion and garlic. 2. Add tomato and beans. 3. Simmer and season."},
          {"Spinach Omelette","egg,spinach,cheese,salt","1. Beat eggs. 2. Wilt spinach in pan. 3. Add egg and cheese; cook through."},
          {"Yogurt Fruit Bowl","yogurt,banana,apple","1. Slice fruit. 2. Spoon yogurt into a bowl. 3. Add fruit and mix."},
          {"Corn Fritters","corn,egg,flour,oil,salt","1. Mix corn, egg, flour and salt. 2. Spoon into hot oiled pan. 3. Cook both sides."},
          {"Avocado Toast","bread,avocado,lemon,salt","1. Toast bread. 2. Mash avocado with lemon and salt. 3. Spread on toast."},
          {"Cucumber Yogurt Salad","cucumber,yogurt,lemon,salt","1. Slice cucumber. 2. Mix yogurt, lemon and salt. 3. Toss together."},
          {"Lentil Carrot Soup","lentils,carrot,onion,water,salt","1. Chop vegetables. 2. Simmer all ingredients until lentils are soft. 3. Season."},
          {"Honey Apple Oats","apple,oats,milk,honey","1. Cook oats in milk. 2. Dice and add apple. 3. Finish with honey."},
          {"Garlic Butter Mushrooms","mushroom,garlic,butter,salt","1. Slice mushrooms. 2. Melt butter and cook garlic. 3. Add mushrooms and sauté."},
          {"Italian Tomato Basil Pasta","pasta,tomato,garlic,olive oil,basil,salt","1. Boil pasta. 2. Sauté garlic in olive oil. 3. Add tomato and basil, simmer, then toss with pasta."},
          {"Japanese Egg Fried Rice","rice,egg,carrot,soy sauce,cooking oil","1. Use cooked rice. 2. Fry carrot, add rice and egg. 3. Season with soy sauce and cook through."},
          {"Thai Coconut Chickpea Curry","chickpeas,coconut milk,onion,garlic,curry powder,rice","1. Cook onion and garlic. 2. Add curry powder, chickpeas and coconut milk. 3. Simmer and serve with rice."},
          {"Mexican Bean Tacos","tortillas,beans,tomato,onion,cumin,lemon","1. Warm beans with onion and cumin. 2. Chop tomato. 3. Fill tortillas and finish with lemon."},
          {"Indian Lentil Dal","lentils,onion,garlic,ginger,turmeric,cumin,salt","1. Simmer lentils until tender. 2. Cook onion, garlic, ginger and spices. 3. Combine and simmer."},
          {"Greek Cucumber Yogurt Bowl","cucumber,yogurt,lemon,olive oil,salt","1. Chop cucumber. 2. Mix yogurt, lemon and olive oil. 3. Combine and season."},
          {"Korean-Style Soy Noodles","noodles,soy sauce,garlic,sesame oil,carrot","1. Cook noodles. 2. Stir-fry carrot and garlic. 3. Toss with soy sauce and sesame oil."},
          {"Moroccan Chickpea Couscous","chickpeas,couscous,carrot,onion,cumin,cinnamon","1. Prepare couscous. 2. Cook onion and carrot with cumin and cinnamon. 3. Add chickpeas and serve over couscous."},
          {"South African Tomato Bredie","tomato,lamb,onion,potato,garlic,salt","1. Brown lamb and onion. 2. Add tomato, garlic and potato. 3. Cover and simmer until tender."},
          {"French Mushroom Omelette","egg,mushroom,butter,salt,black pepper","1. Sauté mushrooms in butter. 2. Beat and season eggs. 3. Pour into pan and fold when set."},
          {"Spanish Potato Tortilla","potato,egg,onion,olive oil,salt","1. Thinly slice potato and onion. 2. Cook gently in olive oil. 3. Add beaten eggs and cook until set."},
          {"Vietnamese-Style Lime Noodles","noodles,carrot,cucumber,soy sauce,lime","1. Cook and cool noodles. 2. Slice vegetables. 3. Toss with soy sauce and lime juice."},
          {"Mediterranean Chickpea Salad","chickpeas,tomato,cucumber,lemon,olive oil,parsley","1. Drain chickpeas. 2. Chop tomato and cucumber. 3. Toss with lemon, olive oil and parsley."},
          {"Simple Chicken Curry","chicken,onion,garlic,curry powder,tomato,rice","1. Brown chicken and onion. 2. Add garlic, curry powder and tomato. 3. Simmer until chicken is cooked; serve with rice."},
          {"Creamy Spinach Pasta","pasta,spinach,cream,garlic,cheese,salt","1. Boil pasta. 2. Sauté garlic and spinach, add cream. 3. Toss with pasta and cheese."},
          {"Caribbean Coconut Rice","rice,coconut milk,salt","1. Rinse rice. 2. Cook with coconut milk and water until tender. 3. Rest and fluff."},
          {"Chinese-Style Garlic Broccoli","broccoli,garlic,soy sauce,cooking oil","1. Cut broccoli. 2. Stir-fry garlic in oil. 3. Add broccoli and a splash of water and soy sauce; cook until tender-crisp."},
          {"Middle Eastern Hummus Bowl","chickpeas,lemon,garlic,olive oil,salt","1. Blend chickpeas, lemon, garlic and olive oil. 2. Add a little water to smooth. 3. Season and serve."},
          {"Berry Overnight Oats","oats,milk,yogurt,blueberry,honey","1. Mix oats, milk and yogurt. 2. Refrigerate overnight. 3. Top with blueberries and honey."}
        };
        for(String[] r:data){ ContentValues v=new ContentValues(); v.put("name",r[0]); v.put("ingredients",r[1]); v.put("steps",r[2]); db.insert("recipes",null,v); }
    }
    @Override public void onUpgrade(SQLiteDatabase db,int oldV,int newV) {
        if (oldV < 2) {
            // Keep the user's pantry; refresh the built-in recipe collection safely.
            db.delete("recipes", null, null);
            seed(db);
        }
    }

    public static class CatalogItem {
        public final String name, category, unit;
        CatalogItem(String n, String c, String u) { name=n; category=c; unit=u; }
    }

    // Built-in ingredient library. Selecting an item adds it to the user's pantry.
    public List<CatalogItem> catalog() {
        List<CatalogItem> a = new ArrayList<>();
        String[][] rows = {
          {"Apple","Fruit","pieces"},{"Avocado","Fruit","pieces"},{"Banana","Fruit","pieces"},{"Blueberry","Fruit","g"},{"Lemon","Fruit","pieces"},{"Lime","Fruit","pieces"},{"Mango","Fruit","pieces"},{"Orange","Fruit","pieces"},{"Pear","Fruit","pieces"},{"Strawberry","Fruit","g"},{"Pineapple","Fruit","pieces"},{"Coconut milk","World pantry","ml"},
          {"Tomato","Vegetables","pieces"},{"Onion","Vegetables","pieces"},{"Garlic","Vegetables","cloves"},{"Potato","Vegetables","pieces"},{"Sweet potato","Vegetables","pieces"},{"Carrot","Vegetables","pieces"},{"Spinach","Vegetables","g"},{"Broccoli","Vegetables","g"},{"Cauliflower","Vegetables","g"},{"Cabbage","Vegetables","g"},{"Cucumber","Vegetables","pieces"},{"Bell pepper","Vegetables","pieces"},{"Chilli","Vegetables","pieces"},{"Mushroom","Vegetables","g"},{"Zucchini","Vegetables","pieces"},{"Eggplant","Vegetables","pieces"},{"Pumpkin","Vegetables","g"},{"Corn","Vegetables","g"},{"Lettuce","Vegetables","g"},{"Green beans","Vegetables","g"},
          {"Egg","Protein & dairy","pieces"},{"Milk","Protein & dairy","ml"},{"Cheese","Protein & dairy","g"},{"Butter","Protein & dairy","g"},{"Yogurt","Protein & dairy","g"},{"Cream","Protein & dairy","ml"},{"Chicken","Protein & dairy","g"},{"Beef","Protein & dairy","g"},{"Lamb","Protein & dairy","g"},{"Fish","Protein & dairy","g"},{"Salmon","Protein & dairy","g"},{"Tuna","Protein & dairy","g"},{"Prawns","Protein & dairy","g"},{"Tofu","Protein & dairy","g"},{"Chickpeas","Protein & dairy","g"},{"Lentils","Protein & dairy","g"},{"Beans","Protein & dairy","g"},{"Peanuts","Protein & dairy","g"},{"Peanut butter","Protein & dairy","g"},
          {"Rice","Grains & pasta","g"},{"Pasta","Grains & pasta","g"},{"Noodles","Grains & pasta","g"},{"Flour","Grains & pasta","g"},{"Bread","Grains & pasta","slices"},{"Oats","Grains & pasta","g"},{"Couscous","Grains & pasta","g"},{"Quinoa","Grains & pasta","g"},{"Tortillas","Grains & pasta","pieces"},{"Cornmeal","Grains & pasta","g"},
          {"Olive oil","Oils, sauces & spices","ml"},{"Cooking oil","Oils, sauces & spices","ml"},{"Oil","Oils, sauces & spices","ml"},{"Water","Oils, sauces & spices","ml"},{"Soy sauce","Oils, sauces & spices","ml"},{"Vinegar","Oils, sauces & spices","ml"},{"Mayonnaise","Oils, sauces & spices","g"},{"Mustard","Oils, sauces & spices","g"},{"Tomato paste","Oils, sauces & spices","g"},{"Stock","Oils, sauces & spices","ml"},{"Honey","Oils, sauces & spices","g"},{"Sugar","Oils, sauces & spices","g"},{"Salt","Oils, sauces & spices","g"},{"Black pepper","Oils, sauces & spices","g"},{"Paprika","Oils, sauces & spices","g"},{"Cumin","Oils, sauces & spices","g"},{"Curry powder","Oils, sauces & spices","g"},{"Turmeric","Oils, sauces & spices","g"},{"Cinnamon","Oils, sauces & spices","g"},{"Ginger","Oils, sauces & spices","g"},{"Coriander","Oils, sauces & spices","g"},{"Basil","Oils, sauces & spices","g"},{"Oregano","Oils, sauces & spices","g"},{"Parsley","Oils, sauces & spices","g"},{"Chilli flakes","Oils, sauces & spices","g"},{"Sesame oil","Oils, sauces & spices","ml"},{"Curry paste","Oils, sauces & spices","g"},{"Pesto","Oils, sauces & spices","g"}
        };
        for (String[] r : rows) a.add(new CatalogItem(r[0],r[1],r[2]));
        return a;
    }
    public long saveItem(long id,String name,double qty,String unit,String expiry) {
        ContentValues v=new ContentValues(); v.put("name",normalize(name)); v.put("qty",qty); v.put("unit",unit); v.put("expiry",expiry);
        SQLiteDatabase db=getWritableDatabase();
        if(id>0) return db.update("pantry",v,"id=?",new String[]{String.valueOf(id)});
        return db.insert("pantry",null,v);
    }
    public static String normalize(String s) {
        String n=s.trim().toLowerCase(Locale.ROOT);
        if(n.endsWith("ies") && n.length()>4) n=n.substring(0,n.length()-3)+"y";
        else if(n.endsWith("oes") && n.length()>4) n=n.substring(0,n.length()-2);
        else if(n.endsWith("s") && !n.endsWith("ss") && n.length()>3) n=n.substring(0,n.length()-1);
        return n;
    }
    public List<PantryItem> items() {
        List<PantryItem> out=new ArrayList<>(); Cursor c=getReadableDatabase().rawQuery("SELECT id,name,qty,unit,expiry FROM pantry ORDER BY name",null);
        while(c.moveToNext()) out.add(new PantryItem(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));
        c.close(); return out;
    }
    public PantryItem item(long id) { for(PantryItem p:items()) if(p.id==id)return p; return null; }
    public void delete(long id){getWritableDatabase().delete("pantry","id=?",new String[]{String.valueOf(id)});}
    public List<Recipe> suggestions() {
        Set<String> have=new HashSet<>(); for(PantryItem p:items()) if(p.qty>0) have.add(normalize(p.name));
        List<Recipe> out=new ArrayList<>(); Cursor c=getReadableDatabase().rawQuery("SELECT id,name,ingredients,steps FROM recipes ORDER BY name",null);
        while(c.moveToNext()){
            String[] need=c.getString(2).split(","); boolean ok=true;
            for(String x:need) if(!have.contains(normalize(x.trim()))) {ok=false;break;}
            if(ok) out.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3)));
        } c.close(); return out;
    }
    public List<Recipe> recipes(){ List<Recipe> out=new ArrayList<>(); Cursor c=getReadableDatabase().rawQuery("SELECT id,name,ingredients,steps FROM recipes ORDER BY name",null); while(c.moveToNext())out.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3))); c.close(); return out; }
    public static class PantryItem { public long id; public String name,unit,expiry; public double qty; PantryItem(long i,String n,double q,String u,String e){id=i;name=n;qty=q;unit=u;expiry=e;} }
    public static class Recipe { public long id; public String name,ingredients,steps; Recipe(long i,String n,String ing,String s){id=i;name=n;ingredients=ing;steps=s;} }
}
