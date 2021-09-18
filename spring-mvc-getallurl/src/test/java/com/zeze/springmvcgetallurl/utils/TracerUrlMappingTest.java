package com.zeze.springmvcgetallurl.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
class TracerUrlMappingTest {

    @Test
    void getUrlPathValueMapping() {

        TracerUrlMapping tracerUrlMapping = new TracerUrlMapping();
        HashMap<String, String> pathMaping = new HashMap<>();
        pathMaping.put("api/{userid}/info", "用户信息");
        pathMaping.put("api/user/{info}", "干扰用户信息");
        pathMaping.put("api/{userid}/ext", "用户拓展信息");
        pathMaping.put("api/test/ext", "干扰用户拓展信息");
        pathMaping.put("api", "所有的用户信息");
        pathMaping.put("api/{userid}", "所有的用户信息");
        tracerUrlMapping.setMaping(pathMaping);

        assertNotNull(tracerUrlMapping.getUrlPathValueMapping());
        assertEquals("用户信息", tracerUrlMapping.getChineseMapping("api/1/info"));
        assertEquals("用户拓展信息", tracerUrlMapping.getChineseMapping("api/1/ext"));

        assertEquals("干扰用户拓展信息", tracerUrlMapping.getChineseMapping("api/test/ext"));
        assertEquals(null, tracerUrlMapping.getChineseMapping("api/test/ext/123"));
        assertEquals("干扰用户信息", tracerUrlMapping.getChineseMapping("api/user/ext"));
        assertEquals("所有的用户信息", tracerUrlMapping.getChineseMapping("api"));

    }

    @Test
    void regexTest() {
        String pattern = "/api/.\\w*" ;
        assertEquals(true, Pattern.matches(pattern, "/api/123"));

    }

    @Test
    void testNullMapping() {
        TracerUrlMapping tracerUrlMapping = new TracerUrlMapping();
        assertNull(tracerUrlMapping.getUrlPathValueMapping());
    }
}