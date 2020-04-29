package cn.tedu.note.util.pattern.bridge;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/3 16:37
 */
public class BridgePatternDemo {
    public static void main(String[] args) {
        Shape redCircle = new Circle(100,100, 10, new RedCircle());
        Shape greenCircle = new Circle(100,100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }
}
