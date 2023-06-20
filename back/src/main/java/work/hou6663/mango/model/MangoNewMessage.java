package work.hou6663.mango.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("mango_new_message")
public class MangoNewMessage {
    @TableId(value = "new_message_id")
    private Integer newMessageId;

    private Integer userId;
    private Integer newMessageType;
    private Integer messageId;
    private String newMessageDetail;
    private Date newMessageTime;

    /**
     * @return new_message_id
     */
    public Integer getNewMessageId() {
        return newMessageId;
    }

    /**
     * @param newMessageId
     */
    public void setNewMessageId(Integer newMessageId) {
        this.newMessageId = newMessageId;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return new_message_type
     */
    public Integer getNewMessageType() {
        return newMessageType;
    }

    /**
     * @param newMessageType
     */
    public void setNewMessageType(Integer newMessageType) {
        this.newMessageType = newMessageType;
    }

    /**
     * @return message_id
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * @return new_message_detail
     */
    public String getNewMessageDetail() {
        return newMessageDetail;
    }

    /**
     * @param newMessageDetail
     */
    public void setNewMessageDetail(String newMessageDetail) {
        this.newMessageDetail = newMessageDetail;
    }
}