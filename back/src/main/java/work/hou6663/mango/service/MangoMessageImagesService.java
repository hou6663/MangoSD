package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoMessageImagesMapper;
import work.hou6663.mango.mapper.MangoMessageMapper;
import work.hou6663.mango.model.MangoMessageImages;
import work.hou6663.mango.util.SameService;

@Service
public class MangoMessageImagesService extends SameService<MangoMessageImagesMapper,MangoMessageImages> {

}
