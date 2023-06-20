package work.hou6663.mango.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("mango_shop_business")
public class MangoShopBusiness {
    @TableId("business_id")
    private Integer businessId;
    @TableField("`shop_id`")
    private Integer shopId;

    @TableField("`shop_goods_image`")
    private String shopGoodsImage;

    @TableField("`shop_goods_title`")
    private String shopGoodsTitle;

    @TableField("`shop_goods_price`")
    private BigDecimal shopGoodsPrice;
    /**
     * @return business_id
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * @param businessId
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
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
     * @return shop_goods_image
     */
    public String getShopGoodsImage() {
        return shopGoodsImage;
    }

    /**
     * @param shopGoodsImage
     */
    public void setShopGoodsImage(String shopGoodsImage) {
        this.shopGoodsImage = shopGoodsImage;
    }

    /**
     * @return shop_goods_title
     */
    public String getShopGoodsTitle() {
        return shopGoodsTitle;
    }

    /**
     * @param shopGoodsTitle
     */
    public void setShopGoodsTitle(String shopGoodsTitle) {
        this.shopGoodsTitle = shopGoodsTitle;
    }

    /**
     * @return shop_goods_price
     */
    public BigDecimal getShopGoodsPrice() {
        return shopGoodsPrice;
    }

    /**
     * @param shopGoodsPrice
     */
    public void setShopGoodsPrice(BigDecimal shopGoodsPrice) {
        this.shopGoodsPrice = shopGoodsPrice;
    }
}