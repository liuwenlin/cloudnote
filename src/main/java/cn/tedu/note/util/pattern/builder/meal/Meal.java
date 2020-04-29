package cn.tedu.note.util.pattern.builder.meal;

import cn.tedu.note.util.pattern.builder.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:31
 */
public class Meal {

    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item){
        items.add(item);
    }

    public float getCost(){
        float cost = 0.0f;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems(){
        for (Item item : items) {
            System.out.print("Item : "+item.name());
            System.out.print(", Packing : "+item.packing().pack());
            System.out.println(", Price : "+item.price());
        }
    }

}
