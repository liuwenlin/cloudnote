package cn.tedu.note.util.pattern.factory.normalFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.normalFactory.IDevice;
import cn.tedu.note.util.pattern.factory.normalFactory.IFactory;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.DellMouse;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.HpMouse;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 15:09
 */
public abstract class MouseFactory {


    public abstract Mouse createDevice();

}
