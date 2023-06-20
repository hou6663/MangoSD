package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("mango_notice")
public class MangoNotice {
    @TableId(value = "notice_id")
    private Integer noticeId;

    private String noticeDetail;

    /**
     * @return notice_id
     */
    public Integer getNoticeId() {
        return noticeId;
    }

    /**
     * @param noticeId
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * @return notice_detail
     */
    public String getNoticeDetail() {
        return noticeDetail;
    }

    /**
     * @param noticeDetail
     */
    public void setNoticeDetail(String noticeDetail) {
        this.noticeDetail = noticeDetail;
    }
}