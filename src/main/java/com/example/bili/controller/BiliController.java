package com.example.bili.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.bili.entity.Bilibili;
import com.example.bili.mapper.BilibiliMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class BiliController {
    private final BilibiliMapper bilibiliMapper;

    @Autowired
    public BiliController(BilibiliMapper bilibiliMapper) {
        this.bilibiliMapper = bilibiliMapper;
    }

    @GetMapping("")
    public String root() {
        return "http://localhost:8888/bili?s=100&e=200";
    }

    @GetMapping("/bili")
    public void bili(Integer s, Integer e) {
        if (e == null || e == 0 || s == null || s == 0 || s > e) return;

        Bilibili bili;
        String urlInfo, urlStat;
        JSONObject objInfo, objStat, dataInfo, dataStat;
        long dt = System.currentTimeMillis(), dtt = dt;
        for (int i = s; i < e; ++i) {
            checkTime(dt);
            dt = System.currentTimeMillis();

            bili = new Bilibili();
            bili.setMid(i);

            urlInfo = "https://api.bilibili.com/x/space/acc/info?mid=" + i + "&jsonp=jsonp";
            objInfo = JSONObject.parseObject(biliGet(urlInfo));
            if ("0".equals(objInfo.get("code").toString())
                    && "0".equals(objInfo.get("message").toString())) {
                dataInfo = JSONObject.parseObject(objInfo.get("data").toString());
                bili.setName(dataInfo.get("name").toString())
                        .setBirthday(dataInfo.get("birthday").toString())
                        .setSex(dataInfo.get("sex").toString())
                        .setLevel(Integer.valueOf(dataInfo.get("level").toString()));
            }

            urlStat = "https://api.bilibili.com/x/relation/stat?vmid=" + i + "&jsonp=jsonp";
            objStat = JSONObject.parseObject(biliGet(urlStat));
            if ("0".equals(objStat.get("code").toString())
                    && "0".equals(objStat.get("message").toString())) {
                dataStat = JSONObject.parseObject(objStat.get("data").toString());
                bili.setFollower(Integer.valueOf(dataStat.get("follower").toString()))
                        .setFollowing(Integer.valueOf(dataStat.get("following").toString()));
            }
            bili.setDt(LocalDateTime.now());
            bilibiliMapper.insert(bili);
            if (i % 10 == 0) {
                System.out.println("第（" + i + "）个完成，本组耗时" + (System.currentTimeMillis() - dtt) + "毫秒");
                dtt = System.currentTimeMillis();
            }
        }
        System.out.println("end");
    }

    private static String biliGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseInfo = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            // 代理设置 HttpHost
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            if (status == 200 && entity != null) {
                responseInfo = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            responseInfo = biliGet(url);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseInfo;
    }

    private static void checkTime(long dt) {
        long diff = System.currentTimeMillis() - dt;
        if (diff < 250) {
            try {
                Thread.sleep(250 - diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 3:05 开始睡眠一小时
        if (LocalTime.now().getHour() == 3) {
            if (LocalTime.now().getMinute() == 5) {
                try {
                    Thread.sleep(60 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
