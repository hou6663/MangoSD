package work.hou6663.mango.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("mango_shop_images")
public class MangoShopImages {
    @TableId("shop_detail_id")
    private Integer shopDetailId;
    @TableField("`shop_id`")
    private Integer shopId;

    @TableField("`shop_images`")
    private String shopImages;
    /**
     * @return shop_detail_id
     */
    public Integer getShopDetailId() {
        return shopDetailId;
    }

    /**
     * @param shopDetailId
     */
    public void setShopDetailId(Integer shopDetailId) {
        this.shopDetailId = shopDetailId;
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
     * @return shop_images
     */
    public String getShopImages() {
        return shopImages;
    }

    /**
     * @param shopImages
     */
    public void setShopImages(String shopImages) {
        this.shopImages = shopImages;
    }
}