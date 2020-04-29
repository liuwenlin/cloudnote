package cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl;

import cn.tedu.note.util.pattern.factory.normalFactory.MouseFunction;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 15:15
 */
public abstract class Mouse extends AbstractDevice implements MouseFunction {

    String leftKey;

    String rightKey;

    String mouseWheel;

    public Mouse(){}

    public Mouse(String name, String type){
        super(name,type);
    }

    public void pointerMove(){
        System.out.println("The anonymous mouse is moving now!");
    }

    public String sayHi(){
        return "This is just a anonymous mouse!";
    }

    public static Mouse getInstance(){
        return new Mouse(){

        };
    }
}
