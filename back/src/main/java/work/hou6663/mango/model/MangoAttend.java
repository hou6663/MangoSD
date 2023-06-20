package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

public class MangoAttend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "attend_id", type = IdType.AUTO)
    private Integer attendId;

    @TableField("user_id")
    private Integer userId;

    @TableField("message_id")
    private Integer messageId;

    /**
     * @return attend_id
     */
    public Integer getAttendId() {
        return attendId;
    }

    /**
     * @param attendId
     */
    public void setAttendId(Integer attendId) {
        this.attendId = attendId;
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
}
