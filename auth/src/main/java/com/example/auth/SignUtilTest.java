package com.example.auth;

import cn.hutool.crypto.digest.MD5;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.*;

/**
 * @datetime 2022-11-02 下午2:45
 * @desc
 * @menu
 */
public class SignUtilTest {

    /**
     * 生成签名sign
     * 加密前：appId=wx123456789&timestamp=1583332804914&key=7214fefff0cf47d7950cb2fc3b5d670a
     * 加密后：E2B30D3A5DA59959FA98236944A7D9CA
     */
    public static String createSign(SortedMap<String, String> params, String key) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es = params.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        //生成
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=").append(key);
        String sign = MD5(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * md5常用工具类
     */
    public static String MD5(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成签名sign
     * 加密前：appId=wx123456789&timestamp=1583332804914&key=7214fefff0cf47d7950cb2fc3b5d670a
     * 加密后：E2B30D3A5DA59959FA98236944A7D9CA
     */

    /**
     * 参与签名的字符串
     * 1. appId=wx123456789
     * 2. timestamp=1583332804914
     * <p>
     * 头信息
     * appId
     * timestamp
     * <p>
     * sign=md5(appId=wx123456789&appSecret=7214fefff0cf47d7950cb2fc3b5d670a&timestamp=1583332804914&url=/org-mp/shop/queryPageShop).toUpperCase()
     *
     * @param args
     */
    public static void main(String[] args) {
        //第二步：用户端发起请求，生成签名后发送请求
        String appSecret = "7214fefff0cf47d7950cb2fc3b5d670a";
        String appId = "wx123456789";
//        long nowTime = new Date().getTime();
        long nowTime = 1583332804914L;

        // 验证签名和时间戳防重放攻击
        //可以在签名验证时加入时间戳，确保请求没有被重放：
        long currentTimestamp = System.currentTimeMillis() / 1000;
        if (Math.abs(currentTimestamp - nowTime) > 300) { // 5分钟有效期
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Timestamp expired");
        }

        //生成签名
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("appId", appId);
        sortedMap.put("timestamp", String.valueOf(nowTime));
        System.out.println("appId:" + appId + " 时间戳:" + nowTime + " 签名：" + SignUtil.createSign(sortedMap, appSecret));
    }


    // 用于生成签名的方法
    public static String generateSign(Map<String, String[]> paramMap, String secretKey) {
        // 按照字典顺序对参数进行排序
        // 通过对key进行排序后得到的顺序就是唯一的
        List<String> keys = new ArrayList<>(paramMap.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String[] values = paramMap.get(key);
            for (String value : values) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        // 去掉最后的 "&"
        sb.deleteCharAt(sb.length() - 1);

        // 将 secretKey 添加到最后
        sb.append(secretKey);

        // 使用 SHA256 或 MD5 生成签名
        return MD5.create().digestHex16(sb.toString());
    }

    // 从请求中获取参数（兼容 GET 和 POST 请求）
    public static Map<String, String[]> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = new HashMap<>();
        // 对于 GET 请求，从 URL 查询字符串中提取参数
        if (request.getMethod().equalsIgnoreCase("GET")) {
            paramMap.putAll(request.getParameterMap());
        }

        // 对于 POST 请求，从请求体（JSON）中提取参数
        else if (request.getMethod().equalsIgnoreCase("POST")) {
            try {
                String body = request.getReader().lines()
                        .reduce("", (accumulator, actual) -> accumulator + actual);
                // 假设请求体为 JSON 格式，解析 JSON 参数
                paramMap.putAll(parseJsonToMap(body));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paramMap;
    }

    // 假设 JSON 请求体需要转换为 Map
    private static Map<String, String[]> parseJsonToMap(String json) {
        Map<String, String[]> map = new HashMap<>();
        // 使用 JSON 解析库（如 Jackson 或 Gson）将请求体转换为 Map
        // 以下为 Jackson 示例
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> jsonMap = objectMapper.readValue(json, Map.class);
            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                map.put(entry.getKey(), new String[]{entry.getValue()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}