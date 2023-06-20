package work.hou6663.mango.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.hou6663.mango.mapper.MangoMessageMapper;
import work.hou6663.mango.model.MangoMessage;
import work.hou6663.mango.util.SameService;

import java.util.List;

@Service
public class MangoMessageDetailService extends SameService<MangoMessageMapper,MangoMessage> {

    @Autowired
    private MangoMessageMapper mangoMessageMapper;

    public MangoMessage getLostMessage() {
        return mangoMessageMapper.getLostMessage();
    }

    public Integer insertMessageDetail(MangoMessage mangoMessage) {
        return mangoMessageMapper.insertMessageDetail(mangoMessage);
    }

    public List<MangoMessage> getAllMessage() {
        return mangoMessageMapper.getAllMessage();
    }

    public List<MangoMessage> getAllHotMessage() {return mangoMessageMapper.getAllHotMessage();}

    /**
     * 查询分类所有
     *
     * @param id
     * @return
     */
    public List<MangoMessage> getMessageByCategoryId(Integer id) {
        return mangoMessageMapper.getMessageByCategoryId(id);
    }

    /**
     * 在分类里查询信息
     */

    public List<MangoMessage> getMessageByCategoryAndKeyword(Integer id, String keyword) {
        return mangoMessageMapper.getMessageByCategoryAndKeyword(id, keyword);
    }

    /**
     * 全局查询
     */
    public List<MangoMessage> getMessageByKeyword(String keyword){
        return mangoMessageMapper.getMessageByKeyword(keyword);
    }

    /**
     * 通过用户id查询
     */
    public List<MangoMessage> getMessageDetailByUserId(Integer userId) {
        return mangoMessageMapper.getMessageDetailByUserId(userId);
    }

    /**
     * 删除对应信息下的所有评论以及回复
     */
    public void deleteCommentAndReply(Integer messageId) {

        mangoMessageMapper.deleteCommentAndReply(messageId);
    }
}
