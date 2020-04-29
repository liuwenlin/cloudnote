package cn.tedu.note.util.pattern.factory.abstractFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.HpKeybo;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.HpMouse;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Keybo;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 16:13
 */
public class HpMouseFactory extends PcFactory{

    @Override
    public Mouse createMouse() {
        return new HpMouse("","");
    }

    @Override
    public Keybo createKeybo() {
        return new HpKeybo("","");
    }
}
