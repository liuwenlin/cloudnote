package cn.tedu.note.util.pattern.factory.normalFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.DellMouse;
import cn.tedu.note.util.pattern.factory.normalFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/28 16:16
 */
public class DellMouseFactory extends MouseFactory {
    @Override
    public Mouse createDevice() {
        return new DellMouse("DellMouse","Dell-x-34");
    }
}
