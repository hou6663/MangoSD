package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoCollectMapper;
import work.hou6663.mango.model.MangoCollect;
import work.hou6663.mango.util.SameService;

import java.util.List;

@Service
public class MangoCollectService extends SameService<MangoCollectMapper,MangoCollect> {

    @Autowired
    private MangoCollectMapper mangoCollectMapper;

    public List<MangoCollect> getAllCollectionMessageByUserId(Integer userId) {
        return mangoCollectMapper.getAllCollectionMessageByUserId(userId);
    }
}
