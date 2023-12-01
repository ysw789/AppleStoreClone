package com.ysw.applestoreclone.service;

import com.ysw.applestoreclone.sensitiveinfo.SensInfoProvider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopSearchDataService {
    public String getData(String keyword) {
        String clientId = SensInfoProvider.getClientId();
        String clientSecret = SensInfoProvider.getClientSecret();

        String text = null;
        try {
            text = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("!! 검색어 인코딩 실패 !!", e);
        }
        String apiUrl = "https://openapi.naver.com/v1/search/shop.json?query=" + text; // JSON 결과 URL

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        HttpURLConnection conn = connect(apiUrl);
        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
                return dataProc(readBody(conn.getInputStream()));
            else
                return readBody(conn.getErrorStream());
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            conn.disconnect();
        }
    }

    public HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다 : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다 : " + apiUrl, e);
        }
    }

    public String readBody(InputStream body) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(body, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다", e);
        }
    }

    // json 데이터 속 불 필요한 태그 데이터 제거
    public String dataProc(String responseBody) {
        String result = responseBody.replace("\\", "");
        result = result.replaceAll("<b>", "");
        result = result.replaceAll("</b>", "");
        return result;
    }
}
