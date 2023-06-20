package work.hou6663.mango.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oa.wx")
public class WxProperties {
	/**
	 * 设置微信小程序的appId
	 */
	private String appId;
	/**
	 * 设置微信小程序的Secret
	 */
	private String appSecret;
	/**
	 * 消息数据格式
	 */
	private String msgDataFormat;


}
