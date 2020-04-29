package cn.tedu.note.util.pattern.factory.normalFactory;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 14:56
 */
public interface IFactory {
    IDevice createDevice(int factorySeq);
}
