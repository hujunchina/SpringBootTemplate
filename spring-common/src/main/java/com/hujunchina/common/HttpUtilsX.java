package com.hujunchina.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/7 4:21 下午
 * @Version 1.0
 */
public class HttpUtilsX {

    public static String doPost(String url, Map<String, Object> params) {
        //【1】声明读写字节流
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String result = null;

        try {
            //【2】URL
            URL urlPath = new URL(url);
            //【3】得到连接器
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
            //【4】配置连接器
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            //【5】构造参数
            StringBuilder buffer = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            buffer.deleteCharAt(buffer.length() - 1);
            //【6】发送参数
            outputStream = httpURLConnection.getOutputStream();
            outputStream.write(buffer.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            //【7】处理返回Code
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
//                log.info("{0} - {1}", ConstTag.TAG, "Http post 请求失败");
                return null;
            }
            //【8】处理返回Body
            inputStream = httpURLConnection.getInputStream();
            byte[] data = readStream(inputStream, httpURLConnection.getContentLength());
            assert data != null;
            result = new String(data, StandardCharsets.UTF_8);
            inputStream.close();

        } catch (Exception ignored) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception ignored) { }
        }
        return result;
    }

    private static byte[] readStream(InputStream inputStream, int contentLength) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) { //一个字节一个字节读取
            byte[] buffer = new byte[1024];
            int len = -1;
            int total = 0;
            while ((len = inputStream.read(buffer)) != -1 || total < contentLength) {
                total += len;
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
//            log.info("{0} - {1}", ConstTag.TAG, "读取返回的字节流出错");
        }
        return null;
    }

    /** 上传图片*/
    public static String uploadImg(String url, String token, byte[] fileByte, String fileType, String fileName) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();

        RequestBody requestBody = RequestBody.create(MediaType.parse(fileType), fileByte);

        MultipartBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, requestBody).build();
        Request request = new Request.Builder().url(url).addHeader("file_upload_token", token).post(multipartBody).build();
        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            return null;
        }

        assert response.body() != null;
        JSONObject upload = JSON.parseObject(response.body().string());
        if (upload.getBoolean("Success")) {
            upload = upload.getJSONObject("result");
            return upload.getString("tmp_file_id");
        }
        return null;
    }

    /** 下载图片，返回图片的字节数组*/
    public static byte[] downloadImg(String url){
        InputStream input = null;
        HttpURLConnection http = null;
        try {
            URL urlEntry = new URL(url);
            http = (HttpURLConnection) urlEntry.getContent();
            http.connect();
            input = http.getInputStream();
            return readStream(input, http.getContentLength());
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (http != null ) {
                    http.disconnect();
                }
            } catch (Exception e) { }
        }
    }
}
