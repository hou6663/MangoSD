package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoMessage;
import work.hou6663.mango.model.MangoMessageImages;
import work.hou6663.mango.service.MangoMessageDetailService;
import work.hou6663.mango.service.MangoMessageImagesService;

import java.util.List;

@RestController
public class GetLostMessageController {
    @Autowired
    private MangoMessageDetailService mangoMessageDetailService;
    @Autowired
    MangoMessageImagesService mangoMessageImagesService;

    @PostMapping("/getMessage/getLostMessage")
    public MangoMessage getLostMessage(){
        MangoMessage mangoMessage = mangoMessageDetailService.getLostMessage();
        MangoMessageImages mangoMessageImages = new MangoMessageImages();
        mangoMessageImages.setMessageId(mangoMessage.getMessageId());
        List<MangoMessageImages> imgList = mangoMessageImagesService.findList(mangoMessageImages);
        mangoMessage.setMessageImages(imgList);
        return mangoMessage;

    }
}
