package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("mango_user")
public class MangoUser {
    @TableId("user_id")
    private Integer userId;
    @TableField("`user_openid`")
    private String userOpenid;

    @TableField("`user_gender`")
    private Integer userGender;

    @TableField("`user_avatar`")
    private String userAvatar;

    @TableField("`user_nickname`")
    private String userNickname;

    @TableField("`user_is_admin`")
    private Integer userIsAdmin;

    @TableField("`user_allow`")
    private Integer userAllow;

    @TableField("`user_creat_time`")
    private Date userCreatTime;

    @TableField("`user_city`")
    private String userCity;

    @TableField("`user_grade`")
    private String userGrade;

    @TableField("`user_phone`")
    private String userPhone;

    @TableField("`user_phone_code`")
    private String userPhoneCode;

    @TableField("`user_phone_code_time`")
    private Date userPhoneCodeTime;

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserPhone_Code(String userPhone_Code) {
        this.userPhoneCode = userPhone_Code;
    }

    public void setUserPhone_Code_Time(Date userPhone_Code_Time) {
        this.userPhoneCodeTime = userPhone_Code_Time;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserPhone_Code() {
        return userPhoneCode;
    }

    public Date getUserPhone_Code_Time() {
        return userPhoneCodeTime;
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
     * @return user_openid
     */
    public String getUserOpenid() {
        return userOpenid;
    }

    /**
     * @param userOpenid
     */
    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid;
    }

    /**
     * @return user_gender
     */
    public Integer getUserGender() {
        return userGender;
    }

    /**
     * @param userGender
     */
    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    /**
     * @return user_avatar
     */
    public String getUserAvatar() {
        return userAvatar;
    }

    /**
     * @param userAvatar
     */
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    /**
     * @return user_nickname
     */
    public String getUserNickname() {
        return userNickname;
    }

    /**
     * @param userNickname
     */
    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    /**
     * @return user_is_admin
     */
    public Integer getUserIsAdmin() {
        return userIsAdmin;
    }

    /**
     * @param userIsAdmin
     */
    public void setUserIsAdmin(Integer userIsAdmin) {
        this.userIsAdmin = userIsAdmin;
    }

    /**
     * @return user_allow
     */
    public Integer getUserAllow() {
        return userAllow;
    }

    /**
     * @param userAllow
     */
    public void setUserAllow(Integer userAllow) {
        this.userAllow = userAllow;
    }

    /**
     * @return user_creat_time
     */
    public Date getUserCreatTime() {
        return userCreatTime;
    }

    /**
     * @param userCreatTime
     */
    public void setUserCreatTime(Date userCreatTime) {
        this.userCreatTime = userCreatTime;
    }

    /**
     * @return user_city
     */
    public String getUserCity() {
        return userCity;
    }

    /**
     * @param userCity
     */
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    /**
     * @return user_grade
     */
    public String getUserGrade() {
        return userGrade;
    }

    /**
     * @param userGrade
     */
    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }
}