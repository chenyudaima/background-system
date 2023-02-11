package com.chenyudaima.enumeration;

/**
 * opc节点数据类型
 * @author 沉鱼代码
 * @date 2022/11/23
 */
public enum DataTypeEnum {
    String(0),
    Boolean(1),
    Char(2),
    Byte(3),
    Short(4),
    Word(5),
    Long(6),
    DWord(7),
    Float(8),
    Double(9),
    BCD(10),
    LBCD(11),
    Date(12),
    LLong(13),
    QWord(14),
    StringArray(15),
    BooleanArray(16),
    CharArray(17),
    ByteArray(18),
    ShortArray(19),
    WordArray(20),
    LongArray(21),
    DWordArray(22),
    FloatArray(23),
    DoubleArray(24),
    BCDArray(25),
    LBCDArray(26),
    DateArray(27),
    LLongArray(28),
    QWordArray(29);


    private final int number;

    DataTypeEnum(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static String getName(int number) {
        for (DataTypeEnum value : DataTypeEnum.values()) {
            if(value.getNumber() == number) {
                return value.toString();
            }

        }
        return null;
    }
}
