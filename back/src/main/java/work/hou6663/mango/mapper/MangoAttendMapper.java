package work.hou6663.mango.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Primary;
import work.hou6663.mango.model.MangoAttend;

import java.util.List;


public interface MangoAttendMapper extends BaseMapper<MangoAttend> {
    List<MangoAttend> getAllAttendMessageByUserId(Integer id);
}