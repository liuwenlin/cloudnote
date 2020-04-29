package cn.tedu.note.util.pattern.builder.item;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 21:28
 */
public class Coke extends ColdDrink {
    @Override
    public String name() {
        return "Coke";
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
