package com.k7cl.bjypc.covid.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CovidUtil {
    static MediaType contentType = MediaType.parse("application/json");
    private final static String url = "http://bmfw.www.gov.cn/bjww/interface/interfaceJson";
    private final static String salt1 = "fTN2pfuisxTavbTuYVSsNJHetwq5bJvCQkjjtiLM2dCratiA";
    private final static String salt2 = "23y0ufFl5YxIyGrI8hWRUZmKkvtSjLQA";

    Proxy proxyTest = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1", 8080));

    OkHttpClient client = new OkHttpClient.Builder()
            .followRedirects(false)
            .connectTimeout(15, TimeUnit.SECONDS)
//            .proxy(proxyTest)
            .build();

    public JSONObject getData(){
        String ts = String.valueOf(System.currentTimeMillis()/1000);
        Map<String, String> body = new HashMap<>();
        body.put("key", "3C502C97ABDA40D0A60FBEE50FAAD1DA");
        body.put("appId", "NcApplication");
        body.put("paasHeader", "zdww");
        body.put("timestampHeader", ts);
        body.put("nonceHeader", "123456789abcdefg");
        body.put("signatureHeader", sha256(ts.concat(salt2).concat("123456789abcdefg").concat(ts)));
        RequestBody requestBody = RequestBody.Companion.create(JSON.toJSONString(body), contentType);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-wif-nonce", "QkjjtiLM2dCratiA")
                .addHeader("x-wif-signature", Objects.requireNonNull(sha256(ts.concat(salt1).concat(ts))))
                .addHeader("x-wif-timestamp", ts)
                .addHeader("x-wif-paasid", "smt-application")
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = Objects.requireNonNull(response.body()).string();
            return JSONObject.parseObject(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
