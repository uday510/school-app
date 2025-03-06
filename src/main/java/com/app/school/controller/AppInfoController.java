package com.app.school.controller;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppInfoController implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> map = new HashMap<>();
        map.put("version", "1.0.0");
        map.put("build", "1.0.0");
        map.put("build-time", "1.0.0");
        builder.withDetail("app", map);
    }

}
