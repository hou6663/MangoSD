package work.hou6663.mango.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import work.hou6663.mango.model.MangoCollect;

import java.util.List;

public interface MangoCollectMapper extends BaseMapper<MangoCollect> {
    List<MangoCollect>  getAllCollectionMessageByUserId(Integer id);

}