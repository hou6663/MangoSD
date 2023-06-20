package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoShopMapper;
import work.hou6663.mango.model.MangoShop;
import work.hou6663.mango.util.SameService;

@Service
public class MangoShopService extends SameService<MangoShopMapper,MangoShop> {
    @Autowired
    private MangoShopMapper mangoShopMapper;


}
