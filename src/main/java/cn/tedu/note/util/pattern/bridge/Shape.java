package cn.tedu.note.util.pattern.bridge;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/3 16:36
 */
public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }
    public abstract void draw();
}
