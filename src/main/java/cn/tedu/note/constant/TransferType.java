package cn.tedu.note.constant;

/**
 * 上下转移线路枚举类型
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/16 14:51
 */
public enum TransferType {

    /**
     * 上转移
     */
    UP_TRANFER(0,"上转移"),

    /**
     * 下转移
     */
    DOWN_TRANSFER(1,"下转移");

    private Integer typeValue;

    private String typeMsg;

    private TransferType(Integer typeValue, String typeMsg) {
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
     * 静态方法1(分支法)-根据code返回类型
     * @param code
     * @return
     */
    public static TransferType getTransferTypeByCode(Integer code){
        switch (code){
            case 0:
                return UP_TRANFER;
            case 1:
                return DOWN_TRANSFER;
            default:
                return UP_TRANFER;
        }
    }

    /**
     * 静态方法2(遍历法)-根据code返回类型
     * @param code
     * @return
     */
    public static TransferType getTransferType(Integer code){
        for(TransferType type : TransferType.values()){
            if(type.getcTypeValue() == code){
                return type;
            }
        }
        return null;
    }
}
