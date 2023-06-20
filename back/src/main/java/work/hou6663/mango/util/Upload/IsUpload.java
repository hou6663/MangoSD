package work.hou6663.mango.util.Upload;

import work.hou6663.mango.model.MangoMessage;
import work.hou6663.mango.model.MangoMessageImages;
import work.hou6663.mango.model.MangoUser;
import work.hou6663.mango.service.MangoUserService;
import work.hou6663.mango.service.MangoMessageDetailService;
import work.hou6663.mango.service.MangoMessageImagesService;

import java.util.List;

public class IsUpload {

    /**
     * 500 服务器错误
     * 200 上传成功
     * 403 不允许发布,拉黑
     * 400 数据出现问题
     * 401  未登录
     * 1000 非法入侵
     */
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public IsUpload isTrue(MangoMessage mangoMessage, MangoMessageDetailService mangoMessageDetailService, MangoMessageImagesService mangoMessageImagesService, MangoUserService mangoUserService) {
        IsUpload isUpload = new IsUpload();

        MangoUser user = mangoUserService.getById(mangoMessage.getUserId());

        if (user == null) {
            isUpload.setCode(1000);
            return isUpload;
        }

        if(user.getUserAllow()!=1){
            isUpload.setCode(301);
            return isUpload;
        }

        List<String> resultImage = mangoMessage.getResultImage();
        // Bug修复：修复了一个导致'user_phone'为空的问题

        if (mangoMessage.getUserPhone() == null)
            mangoMessage.setUserPhone("");
        mangoMessageDetailService.insertMessageDetail(mangoMessage);


        for (int i = 0; i < resultImage.size(); i++) {
            MangoMessageImages mangoMessageImages = new MangoMessageImages();
            mangoMessageImages.setImageUrl(resultImage.get(i));
            mangoMessageImages.setMessageId(mangoMessage.getMessageId());
            mangoMessageImagesService.add(mangoMessageImages);
        }

        isUpload.setCode(200);

        return isUpload;


    }

}
