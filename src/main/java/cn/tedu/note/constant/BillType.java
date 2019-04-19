package cn.tedu.note.constant;

/**
 * 定义单车工作时长提送货单号的类型
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/13 14:57
 */
public enum BillType {

    /**
     * 送货单类型
     */
    DELIVER_GOODS(1,"送货单"),

    /**
     * 提货单类型
     */
    PICKUP_GOODS(0,"提货单");

    private Integer typeValue;

    private String typeMsg;

    private BillType(Integer typeValue, String typeMsg) {
        this.typeValue = typeValue;
        this.typeMsg = typeMsg;
    }

    public Integer getcTypeValue() {
        return typeValue;
    }

    public String getTypeMsg(){
        return typeMsg;
    }

    /**
     * 静态方法1(分支法)-根据code返回单据类型
     * @param code
     * @return
     */
    public static BillType getBillTypeByCode(Integer code){
        switch (code){
            case 0:
                return PICKUP_GOODS;
            case 1:
                return DELIVER_GOODS;
            default:
                return DELIVER_GOODS;
        }
    }

    /**
     * 静态方法2(遍历法)-根据code返回单据类型
     * @param code
     * @return
     */
    public static BillType getBillType(Integer code){
        for(BillType type : BillType.values()){
            if(type.getcTypeValue() == code){
                return type;
            }
        }
        return null;
    }
}
