package com.jasonwangex.easyCoursera.utils;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjz
 * on 17/2/13.
 */
public class HttpUtil {

    private static String connect(HttpRequestBase requestBase) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String rtnStr = null;
        try {
            CloseableHttpResponse response = httpClient.execute(requestBase);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();

            rtnStr = IOUtils.toString(inputStream, Charset.forName("UTF-8"));

            EntityUtils.consume(entity);
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rtnStr;
    }


    public static Map get(String url, Map<String, Object> params) {
        HttpGet httpGet = new HttpGet(url + JsonUtil.toParams(params));

        String response = connect(httpGet);
        return JsonUtil.toObject(Map.class, response);
    }

    public static Map post(String url, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<>();
        params.forEach((key, value) -> {
            nvps.add(new BasicNameValuePair(key, JsonUtil.toString(value)));
        });

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String response = connect(httpPost);

        return JsonUtil.toObject(Map.class, response);
    }

    public static void main(String[] args) {
        Map map = get("https://www.baidu.com", new HashMap<>());
    }

}
