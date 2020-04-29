package cn.tedu.note.util.pattern.factory.normalFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.normalFactory.IDevice;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.HpMouse;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 16:13
 */
public class HpMouseFactory extends MouseFactory {

    @Override
    public Mouse createDevice() {
        return new HpMouse("HpMouse","Hp-m-12");
    }

}
