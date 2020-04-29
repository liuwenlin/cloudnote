package cn.tedu.note.util.pattern.factory.simpleFactory.deviceImpl;

import cn.tedu.note.util.pattern.factory.simpleFactory.IDevice;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 15:04
 */
public abstract class AbstractDevice implements IDevice {

    //名称
    String name;

    //类型
    String type;

    public AbstractDevice(){}

    public AbstractDevice(String name,String type){
        this.name = name;
        this.type = type;
    }

}
