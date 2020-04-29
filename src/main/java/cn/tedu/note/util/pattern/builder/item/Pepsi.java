package cn.tedu.note.util.pattern.builder.item;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:31
 */
public class Pepsi extends ColdDrink {
    @Override
    public String name() {
        return "Pepsi";
    }

    @Override
    public float price() {
        return 35.0f;
    }
}
