package fun.epoch.seckill.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * Description: Customized embedded Tomcat
 * <p>
 * Created by epoch
 */
@Slf4j
@Component // 当 Spring 容器中不存在 TomcatEmbeddedServletContainerFactory 这个 Bean 时，则会自动加载本类
public class TomcatConfiguration implements WebServerFactoryCustomizer<WebServerFactory> {
    @Value("${server.tomcat.keepAliveTimeOut}")
    private int keepAliveTimeOut;
    @Value("${server.tomcat.maxKeepAliveRequests}")
    private int maxKeepAliveRequests;

    @Override
    public void customize(WebServerFactory factory) {
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(connector -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            // 当客户端 n 秒内没有发送请求则断开长连接
            protocol.setKeepAliveTimeout(1000 * keepAliveTimeOut);
            // 当客户端连续发送超过 n 个请求时断开连接
            protocol.setMaxKeepAliveRequests(maxKeepAliveRequests);
        });
        log.info("Customized embedded Tomcat was loaded success");
    }
}
