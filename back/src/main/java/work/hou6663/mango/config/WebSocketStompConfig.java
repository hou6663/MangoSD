package work.hou6663.mango.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lisw
 * @program ly-project
 * @description
 * @createDate 2021-05-30 11:29:45
 * @slogan 长风破浪会有时，直挂云帆济沧海。
 **/
@Configuration
public class WebSocketStompConfig{
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {

        return new ServerEndpointExporter();
    }
}
