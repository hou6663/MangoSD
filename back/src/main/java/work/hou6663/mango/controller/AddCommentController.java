package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.service.MangoAttendService;
import work.hou6663.mango.service.MangoCommentService;
import work.hou6663.mango.service.MangoNewMessageService;
import work.hou6663.mango.service.MangoUserService;
import work.hou6663.mango.util.isComment.IsComment;

@RestController
public class AddCommentController {

    @Autowired
    private MangoUserService mangoUserService;
    @Autowired
    private MangoCommentService mangoCommentService;
    @Autowired
    private MangoNewMessageService mangoNewMessageService;
    @Autowired
    private MangoAttendService mangoAttendService;

    @Transactional
    @PostMapping("/addComment/{userId}/{messageId}/{messageUserId}")
    public IsComment addComment(@PathVariable Integer userId, @RequestBody String userComment, @PathVariable Integer messageId, @PathVariable Integer messageUserId) {
        return new IsComment().isTrue(userId, messageId, userComment, messageUserId, mangoUserService, mangoCommentService, mangoNewMessageService, mangoAttendService);
    }

}
