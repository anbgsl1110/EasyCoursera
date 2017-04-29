package com.jasonwangex.easyCoursera.utils;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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


    public static String get(String url, Map<String, Object> params) {
        HttpGet httpGet = new HttpGet(url + JsonUtil.toParams(params));
        return connect(httpGet);
    }

    public static String post(String url, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        build(httpPost, params);
        return connect(httpPost);
    }

    public static String patch(String url, Map<String, Object> params) {
        HttpPatch httpPatch = new HttpPatch(url);
        build(httpPatch, params);
        return connect(httpPatch);
    }

    public static String put(String url, Map<String, Object> params) {
        HttpPut httpPut = new HttpPut(url);
        build(httpPut, params);
        return connect(httpPut);
    }

    public static String delete(String url, Map<String, Object> params) {
        HttpDelete httpDelete = new HttpDelete(url + JsonUtil.toParams(params));
        return connect(httpDelete);
    }

    private static void build(HttpEntityEnclosingRequestBase httpClient, Map<String, Object> params){
        List<NameValuePair> nvps = new ArrayList<>();
        params.forEach((key, value) -> nvps.add(new BasicNameValuePair(key, JsonUtil.toString(value))));

        try {
            httpClient.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
