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

/**
 * 根据IP查询详细的地址位置
 * 试用开源的ip2region库，开源地址：https://github.com/lionsoul2014/ip2region
 *
 * @author tycoding
 * @date 2019-02-15
 */
public class AddressUtil {

    /**
     * 代码参考官方：https://github.com/lionsoul2014/ip2region/blob/master/binding/java/src/main/java/org/lionsoul/ip2region/test/TestSearcher.java
     *
     * @param ip String
     * @return String
     */
    public static String getAddress(String ip,int algorithm) {
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
                default:
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
}
