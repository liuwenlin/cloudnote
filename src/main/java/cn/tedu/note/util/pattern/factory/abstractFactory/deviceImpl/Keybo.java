package cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl;

import cn.tedu.note.util.pattern.factory.abstractFactory.IDevice;
import cn.tedu.note.util.pattern.factory.abstractFactory.KeyboFunction;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 17:41
 */
public abstract class Keybo extends AbstractDevice implements KeyboFunction {

    String key;

    public Keybo(){

    }

    public Keybo(String name, String type){
        super(name,type);
    }

    public String sayHi(){
        return "This is just a anonymous keybo!";
    }

    public void keyInput(){
        System.out.println("The anonymous keybo is inputting now!");
    }

    public static Keybo getInstance(){
        return new Keybo() {

        };
    }

}
