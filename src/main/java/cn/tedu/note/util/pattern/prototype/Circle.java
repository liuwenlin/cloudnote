package cn.tedu.note.util.pattern.prototype;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 22:02
 */
public class Circle extends Shape {

    public Circle(){
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
