package work.hou6663.mango.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import work.hou6663.mango.model.MangoMessage;

import java.util.List;

public interface MangoMessageMapper extends BaseMapper<MangoMessage> {

    MangoMessage getLostMessage();

    Integer insertMessageDetail(MangoMessage mangoMessage);

    List<MangoMessage> getAllMessage();

    List<MangoMessage> getAllHotMessage();

    List<MangoMessage> getMessageByCategoryId(Integer id);

    List<MangoMessage> getMessageByCategoryAndKeyword(@Param("id") Integer id,@Param("keyword") String keyword);

    List<MangoMessage> getMessageDetailByUserId (Integer userId);

    List<MangoMessage> getMessageByKeyword(@Param("keyword") String keyword);

    void deleteCommentAndReply(@Param("id") Integer id);



}