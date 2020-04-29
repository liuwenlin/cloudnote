package cn.tedu.note.util.pattern.builder.item;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:26
 */
public class VegBurger extends Burger {
    @Override
    public String name() {
        return "Veg Burger";
    }

    @Override
    public float price() {
        return 25.0f;
    }
}
