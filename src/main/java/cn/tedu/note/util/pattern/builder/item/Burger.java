package cn.tedu.note.util.pattern.builder.item;

import cn.tedu.note.util.pattern.builder.packing.Packing;
import cn.tedu.note.util.pattern.builder.packing.Wrapper;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:22
 */
public abstract class Burger implements Item{

    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
