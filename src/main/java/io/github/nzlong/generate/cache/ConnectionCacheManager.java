package io.github.nzlong.generate.cache;

import io.github.nzlong.generate.entity.ConnectionReqVO;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 01 下午6:50
 */
public class ConnectionCacheManager {

    private static ConnectionCacheManager connectionCacheManager;

    private static ConcurrentHashMap<String, Connection> cacheMap;

    static {
        connectionCacheManager = new ConnectionCacheManager();
        cacheMap = new ConcurrentHashMap();
    }

    public static ConnectionCacheManager getInstance() {
        return connectionCacheManager;
    }

    public void put(ConnectionReqVO connectionReqVO, Connection connection) {
        cacheMap.put(generateKey(connectionReqVO), connection);
    }

    public Connection getConn(ConnectionReqVO connectionReqVO) {
        return cacheMap.get(generateKey(connectionReqVO));
    }

    public void remove(ConnectionReqVO connectionReqVO) {
        cacheMap.remove(generateKey(connectionReqVO));
    }

    private String generateKey(ConnectionReqVO connectionReqVO) {
        return connectionReqVO.getHost() + "_" + connectionReqVO.getPort() + "_" + connectionReqVO.getSchema();
    }

}
