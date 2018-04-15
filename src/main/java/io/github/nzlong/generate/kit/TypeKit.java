package io.github.nzlong.generate.kit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 14 下午9:59
 */
public class TypeKit {

    public static Map<String,String> jdbcType2JavaTypeMap = new HashMap<>();

    static{
        jdbcType2JavaTypeMap.put("VARCHAR", "String");
        jdbcType2JavaTypeMap.put("CHAR", "String");
        jdbcType2JavaTypeMap.put("BLOB", "byte[]");
        jdbcType2JavaTypeMap.put("INTEGER", "Long");
        jdbcType2JavaTypeMap.put("TINYINT", "Integer");
        jdbcType2JavaTypeMap.put("MEDIUMINT", "Integer");
        jdbcType2JavaTypeMap.put("BIT", "Boolean");
        jdbcType2JavaTypeMap.put("BIGINT", "Long");
        jdbcType2JavaTypeMap.put("INT", "Integer");
        jdbcType2JavaTypeMap.put("FLOAT", "Float");
        jdbcType2JavaTypeMap.put("DOUBLE", "Double");
        jdbcType2JavaTypeMap.put("SMALLINT", "Integer");
        jdbcType2JavaTypeMap.put("DECIMAL", "BigDecimal");
        jdbcType2JavaTypeMap.put("TEXT", "String");
        jdbcType2JavaTypeMap.put("DATE", "Date");
        jdbcType2JavaTypeMap.put("DATETIME", "Timestamp");
        jdbcType2JavaTypeMap.put("TIMESTAMP", "Timestamp");
    }

    public static String jdbcType2JavaType(String jdbcType){
        return jdbcType2JavaTypeMap.get(jdbcType);
    }

}
