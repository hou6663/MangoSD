package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.*;

public class MangoCollect {

    @TableId(value = "collect_id", type = IdType.AUTO)
    private Integer collectId;

    @TableField("user_id")
    private Integer userId;

    @TableField("message_id")
    private Integer messageId;

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
