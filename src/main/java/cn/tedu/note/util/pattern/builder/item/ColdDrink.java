package cn.tedu.note.util.pattern.builder.item;

import cn.tedu.note.util.pattern.builder.packing.Bottle;
import cn.tedu.note.util.pattern.builder.packing.Packing;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:25
 */
public abstract class ColdDrink implements Item{

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
