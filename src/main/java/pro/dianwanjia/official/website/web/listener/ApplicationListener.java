package pro.dianwanjia.official.website.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
/**
 * @author WYK
 * @date: 2019/8/27 15:11
 * @description: 容器初始化监听器
 */
@WebListener
public class ApplicationListener implements ServletContextListener{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("liting: contextInitialized");
        ServletContext context = sce.getServletContext();
        // IP存储器
        Map<String, Long[]> ipMap = new HashMap<String, Long[]>();
        context.setAttribute("ipMap", ipMap);
        // 限制IP存储器：存储被限制的IP信息
        Map<String, Long> limitedIpMap = new HashMap<String, Long>();
        context.setAttribute("limitedIpMap", limitedIpMap);
        LOGGER.info("ipmap："+ipMap.toString()+";limitedIpMap:"+limitedIpMap.toString()+"初始化成功");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("监听器销毁成功");
    }
}
