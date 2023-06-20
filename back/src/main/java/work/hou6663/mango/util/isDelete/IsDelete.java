package work.hou6663.mango.util.isDelete;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.hou6663.mango.model.*;
import work.hou6663.mango.otherMethod.DeleteAliyunFile;
import work.hou6663.mango.service.*;
import work.hou6663.mango.model.*;
import work.hou6663.mango.service.*;
import work.hou6663.mango.model.*;
import work.hou6663.mango.service.*;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Component
public class IsDelete {

    @Value("${sms.appid}")
    private String accessKeyId;

    @Value("${sms.appkey}")
    private String accessKeySecret;

    @Value("${sms.bucketName}")
    private String bucketName;

    @Value("${sms.endpoint}")
    private String endpoint;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 500 服务器错误
     * 200 上传成功
     * 403 不允许发布,拉黑
     * 400 数据出现问题
     * 401  未登录
     * 409 权限不足
     * 1000 非法入侵
     */
    public IsDelete isDelete(Integer userId, Integer messageId, MangoMessageImagesService mangoMessageImagesService, MangoUserService mangoUserService, MangoMessageDetailService mangoMessageDetailService, MangoAttendService mangoAttendService, MangoCollectService mangoCollectService, MangoNewMessageService mangoNewMessageService) {
        IsDelete isDelete = new IsDelete();
        isDelete.setCode(500);

        MangoUser user = mangoUserService.getById(userId);

        if (user == null) {
            isDelete.setCode(1000);
            return isDelete;
        }

        MangoMessage message = mangoMessageDetailService.getById(messageId);

        boolean canDelete =false;
        int messageUserIsAdmin = mangoUserService.getById(message.getUserId()).getUserIsAdmin();
        if (message.getUserId().equals(user.getUserId()))
            canDelete = true;
        else if (user.getUserIsAdmin() == 3)
            canDelete = true;
        else if(user.getUserIsAdmin() == 2 && messageUserIsAdmin == 1 )
            canDelete =true;
        if (canDelete) {
            /**
             * 删除对应评论
             */
            mangoMessageDetailService.deleteCommentAndReply(messageId);
            /**
             * 删除我的参与
             */
            MangoAttend mangoAttend = new MangoAttend();
            mangoAttend.setMessageId(messageId);
            mangoAttendService.delete(mangoAttend);
            /**
             * 删除收藏
             */
            MangoCollect mangoCollect = new MangoCollect();
            mangoCollect.setMessageId(messageId);
            mangoCollectService.delete(mangoCollect);

            /**
             * 删除消息
             */
            MangoNewMessage mangoNewMessage = new MangoNewMessage();
            mangoNewMessage.setMessageId(messageId);
            mangoNewMessageService.delete(mangoNewMessage);

            mangoMessageDetailService.deleteById(messageId);
            MangoMessageImages mangoMessageImages = new MangoMessageImages();
            mangoMessageImages.setMessageId(messageId);
            List<MangoMessageImages> images = mangoMessageImagesService.findList(mangoMessageImages);
            mangoMessageImagesService.delete(mangoMessageImages);

            String secretId = accessKeyId;
            String secretKey = accessKeySecret;
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region("ap-shanghai");
            ClientConfig clientConfig = new ClientConfig(region);
            // 这里建议设置使用 https 协议
            // 从 5.6.54 版本开始，默认使用了 https
            clientConfig.setHttpProtocol(HttpProtocol.https);
            // 3 生成 cos 客户端。
            COSClient cosClient = new COSClient(cred, clientConfig);

            DeleteAliyunFile deleteAliyunFile = new DeleteAliyunFile();

            for (int i = 0; i < images.size(); i++) {
                String objectName = images.get(i).getImageUrl();
                deleteAliyunFile.DeleteAliyunFile(objectName, cosClient, bucketName);
            }
            // 关闭OSSClient。
//            ossClient.shutdown();

            isDelete.setCode(200);
        }
        else {
            isDelete.setCode(409);
        }
        return isDelete;
    }
}
