package com.zeze.springmvcgetallurl.utils;

import com.zeze.springmvcgetallurl.anno.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@Component
public class TracerUrlMapping {

    /**
     * 用于存放没有路径参数的urlmapping
     */
    private HashMap<String, String> urlMapping = new HashMap<>();

    /**
     * 用于存放有路径参数的urlmapping
     */
    private HashMap<String, String> pathValueMapping = new HashMap<>();

    /**
     * 存放上下文的容器
     */
    @Autowired
    private WebApplicationContext applicationContext;


    /**
     * get the mapping between url and chinese char
     *
     * @return urlmappings
     */
    private HashMap<String, String> getAllUrlMapping() {
        // RequestMappingHandlerMappingc.class 不止一个bean，
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class, "requestMappingHandlerMapping");
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        HashMap<String, String> urlMap = new HashMap<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String key = "";
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                key = requestMethod.toString();
            }
            // eg: GET /api/test
            key = key + " " + info.getPatternsCondition().getPatterns().toArray()[0];
            Tracer annotation = method.getMethod().getAnnotation(Tracer.class);

            String value = key;
            if (annotation != null) {
                value = annotation.OperationName();
            }
            urlMap.put(key, value);
        }
        return urlMap;
    }

    /**
     * 路径参数正则匹配
     * eg： api/{userid}/info     api/./info      api/1/info
     *      api/user/{info}       api/user/.\\w*  api/user/test
     *      api/{userid}          api/.\\w*       opi/test
     * @return
     */
    public HashMap<String, String> getUrlPathValueMapping() {
        if (urlMapping.isEmpty()) {
            return new HashMap<>();
        }
        final Set<Map.Entry<String, String>> entries = urlMapping.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            final String key = entry.getKey();

            if (key.contains("{") && key.contains("}")) {
                int left = key.indexOf("{");
                int right = key.indexOf("}");
                String leftStr = key.substring(0, left);
                String rightStr = key.substring(right+1);
                String newKey= leftStr + "." + rightStr;
                if ("".equals(rightStr)) {
                    newKey = newKey + "\\w*";
                }
                pathValueMapping.put(newKey, entry.getValue());
            }
        }
        return pathValueMapping;
    }

    public void setMaping(HashMap<String, String> maping) {
        this.urlMapping = maping;
    }

    /**
     * 获取路径参数映射的中文字符
     * @param toBeMatched toBeMatched
     * @return 对应的中文字符
     */
    public String getChineseFormPathMapping(String toBeMatched) {
        if (pathValueMapping.isEmpty()) {
            pathValueMapping = getUrlPathValueMapping();
        }
        AtomicReference<String> result = new AtomicReference<>();
        pathValueMapping.forEach(
                (pathValue, chineseChar) -> {
                    if (Pattern.matches(pathValue, toBeMatched)) {
                        result.set(chineseChar);
                    }
                }
        );

        return result.get();
    }

    /**
     * 获取非路径参数映射的中文字符
     * @param url url
     * @return 对应的中文字符
     */
    public String getChineseFormUrlMapping(String url) {
        if (urlMapping.isEmpty()) {
            urlMapping = getAllUrlMapping();
        }
        return urlMapping.get(url);
    }

    public String getChineseMapping(String url) {
        String chineseFormUrlMapping = getChineseFormUrlMapping(url);
        if (chineseFormUrlMapping != null) {
            return chineseFormUrlMapping;
        }
        String chineseFormPathMapping = getChineseFormPathMapping(url);
        if (chineseFormPathMapping != null) {
            return chineseFormPathMapping;
        }

        return url;
    }

}
