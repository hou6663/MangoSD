package work.hou6663.mango.service;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.hou6663.mango.mapper.MangoNoticeMapper;
import work.hou6663.mango.model.MangoNotice;
import work.hou6663.mango.util.SameService;

@Service
public class MangoNoticeService extends SameService<MangoNoticeMapper, MangoNotice> {
}
