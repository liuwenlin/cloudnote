package cn.tedu.note.util.pattern.factory.abstractFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Keybo;
import cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 18:22
 */
public abstract class PcFactory {

    public abstract Mouse createMouse();

    public abstract Keybo createKeybo();

}
