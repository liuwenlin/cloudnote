package cn.tedu.note.util.pattern.strategy;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/16 16:41
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}
