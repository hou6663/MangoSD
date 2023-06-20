package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoCommentMapper;
import work.hou6663.mango.model.MangoComment;
import work.hou6663.mango.util.SameService;

@Service
public class MangoCommentService extends SameService<MangoCommentMapper,MangoComment> {
}
