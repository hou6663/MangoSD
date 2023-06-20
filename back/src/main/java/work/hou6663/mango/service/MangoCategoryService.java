package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoCategoryMapper;
import work.hou6663.mango.model.MangoCategory;
import work.hou6663.mango.util.SameService;

@Service
public class MangoCategoryService extends SameService<MangoCategoryMapper,MangoCategory> {
}
