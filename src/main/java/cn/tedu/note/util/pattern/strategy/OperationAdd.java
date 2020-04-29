package cn.tedu.note.util.pattern.strategy;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/16 16:40
 */
public class OperationAdd implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
