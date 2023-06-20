/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package work.hou6663.mango.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 云存储配置信息
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    //阿里云绑定的域名
    private String aliyunDomain;

    //阿里云路径前缀
    private String aliyunPrefix;

    //阿里云EndPoint
    private String aliyunEndPoint;

    //阿里云AccessKeyId
    private String aliyunAccessKeyId;

    //阿里云AccessKeySecret
    private String aliyunAccessKeySecret;

    //阿里云BucketName
    private String aliyunBucketName;
}
