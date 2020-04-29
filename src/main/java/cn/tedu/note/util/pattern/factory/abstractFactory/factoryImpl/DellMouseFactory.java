package cn.tedu.note.util.pattern.factory.abstractFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.DellKeybo;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.DellMouse;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Keybo;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 16:16
 */
public class DellMouseFactory extends PcFactory{
    @Override
    public Mouse createMouse() {
        return new DellMouse("","");
    }

    @Override
    public Keybo createKeybo() {
        return new DellKeybo("","");
    }
}
