package cn.tedu.note.util.pattern.factory.normalFactory.runEnv;

import cn.tedu.note.util.pattern.factory.normalFactory.IFactory;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.HpMouse;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.Mouse;
import cn.tedu.note.util.pattern.factory.normalFactory.factoryImpl.DellMouseFactory;
import cn.tedu.note.util.pattern.factory.normalFactory.factoryImpl.HpMouseFactory;
import cn.tedu.note.util.pattern.factory.normalFactory.factoryImpl.MouseFactory;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 16:44
 */
public class FactoryRun {

    public static void main(String[] args) {
        MouseFactory hpFactory = new HpMouseFactory();
        Mouse hpMouse = hpFactory.createDevice();
        System.out.println("Build result: " + hpMouse.sayHi());
        hpMouse.pointerMove();
        MouseFactory dellFactory = new DellMouseFactory();
        Mouse dellMouse = dellFactory.createDevice();
        System.out.println("Build result: " + dellMouse.sayHi());
        dellMouse.pointerMove();
    }
}
