package cn.tedu.note.util.pattern.prototype;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/2 22:01
 */
public class Square extends Shape {

    public Square(){
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}