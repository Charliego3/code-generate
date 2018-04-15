package io.github.nzlong.generate.kit;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午2:31
 */
public interface Const {

    /**
     * 主线程名称
     */
    String PROJECT_THREAD_NAME = "generate-main";

    /**
     * MySQL数据库连接驱动
     */
    String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    /**
     * 数据库连接地址名称
     */
    String JDBC_URL = "jdbc.url";

    /**
     * 数据库用户名
     */
    String JDBC_USER_NAME = "jdbc.username";

    /**
     * 数据库密码
     */
    String JDBC_PASSWARD = "jdbc.password";

    /**
     * 成功返回码
     */
    int SUCCESS_CODE = 1;

    /**
     * 默认开始页
     */
    int DEFAULT_PAGE_INDEX = 0;

    /**
     * 每页默认大小
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 类注释
     */
    String CLASS_ANNOTATION = "\n/**\n" +
                              " * @author: %s\n" +
                              " * @osName: %s\n" +
                              " * @description: %s\n" +
                              " * @encoding: %s\n" +
                              " * @date: Create in %s\n" +
                              " */\n";

    /**
     * public class xxx
     */
    String PUBLIC_CLASS = "public class %s {\n\n";

    String IMPORT_BIGDECIMAL = "import java.math.BigDecimal;\n";

    String FIELD_PRIVATE = "\tprivate %s %s;\n\n";

    String FIELD_ANNOTATION = "\t/**\n" +
                              "\t  * %s\n" +
                              "\t  */\n";

    String STRUCUTRE = "\tpublic %s";

    String STRUCUTRE_ANNOTATION = "\t/**\n" +
                                        "\t  * %s\n" +
                                        "\t  */\n";

    String EMPTY_STRUCUTRE = "空参构造";

    String ALL_STRUCUTRE = "全参构造";

    String CLASS_END = "}";

    String FILE_TYPE = ".java";

    String IMPORT_DATE = "import java.util.Date;\n";

    String IMPORT_TIMESTAMP = "import java.sql.Timestamp;\n";


}
