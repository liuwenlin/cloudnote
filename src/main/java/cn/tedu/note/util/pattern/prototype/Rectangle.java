package cn.tedu.note.util.pattern.prototype;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 22:01
 */
public class Rectangle extends Shape {
    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
