package cn.tedu.note.util.pattern.factory.abstractFactory.runEnv;

import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Mouse;
import cn.tedu.note.util.pattern.factory.abstractFactory.factoryImpl.DellMouseFactory;
import cn.tedu.note.util.pattern.factory.abstractFactory.factoryImpl.HpMouseFactory;
import cn.tedu.note.util.pattern.factory.abstractFactory.factoryImpl.PcFactory;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 16:44
 */
public class FactoryRun {

    public static void main(String[] args) {
        PcFactory hpFactory = new HpMouseFactory();

        hpFactory.createKeybo().sayHi();

        hpFactory.createMouse().sayHi();

        hpFactory.createKeybo().keyInput();

        hpFactory.createMouse().pointerMove();

        PcFactory dellFactory = new DellMouseFactory();

        dellFactory.createKeybo().sayHi();

        dellFactory.createMouse().sayHi();

        dellFactory.createKeybo().keyInput();

        dellFactory.createMouse().pointerMove();
    }
}
