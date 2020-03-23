package com.dimples.core.util;

import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * 根据IP查询详细的地址位置
 * 试用开源的ip2region库，开源地址：https://github.com/lionsoul2014/ip2region
 *
 * @author tycoding
 * @date 2019-02-15
 */
@Slf4j
public class AddressUtil {

    /**
     * 代码参考官方：https://github.com/lionsoul2014/ip2region/blob/master/binding/java/src/main/java/org/lionsoul/ip2region/test/TestSearcher.java
     *
     * @param ip String
     * @return String
     */
    public static String getAddress(String ip, int algorithm) {
        //db
        String dbPath = AddressUtil.class.getResource("/config/ip2region.db").getPath();

        File file = new File(dbPath);

        if (!file.exists()) {
            String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
            dbPath = tmpDir + "ip.db";
            file = new File(dbPath);
            try {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(AddressUtil.class.getClassLoader().getResourceAsStream("classpath:config/ip2region.db")), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //define the method
            Method method;
            switch (algorithm) {
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
            }

            DataBlock dataBlock;
            if (!Util.isIpAddress(ip)) {
                System.out.println("Error: Invalid ip address");
            }
            dataBlock = (DataBlock) Objects.requireNonNull(method).invoke(searcher, ip);
            reader.close();
            searcher.close();
            return dataBlock.getRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressWarnings("all")
    public static String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            String dbPath = AddressUtil.class.getResource("/config/ip2region.db").getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
                dbPath = tmpDir + "ip.db";
                file = new File(dbPath);
                FileUtils.copyInputStreamToFile(AddressUtil.class.getClassLoader()
                        .getResourceAsStream("classpath:config/ip2region.db"), file);
            }
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, dbPath);
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.error("Error: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.error("获取IP地址失败，{}", e.getMessage());
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
