package com.zeze.springmvcgetallurl.utils;

import com.zeze.springgetallurl.anno.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@Component
public class TracerUrlMapping {


    @Autowired
    private WebApplicationContext applicationContext;


    /**
     * get the mapping between url and chinese char
     * @return urlmappings
     */
    public HashMap<String, String> getUrlMapping() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

//		List<String> urlList = new ArrayList<>();
//		for (RequestMappingInfo info : map.keySet()) {
//			// 获取url的Set集合，一个方法可能对应多个url
//			Set<String> patterns = info.getPatternsCondition().getPatterns();
//
//			for (String url : patterns) {
//				urlList.add(url);
//			}
//		}
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
            key = key + " " +info.getPatternsCondition().getPatterns().toArray()[0];
            Tracer annotation = method.getMethod().getAnnotation(Tracer.class);
            String value = key;
            if (annotation != null) {
                value = annotation.OperationName();
            }
            urlMap.put(key, value);
        }
        return urlMap;
    }


}
