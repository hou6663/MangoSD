package work.hou6663.mango.model;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("`mango_shop`")
public class MangoShop {
    @TableId(value = "`shop_id`", type = IdType.AUTO)
    private Integer shopId;

    @TableField("`shop_name`")
    private String shopName;

    @TableField("`shop_intro`")
    private String shopIntro;

    @TableField("`shop_phone`")
    private String shopPhone;

    @TableField("`shop_avatar`")
    private String shopAvatar;

    @TableField("`shop_latitude`")
    private String shopLatitude;

    @TableField("`shop_longitude`")
    private String shopLongitude;

    @TableField("`shop_creat_time`")
    private Date shopCreateTime;

    @TableField(exist = false)
    private List<MangoShopImages> shopImages;

    @TableField(exist = false)
    private List<MangoShopBusiness> shopBusinesses;
    public List<MangoShopImages> getShopImages() {
        return shopImages;
    }

    public void setShopImages(List<MangoShopImages> shopImages) {
        this.shopImages = shopImages;
    }

    public List<MangoShopBusiness> getShopBusinesses() {
        return shopBusinesses;
    }

    public void setShopBusinesses(List<MangoShopBusiness> shopBusinesses) {
        this.shopBusinesses = shopBusinesses;
    }

    /**
     * @return shop_id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * @param shopId
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * @return shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * @return shop_intro
     */
    public String getShopIntro() {
        return shopIntro;
    }

    /**
     * @param shopIntro
     */
    public void setShopIntro(String shopIntro) {
        this.shopIntro = shopIntro;
    }

    /**
     * @return shop_phone
     */
    public String getShopPhone() {
        return shopPhone;
    }

    /**
     * @param shopPhone
     */
    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    /**
     * @return shop_avatar
     */
    public String getShopAvatar() {
        return shopAvatar;
    }

    /**
     * @param shopAvatar
     */
    public void setShopAvatar(String shopAvatar) {
        this.shopAvatar = shopAvatar;
    }

    /**
     * @return shop_latitude
     */
    public String getShopLatitude() {
        return shopLatitude;
    }

    /**
     * @param shopLatitude
     */
    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    /**
     * @return shop_longitude
     */
    public String getShopLongitude() {
        return shopLongitude;
    }

    /**
     * @param shopLongitude
     */
    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    /**
     * @return shop_creat_time
     */
    public Date getShopCreateTime() {

        return shopCreateTime;
    }

    /**
     * @param shopCreateTime
     */
    public void setShopCreateTime(Date shopCreateTime) {
        this.shopCreateTime = shopCreateTime;
    }

    @Override
    public String toString() {
        return "MangoShop{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopIntro='" + shopIntro + '\'' +
                ", shopPhone='" + shopPhone + '\'' +
                ", shopAvatar='" + shopAvatar + '\'' +
                ", shopLatitude='" + shopLatitude + '\'' +
                ", shopLongitude='" + shopLongitude + '\'' +
                ", shopCreatTime=" + shopCreateTime +
                ", shopImages=" + shopImages +
                ", shopBusinesses=" + shopBusinesses +
                '}';
    }
}