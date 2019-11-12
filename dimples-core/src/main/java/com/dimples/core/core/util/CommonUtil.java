package com.dimples.core.core.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 高频方法集合类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
public class CommonUtil {

    private static final char UNDERLINE = '_';

    private static final String BASE_CHARACTER = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 获取随机位数的字符串
     *
     * @param length int
     * @return String
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_CHARACTER.length());
            sb.append(BASE_CHARACTER.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 判断一个对象是否是时间类型
     *
     * @param o Object
     * @return String
     */
    public static String dateType(Object o) {
        if (o instanceof Date) {
            return DateUtil.getDay((Date) o);
        } else {
            return o.toString();
        }
    }

    /**
     * ids格式化
     *
     * @param ids  String
     * @param flag true 正向 加[] false 反向 去 []
     * @return String
     */
    public static String idsStrFormat(String ids, Boolean flag) {
        if (CommonUtil.isEmpty(ids)) {
            return ids;
        }
        if (flag) {
            String[] id = ids.split(",");
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < id.length; i++) {
                if (i == 0) {
                    res.append("[").append(id[i]).append("]");
                } else {
                    res.append(",[").append(id[i]).append("]");
                }
            }
            return res.toString();
        } else {
            String res = ids.replace("[", "");
            res = res.replace("]", "");
            return res;
        }
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    public static boolean equals(Object obj1, Object obj2) {
        return Objects.equals(obj1, obj2);
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    public static boolean notEquals(Object obj1, Object obj2) {
        return !(Objects.equals(obj1, obj2));
    }

    /**
     * 对象是否不为空(新增)
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return boolean
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 对象是否为空
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return boolean
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return "".equals(o.toString().trim());
        } else if (o instanceof List) {
            return ((List) o).size() == 0;
        } else if (o instanceof Map) {
            return ((Map) o).size() == 0;
        } else if (o instanceof Set) {
            return ((Set) o).size() == 0;
        } else if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        } else if (o instanceof int[]) {
            return ((int[]) o).length == 0;
        } else if (o instanceof long[]) {
            return ((long[]) o).length == 0;
        }
        return false;
    }

    /**
     * 对象组中是否存在 Empty Object
     *
     * @param os 对象组
     * @return boolean
     */
    public static boolean isOneEmpty(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是 Empty Object
     *
     * @param os Object...
     * @return boolean
     */
    public static boolean isAllEmpty(Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str Object
     * @return String
     */
    public static String toStr(Object str) {
        return toStr(str, "");
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str          Object
     * @param defaultValue String
     * @return String
     */
    public static String toStr(Object str, String defaultValue) {
        if (null == str) {
            return defaultValue;
        }
        return str.toString().trim();
    }

    /**
     * 是否为数字
     *
     * @param obj Object
     * @return boolean
     */
    public static boolean isNum(Object obj) {
        try {
            Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 对象中是否包含元素
     *
     * @param obj     对象
     * @param element 元素
     * @return 是否包含
     */
    public static boolean contains(Object obj, Object element) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (element == null) {
                return false;
            }
            return ((String) obj).contains(element.toString());
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).contains(element);
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).values().contains(element);
        }

        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(obj, i);
                if (equals(o, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 当前时间
     *
     * @return String
     */
    public static String currentTime() {
        return DateUtil.now();
    }

    /**
     * 驼峰转下划线
     *
     * @param param String
     * @return String
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param param String
     * @return String
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 大写转为小写方法下划线转驼峰
     *
     * @param orgMap Map<String, Object>
     * @return Map<String, Object>
     */
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();

        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            resultMap.put(underlineToCamel(key.toLowerCase()), orgMap.get(key));
        }
        return resultMap;
    }

    /**
     * 判断是否是windows操作系统
     *
     * @return Boolean
     */
    public static Boolean isWinOs() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }

    /**
     * 获取uuid
     *
     * @return String
     */
    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");

    }

    /**
     * 判断一个字符是否是中文
     *
     * @param c char
     * @return boolean
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str String
     * @return boolean
     */
    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取临时目录
     *
     * @return String
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 从html中提取纯文本
     *
     * @param strHtml String
     * @return String
     */
    public static String html2Text(String strHtml) {
        // 剔出<html>的标签
        String txtcontent = strHtml.replaceAll("</?[^>]+>", "");
        // 去除字符串中的空格,回车,换行符,制表符
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
        return txtcontent;
    }
}