package work.hou6663.mango.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import work.hou6663.mango.mapper.MangoUserMapper;
import work.hou6663.mango.model.MangoUser;
import work.hou6663.mango.util.SameService;

import java.util.List;

@Service
public class MangoUserService extends SameService<MangoUserMapper, MangoUser> {
    @Autowired
    private MangoUserMapper mangoUserMapper;


   /*
     * 新用户插入
     *
     * @param mangoUser
     * @return
     */
    public Integer insertUserMessage(MangoUser mangoUser) {
        return mangoUserMapper.insertUserMessage(mangoUser);
    }

    /**
     * 得到符合条件的用户数
     *
     * @param mangoUser
     * @return
     */

    public Integer getUserCount(MangoUser mangoUser) {
        QueryWrapper<MangoUser> queryWrapper = new QueryWrapper<>(mangoUser);
        return count(queryWrapper);
    }


    /**
     * 更新用户信息
     *
     * @param mangoUser
     */
    public void updateUserMessage(MangoUser mangoUser) {
        mangoUserMapper.updateById(mangoUser);
    }

    /**
     * 返回符合条件的用户信息
     */
    public List<MangoUser> getUserMessageByOtherMessage(MangoUser mangoUser) {
        QueryWrapper<MangoUser> queryWrapper = new QueryWrapper<>(mangoUser);  // 根据条件创建查询包装器
        return list(queryWrapper);
    }


    /**
     * 通过openid查找用户
     */
    public MangoUser selectUserByOpenid(String userOpenid){
        return  mangoUserMapper.selectUserByOpenid(userOpenid);
    }

    public MangoUser SelectUserById(int userId){
        return  mangoUserMapper.selectUserByID(userId);
    }

    public void UpdateUserInfo(MangoUser mangoUser)
    {
        mangoUserMapper.updateUserInfo(mangoUser);
    }

    public List<MangoUser> selectAllUser(){
        return list();
    }

    public MangoUser selectUserByUserId(int id){
        return mangoUserMapper.selectUserByID(id);
    }

    public int updateBanStatus(int userId, int status){return mangoUserMapper.updateBanStatus(userId, status);}
    public int updateUserRole(int userId, int roleId){return mangoUserMapper.updateUserRole(userId, roleId);}


}
