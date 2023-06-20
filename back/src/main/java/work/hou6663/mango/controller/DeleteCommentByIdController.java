package work.hou6663.mango.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoComment;
import work.hou6663.mango.model.MangoCommentReply;
import work.hou6663.mango.model.MangoUser;
import work.hou6663.mango.service.MangoCommentReplayService;
import work.hou6663.mango.service.MangoCommentService;
import work.hou6663.mango.service.MangoUserService;

@RestController
public class DeleteCommentByIdController {

    @Autowired
    private MangoCommentService mangoCommentService;
    @Autowired
    private MangoUserService mangoUserService;
    @Autowired
    private MangoCommentReplayService mangoCommentReplayService;

    @Transactional
    @PostMapping("/deleteCommentByCommentId/{userId}/{messageId}")
    public Integer deleteCommentByCommentId(@PathVariable Integer userId, @PathVariable Integer messageId) {
        MangoUser user = mangoUserService.getById(userId);
        if (user == null) {
            return 400;
        }
        MangoComment mangoComment = new MangoComment();

        if (user.getUserIsAdmin() == 2) {
            //逻辑问题 ： 如果用户为管理员， 用户将无法删除自己的评论  -> 已修复
            MangoComment  mangoComment1 = mangoCommentService.getById(messageId);
            int userP = mangoUserService.getById(mangoComment1.getUserId()).getUserIsAdmin();
            if ((userP == 2 || userP == 3) && !user.getUserId().equals(mangoComment1.getUserId()))
                return 401;
            mangoComment.setCommentId(messageId);
        }
        else if(user.getUserIsAdmin() == 3){
            mangoComment.setCommentId(messageId);
        }else {
            mangoComment.setCommentId(messageId);
            mangoComment.setUserId(userId);
        }

        mangoCommentService.delete(mangoComment);

        MangoCommentReply mangoCommentReply = new MangoCommentReply();
        mangoCommentReply.setCommentId(messageId);
        mangoCommentReplayService.delete(mangoCommentReply);


        return 200;
    }


    @Transactional
    @PostMapping("/deleteCommentReplyByCommentId/{userId}/{commentReplyId}")
    public Integer deleteCommentReplyByCommentId(@PathVariable Integer userId, @PathVariable Integer commentReplyId) {
        MangoUser user = mangoUserService.getById(userId);
        if (user == null) {
            return 400;
        }
        MangoCommentReply mangoCommentReply = new MangoCommentReply();
        if (user.getUserIsAdmin() == 2) {
            //逻辑问题 ： 如果用户为管理员， 用户将无法删除自己的评论  ->已修复

            MangoCommentReply mangoCommentReply1 = mangoCommentReplayService.getById(commentReplyId);
            int userP = mangoUserService.getById(mangoCommentReply1.getCommentUserId()).getUserIsAdmin();
            if ((userP == 2 || userP == 3 ) && !user.getUserId().equals(mangoCommentReply1.getReplayUserId()))
                return 401;
            mangoCommentReply.setCommentReplyId(commentReplyId);
        }else if(user.getUserIsAdmin() == 3){
            mangoCommentReply.setCommentReplyId(commentReplyId);
        }
        else {
            mangoCommentReply.setCommentReplyId(commentReplyId);
            mangoCommentReply.setReplayUserId(userId);
        }
        mangoCommentReplayService.delete(mangoCommentReply);

        return 200;
    }

}


