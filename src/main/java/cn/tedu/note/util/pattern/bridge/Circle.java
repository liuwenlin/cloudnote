package cn.tedu.note.util.pattern.bridge;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/3 16:37
 */
public class Circle extends Shape {
    private int x, y, radius;

    public Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw() {
        drawAPI.drawCircle(radius,x,y);
    }
}