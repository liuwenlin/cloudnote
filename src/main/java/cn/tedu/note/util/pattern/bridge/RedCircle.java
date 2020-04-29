package cn.tedu.note.util.pattern.bridge;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/3 16:36
 */
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: red, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}