package pro.dianwanjia.official.website.web.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pro.dianwanjia.official.website.util.NetworkUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author WYK
 * @date: 2019/8/27 15:11
 * @description: 请求过滤器
 */
@WebFilter(urlPatterns = "/*",initParams = @WebInitParam(name = "excludedPaths",value = "/images/favicon.ico"))//过滤排除路径比如(value="/css/*,*.js,image/*")
@ConfigurationProperties(prefix = "request")
public class RequestFilter implements Filter {
    @ApiModelProperty(value = "限制访问时间")
    @Setter
    private long limitedTimeMillis;
    @ApiModelProperty(value = "最大访问次数")
    @Setter
    private int maximumVisitTime;
    @ApiModelProperty(value = "最小安全时间")
    @Setter
    private int minSafeTimeMillis;


    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    /**
     * 不需要被过滤器拦截的页面 ，主要用于静态资源的放行
     */
    private String excludedPaths;
    private String [] excludedPathArray;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化时读取配置的init-param
        excludedPaths = filterConfig.getInitParameter("excludedPaths");
        if(excludedPaths!=null&&!"".equals(excludedPaths)){
            excludedPathArray = excludedPaths.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 过滤排除路径
        if(isFilterExcludeRequest(request)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ServletContext context = request.getServletContext();
        // 过滤限制ip
        Map<String, Long> limitedIpMap = (Map<String, Long>) context.getAttribute("limitedIpMap");
        filterLimitedIpMap(limitedIpMap);
        context.setAttribute("limitedIpMap",limitedIpMap);
        // 验证ip是否被限制
        String ip = NetworkUtil.getIpAddress(request);
        if (isLimitedIp(ip,limitedIpMap)) {
            return;
        }
        // 没有限制则进行ip访问次数验证
        Map<String,Long[]> ipMap  = (Map<String, Long[]>) context.getAttribute("ipMap");
        if(ipMap.containsKey(ip)){
            // 当前时间是否已经超出最小安全时间,是:初始化,否:访问次数+1
            Long[] ipInfo = ipMap.get(ip);
            Long accessTimeMillis = ipInfo[1];
            long currentTimeMillis = System.currentTimeMillis();
            if(currentTimeMillis-accessTimeMillis>=minSafeTimeMillis){
                // 初始化ip访问
                initIpVisitsNumber(ipMap,ip);
            }else{
                ipInfo[0] = ipInfo[0]+1;
                if(ipInfo[0]>=maximumVisitTime){
                    // 在最小安全时间内达到最大访问次数,限制ip
                    limitedIpMap.put(ip, currentTimeMillis + limitedTimeMillis);
                    context.setAttribute("limitedIpMap",limitedIpMap);
                    // ip已被限制,将此ip从ipMap中释放
                    ipMap.remove(ip);
                    context.setAttribute("ipMap",ipMap);
                    return;
                }
            }
        }else{
            // 初始化ip访问
            initIpVisitsNumber(ipMap,ip);
        }

        context.setAttribute("ipMap",ipMap);
        filterChain.doFilter(request,response);
    }

    /**
     * 初始化ip访问次数
     * @param ipMap ip访问记录集合
     * @param ip 当前请求ip
     */
    private void initIpVisitsNumber(Map<String, Long[]> ipMap, String ip) {
        Long[] ipInfo = new Long[2];
        ipInfo[0] = 1l;
        ipInfo[1] = System.currentTimeMillis();
        ipMap.put(ip,ipInfo);
    }

    /**
     * 判断是否是限制ip
     * @param ip
     * @param limitedIpMap 限制ip集合
     * @return
     */
    private boolean isLimitedIp(String ip, Map<String, Long> limitedIpMap) {
        if(ip == null||limitedIpMap == null)return false;
        Iterator<String> iterator = limitedIpMap.keySet().iterator();
        while(iterator.hasNext()){
            if(iterator.next().equals(ip))return true;
        }
        return false;
    }

    /**
     * 过滤限制ip
     *
     * @param limitedIpMap
     */
    private void filterLimitedIpMap(Map<String, Long> limitedIpMap) {
        if (limitedIpMap == null) return;
        Set<String> ipSet = limitedIpMap.keySet();
        Iterator<String> iterator = ipSet.iterator();
        long currentTimeMillis = System.currentTimeMillis();
        while (iterator.hasNext()) {
            Long limitedTimeMillis = limitedIpMap.get(iterator.next());
            // 限制时间已过
            if (limitedTimeMillis <= currentTimeMillis) {
                iterator.remove();
            }
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("销毁成功");
    }

    /**
     * 判断是否是 过滤器直接放行的请求
     * 主要用于静态资源的放行
     * @param request
     * @return
     */
    private boolean isFilterExcludeRequest(HttpServletRequest request) {
        if(null != excludedPathArray && excludedPathArray.length > 0) {
            String url = request.getRequestURI();
            for (String ecludedUrl : excludedPathArray) {
                if (ecludedUrl.startsWith("*.")) {
                    // 如果配置的是后缀匹配, 则把前面的*号干掉，然后用endWith来判断
                    if(url.endsWith(ecludedUrl.substring(1))){
                        return true;
                    }
                } else if (ecludedUrl.endsWith("/*")) {
                    if(!ecludedUrl.startsWith("/")) {
                        // 前缀匹配，必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    // 如果配置是前缀匹配, 则把最后的*号干掉，然后startWith来判断
                    String prffixStr = request.getContextPath() + ecludedUrl.substring(0, ecludedUrl.length() - 1);
                    if(url.startsWith(prffixStr)) {
                        return true;
                    }
                } else {
                    // 如果不是前缀匹配也不是后缀匹配,那就是全路径匹配
                    if(!ecludedUrl.startsWith("/")) {
                        // 全路径匹配，也必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    String targetUrl = request.getContextPath() + ecludedUrl;
                    if(url.equals(targetUrl)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
