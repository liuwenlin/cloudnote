package cn.tedu.note.util.pattern.builder.reception;

import cn.tedu.note.util.pattern.builder.item.ChickenBurger;
import cn.tedu.note.util.pattern.builder.item.Coke;
import cn.tedu.note.util.pattern.builder.item.Pepsi;
import cn.tedu.note.util.pattern.builder.item.VegBurger;
import cn.tedu.note.util.pattern.builder.meal.Meal;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:41
 */
public class MealBuilder {
    public Meal prepareVegMeal (){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public Meal prepareNonVegMeal (){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
