package cn.tedu.note.util.pattern.factory.simpleFactory.runEnv;

import cn.tedu.note.util.pattern.factory.simpleFactory.IDevice;
import cn.tedu.note.util.pattern.factory.simpleFactory.IFactory;
import cn.tedu.note.util.pattern.factory.simpleFactory.deviceImpl.Mouse;
import cn.tedu.note.util.pattern.factory.simpleFactory.factoryImpl.MouseFactory;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 16:44
 */
public class FactoryRun {

    public static void main(String[] args) {
        IFactory factory = new MouseFactory();
        Mouse mouse = (Mouse) factory.createDevice(0);
        System.out.println("Build result: " + mouse.sayHi());
        mouse.pointerMove();
    }
}
