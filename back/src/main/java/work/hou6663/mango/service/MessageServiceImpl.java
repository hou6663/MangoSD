package work.hou6663.mango.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.hou6663.mango.mapper.MessageMapper;
import work.hou6663.mango.model.MangoChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.hou6663.mango.service.inter.MessageService;

/**
 * @author lisw
 * @program message
 * @description
 * @createDate 2021-08-18 17:10:38
 * @slogan 长风破浪会有时，直挂云帆济沧海。
 **/
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MangoChatMessage> implements MessageService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int sendMessage(MangoChatMessage mangoChatMessage) {
        //将此二人以往的最后聊天消息，置为不是最后的消息
        log.info("In sendMessage method");
        UpdateWrapper<MangoChatMessage> updateWrapper = new UpdateWrapper();
        updateWrapper.set("is_last",0);
        updateWrapper.eq("del_flag", 0);
        updateWrapper.and(wrapper -> wrapper.and(item -> item.eq("sender", mangoChatMessage.getSender()).eq("receiver", mangoChatMessage.getReceiver()))
                .or(item -> item.eq("receiver", mangoChatMessage.getSender()).eq("sender", mangoChatMessage.getReceiver()))
        );
        this.update(updateWrapper);
        // 保存消息
        mangoChatMessage.setIsLast(true);
        mangoChatMessage.setDelFlag("0");
        this.save(mangoChatMessage);
        return 1;
    }
}
