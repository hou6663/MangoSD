/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package work.hou6663.mango.config;
/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
public final class OSSFactory {


    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = SpringContextHolder.getBean(CloudStorageConfig.class);
            return new AliyunCloudStorageService(config);
    }

}
