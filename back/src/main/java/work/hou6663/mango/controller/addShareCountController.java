package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoMessage;
import work.hou6663.mango.service.MangoMessageDetailService;

@RestController
public class addShareCountController {


    @Autowired
    private MangoMessageDetailService mangoMessageDetailService;

    @PostMapping("/share/addShareCount/{messageId}")
    public Integer addShareCount(@PathVariable Integer messageId) {

        MangoMessage message = mangoMessageDetailService.getById(messageId);

        MangoMessage mangoMessage = new MangoMessage();

        mangoMessage.setMessageId(messageId);
        mangoMessage.setMessageShare(message.getMessageShare() + 1);
        mangoMessageDetailService.update(mangoMessage);


        return message.getMessageShare();
    }

}
