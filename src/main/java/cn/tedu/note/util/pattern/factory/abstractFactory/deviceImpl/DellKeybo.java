package cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl;

import cn.tedu.note.util.pattern.factory.abstractFactory.KeyboFunction;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 21:30
 */
public class DellKeybo extends Keybo implements KeyboFunction {

    public DellKeybo(){}

    public DellKeybo(String name, String type){
        super(name,type);
    }

    @Override
    public void keyInput() {
        System.out.println("Now you are typing words in using Dell keybo.");
    }

    @Override
    public String sayHi() {
        return "This is a Dell keybo product.";
    }
}
