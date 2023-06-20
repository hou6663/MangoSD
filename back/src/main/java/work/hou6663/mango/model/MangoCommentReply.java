package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

public class MangoCommentReply {

    @Override
    public String toString() {
        return "MangoCommentReply{" +
                "commentReplyId=" + commentReplyId +
                ", commentId=" + commentId +
                ", commentUserId=" + commentUserId +
                ", replayUserId=" + replayUserId +
                ", replayUserName='" + replayUserName + '\'' +
                ", receiveUserId=" + receiveUserId +
                ", receiveUserName='" + receiveUserName + '\'' +
                ", replyDetail='" + replyDetail + '\'' +
                ", replyTime=" + replyTime +
                '}';
    }

    @TableId(value = "comment_reply_id")
    private Integer commentReplyId;
    private Integer commentId;
    private Integer commentUserId;
    private Integer replayUserId;
    private String replayUserName;
    private Integer receiveUserId;
    private String receiveUserName;
    private String replyDetail;
    private Date replyTime;

    public Integer getCommentReplyId() {
        return commentReplyId;
    }

    public void setCommentReplyId(Integer commentReplyId) {
        this.commentReplyId = commentReplyId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    public Integer getReplayUserId() {
        return replayUserId;
    }

    public void setReplayUserId(Integer replayUserId) {
        this.replayUserId = replayUserId;
    }

    public String getReplayUserName() {
        return replayUserName;
    }

    public void setReplayUserName(String replayUserName) {
        this.replayUserName = replayUserName;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReplyDetail() {
        return replyDetail;
    }

    public void setReplyDetail(String replyDetail) {
        this.replyDetail = replyDetail;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}
