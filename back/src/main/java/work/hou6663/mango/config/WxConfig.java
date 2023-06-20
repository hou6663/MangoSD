package work.hou6663.mango.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;



@Configuration
public class WxConfig {
	@Autowired
	private WxProperties properties;

	@Bean

	public WxMaConfig wxMaConfig() {
		WxMaInMemoryConfig config = new WxMaInMemoryConfig();
		config.setAppid(properties.getAppId());
		config.setSecret(properties.getAppSecret());
		config.setMsgDataFormat(properties.getMsgDataFormat());
		return config;
	}

	@Bean
	public WxMaService wxMaService(WxMaConfig maConfig) {
		WxMaService service = new WxMaServiceImpl();
		service.setWxMaConfig(maConfig);
		return service;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		// 创建一个允许跟随重定向的HttpClient
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setRedirectStrategy(new LaxRedirectStrategy())
				.build();

		// 使用HttpClient创建一个HttpComponentsClientHttpRequestFactory实例
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		// 使用HttpComponentsClientHttpRequestFactory构建RestTemplate
		return builder.requestFactory(() -> requestFactory).build();
	}

}