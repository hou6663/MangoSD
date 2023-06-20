package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import java.util.List;

public class MangoMessage {

    //热度分数
    @TableField(exist = false)
    private double score;

    @Override
    public String toString() {
        return "MangoMessage{" +
                "resultImage=" + resultImage +
                ", messageId=" + messageId +
                ", userIdAnonymity=" + userIdAnonymity +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", userPhone='" + userPhone + '\'' +
                ", userMajor='" + userMajor + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", messageDetail='" + messageDetail + '\'' +
                ", messageShare=" + messageShare +
                ", messageComment=" + messageComment +
                ", messageWatch=" + messageWatch +
                ", messageCreatTime=" + messageCreatTime +
                ", messageImages=" + messageImages +
                ", comments=" + comments +
                ", mangoUser=" + mangoUser +
                '}';
    }

    @TableField(exist = false)
    private List<String> resultImage;

    public List<String> getResultImage() {
        return resultImage;
    }

    public void setResultImage(List<String> resultImage) {
        this.resultImage = resultImage;
    }

    //... existing getters and setters for resultImage

    @TableId(value = "message_id")
    private Integer messageId;

    private Integer userIdAnonymity;
    private Integer userId;
    private Integer categoryId;
    private String userPhone;
    private String userMajor;
    private String userLevel;
    private String messageDetail;
    private Integer messageShare;
    private Integer messageComment;
    private Integer messageWatch;
    private Date messageCreatTime;

    @TableField(exist = false)
    private List<MangoMessageImages> messageImages;
    @TableField(exist = false)
    private List<MangoComment> comments;
    @TableField(exist = false)
    private MangoUser mangoUser;


    //... existing getters and setters for MangoUser, messageImages, and comments

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserIdAnonymity() {
        return userIdAnonymity;
    }

    public void setUserIdAnonymity(Integer userIdAnonymity) {
        this.userIdAnonymity = userIdAnonymity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public Integer getMessageShare() {
        return messageShare;
    }

    public void setMessageShare(Integer messageShare) {
        this.messageShare = messageShare;
    }

    public Integer getMessageComment() {
        return messageComment;
    }

    public void setMessageComment(Integer messageComment) {
        this.messageComment = messageComment;
    }

    public Integer getMessageWatch() {
        return messageWatch;
    }

    public void setMessageWatch(Integer messageWatch) {
        this.messageWatch = messageWatch;
    }

    public Date getMessageCreatTime() {
        return messageCreatTime;
    }

    public void setMessageCreatTime(Date messageCreatTime) {
        this.messageCreatTime = messageCreatTime;
    }

    public List<MangoMessageImages> getMessageImages() {
        return messageImages;
    }

    public void setMessageImages(List<MangoMessageImages> messageImages) {
        this.messageImages = messageImages;
    }

    public List<MangoComment> getComments() {
        return comments;
    }

    public void setComments(List<MangoComment> comments) {
        this.comments = comments;
    }

    public MangoUser getMangoUser() {
        return mangoUser;
    }

    public void setMangoUser(MangoUser mangoUser) {
        this.mangoUser = mangoUser;
    }

    //... existing getters and setters for messageId, userIdAnonymity, etc.

    // 按照帖子热度排序
    public static List<MangoMessage> sortMessage(List<MangoMessage> messageList) {
        double k1 = 0.6;
        double k2 = 0.3;
        double k3 = 0.1;

        // 计算每个帖子的热度值
        for (MangoMessage message : messageList) {
            message.score = k1 * message.messageWatch + k2 * message.messageComment + k3 * message.messageShare;
        }

        // 对帖子列表按照热度值进行排序
        messageList.sort((m1, m2) -> Double.compare(m2.score, m1.score));

        return messageList;
    }
}
