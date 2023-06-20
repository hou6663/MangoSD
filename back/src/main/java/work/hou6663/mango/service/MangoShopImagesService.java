package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoShopImagesMapper;
import work.hou6663.mango.model.MangoShopImages;
import work.hou6663.mango.util.SameService;

import java.util.List;

@Service
public class MangoShopImagesService extends SameService<MangoShopImagesMapper,MangoShopImages> {

    public List<MangoShopImages> selectByShopId(Integer shopId){
        return baseMapper.selectByShopId(shopId);
    }

}
