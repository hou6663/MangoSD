package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoCommentReply;
import work.hou6663.mango.service.MangoAttendService;
import work.hou6663.mango.service.MangoCommentReplayService;
import work.hou6663.mango.service.MangoNewMessageService;
import work.hou6663.mango.service.MangoUserService;
import work.hou6663.mango.util.isCommentReply.IsCommentReply;

@RestController
public class AddCommentReplyController {

    @Autowired
    private MangoUserService mangoUserService;
    @Autowired
    private MangoCommentReplayService mangoCommentReplayService;
    @Autowired
    private MangoNewMessageService mangoNewMessageService;
    @Autowired
    private MangoAttendService mangoAttendService;

    @Transactional
    @PostMapping("/addCommentReply/{messageId}")
    public IsCommentReply addCommentReply(@PathVariable Integer messageId, @RequestBody MangoCommentReply mangoCommentReply) {

        return new IsCommentReply().isTrue(messageId, mangoCommentReply, mangoUserService, mangoCommentReplayService, mangoNewMessageService,mangoAttendService);

    }


}
