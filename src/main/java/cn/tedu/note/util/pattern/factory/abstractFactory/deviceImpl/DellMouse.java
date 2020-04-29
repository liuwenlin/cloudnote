package cn.tedu.note.util.pattern.factory.abstractFactory.deviceImpl;

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
        return "This is Dell mouse.";
    }

    @Override
    public void pointerMove() {
        System.out.println("Hp mouse is moving now!");
    }
}
