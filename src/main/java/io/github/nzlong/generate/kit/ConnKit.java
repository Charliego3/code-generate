package io.github.nzlong.generate.kit;

import io.github.nzlong.generate.cache.ConnectionCacheManager;
import io.github.nzlong.generate.entity.ConnectionReqVO;
import io.github.nzlong.generate.entity.BaseRespVO;
import io.github.nzlong.generate.exception.GeneraterException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午5:27
 */
public class ConnKit {

    /**
     * 数据库连接
     */
    private static Connection connection;

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection(ConnectionReqVO connectionReqVO) throws SQLException, ClassNotFoundException {
        connection = ConnectionCacheManager.getInstance().getConn(connectionReqVO);
        if (connection == null) {
            testConnection(connectionReqVO);
        }
        return connection;
    }

    /**
     * 测试连接
     *
     * @param connectionVO
     * @return
     */
    public static BaseRespVO testConnection(ConnectionReqVO connectionVO) throws ClassNotFoundException, SQLException {
        if (connectionVO == null) {
            throw new GeneraterException(SysCode.CONNECT_ERROR);
        }
        BaseRespVO connectionRespVo = new BaseRespVO();
        connectionRespVo.setSuccess(false);
        connection = ConnectionCacheManager.getInstance().getConn(connectionVO);
        if (connection == null) {
            Class.forName(Const.DRIVER_CLASS_NAME);
            String url = "jdbc:mysql://" + connectionVO.getHost() + ":" + connectionVO.getPort();
            connection = DriverManager.getConnection(url, connectionVO.getUserName(), connectionVO.getpassword());
        }
        if (connection != null) {
            ConnectionCacheManager.getInstance().put(connectionVO, connection);
            connectionRespVo.setSuccess(true).setReason("Successful!");
        }
        return connectionRespVo;
    }

}
