package cn.tedu.note.util.pattern.builder.item;

import cn.tedu.note.util.pattern.builder.packing.Packing;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:20
 */
public interface Item {

    String name();

    Packing packing();

    float price();

}
