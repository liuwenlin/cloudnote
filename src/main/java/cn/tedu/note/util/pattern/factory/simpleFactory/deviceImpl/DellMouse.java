package cn.tedu.note.util.pattern.factory.simpleFactory.deviceImpl;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/2/27 16:15
 */
public class DellMouse extends Mouse {

    public DellMouse(String name, String type){
        super(name,type);
    }

    @Override
    public String sayHi() {
        return null;
    }

    @Override
    public void pointerMove() {

    }
}
