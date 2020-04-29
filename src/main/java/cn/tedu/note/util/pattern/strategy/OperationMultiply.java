package cn.tedu.note.util.pattern.strategy;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/16 16:41
 */
public class OperationMultiply implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
