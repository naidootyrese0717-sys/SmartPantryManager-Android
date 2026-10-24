package za.co.smartpantry;

import android.app.*;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import android.text.InputType;
import java.util.*;

public class MainActivity extends Activity {
    DatabaseHelper db;
    LinearLayout root, body, nav;
    final int teal=Color.rgb(8,127,140), navy=Color.rgb(18,48,71), mint=Color.rgb(229,246,242), bg=Color.rgb(246,248,250), ink=Color.rgb(31,41,55), muted=Color.rgb(100,116,139), orange=Color.rgb(245,158,11);
    String screen="pantry";
    @Override public void onCreate(Bundle b){super.onCreate(b); db=new DatabaseHelper(this); show("pantry");}
    int dp(float x){return (int)(x*getResources().getDisplayMetrics().density+.5f);}
    TextView text(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);if(bold)t.setTypeface(null,Typeface.BOLD);return t;}
    GradientDrawable shape(int color,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(dp(radius));return d;}
    LinearLayout vertical(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);return l;}
    LinearLayout horizontal(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.HORIZONTAL);l.setGravity(Gravity.CENTER_VERTICAL);return l;}
    void pad(View v,int a){v.setPadding(dp(a),dp(a),dp(a),dp(a));}
    TextView button(String label,int color){TextView b=text(label,14,Color.WHITE,true);b.setGravity(Gravity.CENTER);b.setPadding(dp(15),dp(12),dp(15),dp(12));b.setBackground(shape(color,14));return b;}
    void show(String which){screen=which;
        root=vertical();root.setBackgroundColor(bg);setContentView(root);
        LinearLayout header=vertical();header.setBackgroundColor(navy);header.setPadding(dp(20),dp(18),dp(20),dp(20));
        TextView brand=text("🥬  SMART PANTRY",13,Color.rgb(155,230,216),true);header.addView(brand);
        TextView title=text(which.equals("pantry")?"Your pantry":which.equals("suggestions")?"Recipes you can make":which.equals("settings")?"Your kitchen profile":which.equals("form")?"Ingredient details":which.equals("catalog")?"Ingredient library":"Recipe details",26,Color.WHITE,true);
        LinearLayout.LayoutParams tp=new LinearLayout.LayoutParams(-1,-2);tp.topMargin=dp(9);header.addView(title,tp);
        TextView sub=text(which.equals("pantry")?"Keep track. Cook smarter. Waste less.":which.equals("suggestions")?"Only recipes with every ingredient in stock.":which.equals("settings")?"Simple, local and private.":which.equals("form")?"Add or update what you have.":"Everything you need, no extra shopping.",13,Color.rgb(211,226,236),false);
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,-2);sp.topMargin=dp(5);header.addView(sub,sp);root.addView(header);
        ScrollView scroll=new ScrollView(this);body=vertical();body.setPadding(dp(16),dp(16),dp(16),dp(20));scroll.addView(body);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        nav=horizontal();nav.setBackgroundColor(Color.WHITE);nav.setPadding(dp(7),dp(8),dp(7),dp(8));
        addNav("Pantry","pantry");addNav("Recipes","suggestions");addNav("Settings","settings");root.addView(nav);
        if(which.equals("pantry")) pantry(); else if(which.equals("suggestions")) suggestions(); else if(which.equals("settings")) settings(); else if(which.equals("form")) form(getIntent().getLongExtra("edit_id",0)); else if(which.equals("catalog")) catalog(); else if(which.equals("detail")) detail(getIntent().getLongExtra("recipe_id",0));
    }
    void addNav(String label,String target){TextView b=text((screen.equals(target)?"● ":"")+label,12,screen.equals(target)?teal:muted,true);b.setGravity(Gravity.CENTER);b.setPadding(dp(8),dp(10),dp(8),dp(10));nav.addView(b,new LinearLayout.LayoutParams(0,-2,1));b.setOnClickListener(v->show(target));}
    void section(String s){TextView t=text(s,18,navy,true);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(10);body.addView(t,p);}
    void card(View v){v.setBackground(shape(Color.WHITE,18));v.setElevation(dp(2));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(10);body.addView(v,p);}
    void pantry(){
        List<DatabaseHelper.PantryItem> items=db.items();
        LinearLayout summary=horizontal();summary.setBackground(shape(mint,18));summary.setPadding(dp(16),dp(16),dp(16),dp(16));
        LinearLayout left=vertical();left.addView(text(""+items.size(),28,navy,true));left.addView(text("ingredients in your pantry",13,muted,false));summary.addView(left,new LinearLayout.LayoutParams(0,-2,1));
        TextView add=button("+ Choose items",teal);summary.addView(add);add.setOnClickListener(v->show("catalog"));card(summary);
        TextView manual= text("+ Add a custom ingredient",14,teal,true); manual.setPadding(dp(2),dp(5),dp(2),dp(12)); body.addView(manual); manual.setOnClickListener(v->openForm(0));
        section("Your ingredients");
        if(items.isEmpty()){empty("Your pantry is ready for a fresh start.","Add ingredients you already have at home to unlock recipes.");}
        for(DatabaseHelper.PantryItem p:items){
            LinearLayout row=horizontal();row.setPadding(dp(14),dp(12),dp(10),dp(12));
            LinearLayout details=vertical();details.addView(text(cap(p.name),16,ink,true));details.addView(text(fmt(p.qty)+" "+p.unit+(p.expiry.isEmpty()?"":"  ·  Best before "+p.expiry),12,muted,false));row.addView(details,new LinearLayout.LayoutParams(0,-2,1));
            TextView edit=button("Edit",teal);row.addView(edit);LinearLayout.LayoutParams ep=(LinearLayout.LayoutParams)edit.getLayoutParams();ep.rightMargin=dp(6);edit.setLayoutParams(ep);edit.setOnClickListener(v->openForm(p.id));
            TextView del=button("×",Color.rgb(220,38,38));row.addView(del);del.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Delete ingredient?").setMessage("Remove "+p.name+" from your pantry?").setNegativeButton("Cancel",null).setPositiveButton("Delete",(d,w)->{db.delete(p.id);show("pantry");}).show());
            card(row);
        }
        TextView go=button("Find recipes from my pantry  →",navy);LinearLayout.LayoutParams gp=new LinearLayout.LayoutParams(-1,-2);gp.topMargin=dp(6);body.addView(go,gp);go.setOnClickListener(v->show("suggestions"));
    }
    void catalog(){
        section("Choose ingredients");
        TextView intro=text("Select everything you currently have. Selected items will be added to your pantry with a starter quantity that you can edit later.",14,muted,false); LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(-1,-2);ip.bottomMargin=dp(12);body.addView(intro,ip);
        EditText search=input("Search 90+ ingredients...",false); fieldCard("Find ingredients",search);
        LinearLayout list=vertical(); body.addView(list,new LinearLayout.LayoutParams(-1,-2));
        List<DatabaseHelper.CatalogItem> all=db.catalog(); List<CheckBox> checks=new ArrayList<>(); List<DatabaseHelper.CatalogItem> shown=new ArrayList<>();
        String last="";
        for(DatabaseHelper.CatalogItem item:all){
            if(!item.category.equals(last)){ TextView heading=text(item.category,16,navy,true); LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,-2);hp.topMargin=dp(12);hp.bottomMargin=dp(5);list.addView(heading,hp);last=item.category; }
            CheckBox cb=new CheckBox(this);cb.setText(item.name+"  ·  "+item.unit);cb.setTextColor(ink);cb.setTextSize(14);cb.setTag(item);cb.setPadding(dp(8),dp(4),dp(8),dp(4));
            list.addView(cb);checks.add(cb);shown.add(item);
        }
        search.addTextChangedListener(new android.text.TextWatcher(){public void beforeTextChanged(CharSequence s,int st,int count,int after){} public void onTextChanged(CharSequence s,int st,int before,int count){String q=s.toString().trim().toLowerCase(Locale.ROOT);for(CheckBox cb:checks){DatabaseHelper.CatalogItem item=(DatabaseHelper.CatalogItem)cb.getTag();cb.setVisibility(item.name.toLowerCase(Locale.ROOT).contains(q)||item.category.toLowerCase(Locale.ROOT).contains(q)?View.VISIBLE:View.GONE);}} public void afterTextChanged(android.text.Editable e){}});
        TextView add=button("Add selected ingredients to pantry",teal);LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(-1,-2);bp.topMargin=dp(16);body.addView(add,bp);
        add.setOnClickListener(v->{int count=0;for(CheckBox cb:checks)if(cb.isChecked()){DatabaseHelper.CatalogItem item=(DatabaseHelper.CatalogItem)cb.getTag();boolean exists=false;for(DatabaseHelper.PantryItem old:db.items())if(DatabaseHelper.normalize(old.name).equals(DatabaseHelper.normalize(item.name))){exists=true;break;}if(!exists){db.saveItem(0,item.name,1,item.unit,"");count++;}}if(count==0)toast("Select new ingredients first. Items already in your pantry are skipped.");else{toast(count+" ingredient(s) added");show("pantry");}});
        TextView back=button("Back to pantry",navy);LinearLayout.LayoutParams xp=new LinearLayout.LayoutParams(-1,-2);xp.topMargin=dp(8);body.addView(back,xp);back.setOnClickListener(v->show("pantry"));
    }

    void suggestions(){
        List<DatabaseHelper.Recipe> rs=db.suggestions();
        LinearLayout banner=vertical();banner.setPadding(dp(16),dp(16),dp(16),dp(16));banner.setBackground(shape(mint,18));
        banner.addView(text("Strict match is ON ✓",16,teal,true));banner.addView(text("Every suggested recipe uses ingredients currently in your pantry. Partial matches are never shown.",13,ink,false));card(banner);
        section("Ready to cook · "+rs.size());
        if(rs.isEmpty())empty("No recipes match your pantry yet","Add more ingredients such as egg, tomato, pasta or potato. Recipes appear only when every required ingredient is available.");
        for(DatabaseHelper.Recipe r:rs){LinearLayout c=vertical();c.setPadding(dp(16),dp(14),dp(16),dp(14));c.addView(text("🍽  "+r.name,17,navy,true));c.addView(text(r.ingredients.replace(", ",", ").replace(",", "  ·  "),12,muted,false));TextView open=button("View recipe  →",teal);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(12);c.addView(open,p);open.setOnClickListener(v->openRecipe(r.id));card(c);}
        TextView all=text("Browse all recipes",14,teal,true);all.setPadding(0,dp(10),0,dp(10));body.addView(all);all.setOnClickListener(v->{List<DatabaseHelper.Recipe> allRecipes=db.recipes();String[] names=new String[allRecipes.size()];for(int i=0;i<names.length;i++)names[i]=allRecipes.get(i).name;new AlertDialog.Builder(this).setTitle("Recipe collection").setItems(names,(d,w)->openRecipe(allRecipes.get(w).id)).show();});
    }
    void settings(){section("App preferences");LinearLayout c=vertical();c.setPadding(dp(16),dp(16),dp(16),dp(16));c.addView(text("Your data stays on this device",17,navy,true));c.addView(text("Pantry items are saved in a local SQLite database and remain available after you close the app.",14,muted,false));card(c);
        LinearLayout about=vertical();about.setPadding(dp(16),dp(16),dp(16),dp(16));about.addView(text("Built for less food waste",17,navy,true));about.addView(text("• 20 seeded recipes\n• Strict ingredient matching\n• Add, edit and delete pantry items\n• Optional best-before date\n• No GPS, maps or payment features",14,muted,false));card(about);
        TextView reset=button("Clear my pantry",Color.rgb(220,38,38));body.addView(reset);reset.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("Clear pantry?").setMessage("This deletes all pantry ingredients but keeps the recipe collection.").setNegativeButton("Cancel",null).setPositiveButton("Clear",(d,w)->{for(DatabaseHelper.PantryItem p:db.items())db.delete(p.id);show("settings");}).show());
    }
    void empty(String title,String desc){LinearLayout c=vertical();c.setPadding(dp(18),dp(22),dp(18),dp(22));c.setGravity(Gravity.CENTER);c.addView(text("🥣",34,teal,true));TextView t=text(title,17,navy,true);t.setGravity(Gravity.CENTER);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(8);c.addView(t,p);TextView d=text(desc,13,muted,false);d.setGravity(Gravity.CENTER);p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(7);c.addView(d,p);card(c);}
    void form(long id){
        DatabaseHelper.PantryItem old=id>0?db.item(id):null;
        section(id>0?"Edit ingredient":"New ingredient");
        EditText name=input("Ingredient name (e.g. tomato)",false);EditText qty=input("Quantity (e.g. 3)",true);EditText unit=input("Unit (e.g. pieces, g, cups)",false);EditText expiry=input("Best-before date (optional, e.g. 2026-11-30)",false);
        if(old!=null){name.setText(old.name);qty.setText(fmt(old.qty));unit.setText(old.unit);expiry.setText(old.expiry);}
        fieldCard("Ingredient name",name);fieldCard("Quantity",qty);fieldCard("Unit",unit);fieldCard("Best-before date",expiry);
        TextView save=button("Save ingredient",teal);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(8);body.addView(save,p);
        save.setOnClickListener(v->{String n=name.getText().toString().trim(),q=qty.getText().toString().trim(),u=unit.getText().toString().trim(),e=expiry.getText().toString().trim();if(n.isEmpty()||q.isEmpty()||u.isEmpty()){toast("Please enter a name, quantity and unit.");return;}double amount;try{amount=Double.parseDouble(q);if(amount<=0)throw new Exception();}catch(Exception ex){toast("Quantity must be a number greater than zero.");return;}if(n.contains(",")||n.length()>60){toast("Use a single ingredient name (maximum 60 characters).");return;}db.saveItem(id,n,amount,u,e);toast("Ingredient saved");show("pantry");});
        if(id>0){TextView cancel=button("Cancel",navy);LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2);cp.topMargin=dp(8);body.addView(cancel,cp);cancel.setOnClickListener(v->show("pantry"));}
    }
    EditText input(String hint,boolean number){EditText e=new EditText(this);e.setSingleLine(true);e.setTextSize(15);e.setHint(hint);e.setTextColor(ink);e.setHintTextColor(muted);e.setInputType(number?InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL:InputType.TYPE_CLASS_TEXT);return e;}
    void fieldCard(String label,EditText e){LinearLayout c=vertical();c.setPadding(dp(12),dp(8),dp(12),dp(4));c.addView(text(label,12,muted,true));c.addView(e);card(c);}
    void detail(long id){DatabaseHelper.Recipe r=null;for(DatabaseHelper.Recipe x:db.recipes())if(x.id==id)r=x;if(r==null){empty("Recipe unavailable","Return to the recipe list and choose another recipe.");return;}
        LinearLayout c=vertical();c.setPadding(dp(18),dp(18),dp(18),dp(18));c.addView(text("INGREDIENTS",12,teal,true));for(String s:r.ingredients.split(",")){TextView t=text("✓  "+cap(s.trim()),15,ink,false);t.setPadding(0,dp(7),0,dp(7));c.addView(t);}c.addView(text("METHOD",12,teal,true));TextView steps=text(r.steps,15,ink,false);steps.setPadding(0,dp(10),0,0);c.addView(steps);card(c);
        TextView back=button("← Back to recipes",navy);body.addView(back);back.setOnClickListener(v->show("suggestions"));
    }
    void openForm(long id){Intent i=new Intent(this,MainActivity.class);i.putExtra("edit_id",id);i.putExtra("screen","form");startActivity(i);}
    void openRecipe(long id){Intent i=new Intent(this,MainActivity.class);i.putExtra("recipe_id",id);i.putExtra("screen","detail");startActivity(i);}
    @Override protected void onResume(){super.onResume();if(getIntent()!=null&&getIntent().hasExtra("screen")){String s=getIntent().getStringExtra("screen");getIntent().removeExtra("screen");show(s);}}
    String cap(String s){if(s==null||s.isEmpty())return s;return s.substring(0,1).toUpperCase(Locale.ROOT)+s.substring(1);}
    String fmt(double x){return x==(long)x?String.valueOf((long)x):String.valueOf(x);}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
