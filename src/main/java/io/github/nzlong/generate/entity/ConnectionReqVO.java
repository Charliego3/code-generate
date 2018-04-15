package io.github.nzlong.generate.entity;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午7:31
 */
public class ConnectionReqVO {

    private String host;

    private Integer port;

    private String schema;

    private String userName;

    private String password;

    public String getHost() {
        return host;
    }

    public ConnectionReqVO setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public ConnectionReqVO setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public ConnectionReqVO setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ConnectionReqVO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getpassword() {
        return password;
    }

    public ConnectionReqVO setpassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "ConnectionVO{" + "host='" + host + '\'' + ", port=" + port + ", schema='" + schema + '\'' + ", userName='" + userName + '\'' + ", password='" + password + '\'' + '}';
    }

}
