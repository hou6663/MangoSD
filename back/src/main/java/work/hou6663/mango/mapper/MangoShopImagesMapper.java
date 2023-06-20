package work.hou6663.mango.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import work.hou6663.mango.model.MangoShopImages;

import java.util.List;

public interface MangoShopImagesMapper extends BaseMapper<MangoShopImages> {
    public List<MangoShopImages> selectByShopId(int shopId);
}