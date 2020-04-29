package cn.tedu.note.util.pattern.factory.simpleFactory.factoryImpl;

import cn.tedu.note.util.pattern.factory.simpleFactory.IDevice;
import cn.tedu.note.util.pattern.factory.simpleFactory.IFactory;
import cn.tedu.note.util.pattern.factory.simpleFactory.deviceImpl.DellMouse;
import cn.tedu.note.util.pattern.factory.simpleFactory.deviceImpl.HpMouse;
import cn.tedu.note.util.pattern.factory.simpleFactory.deviceImpl.Mouse;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 15:09
 */
public class MouseFactory implements IFactory {

    @Override
    public Mouse createDevice(int factorySeq) {
        switch (factorySeq){
            case 0 : return new HpMouse("HpMouse","Hp-m-12");
            case 1 : return new DellMouse("DellMouse","Dell-x-34");
            default: return Mouse.getInstance();
        }
    }
}
