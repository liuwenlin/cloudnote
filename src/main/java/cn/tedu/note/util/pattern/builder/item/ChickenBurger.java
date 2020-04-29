package cn.tedu.note.util.pattern.builder.item;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:27
 */
public class ChickenBurger extends Burger {
    @Override
    public String name() {
        return "Chicken Burger";
    }

    @Override
    public float price() {
        return 50.5f;
    }
}
