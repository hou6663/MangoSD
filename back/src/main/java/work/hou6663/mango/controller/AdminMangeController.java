package work.hou6663.mango.controller;
import cn.binarywang.wx.miniapp.api.WxMaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.hou6663.mango.model.MangoUser;
import work.hou6663.mango.service.MangoUserService;
import work.hou6663.mango.service.SmsService;

import java.util.List;


@Slf4j
@RestController
public class AdminMangeController {
    @Autowired
    private WxMaService wxService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private MangoUserService mangoUserService;

    /**
     * 管理员管理功能
     *
     * @param
     * @param
     * @return mangoUser,List<MangoMessage>
     */
    @Transactional
    @PostMapping("/selectAllUsers")
    public List<MangoUser> selectAllUsers() {
        return mangoUserService.selectAllUser();
    }
    @PostMapping("/selectUserByUserId")
    public MangoUser selectUserByUserId(int userId) {
        return mangoUserService.selectUserByUserId(userId);
    }

    /**
     *
     * @param userId
     * @param status
     * @return
     */
    @PostMapping("/updateBanStatus")
    public int updateBanStatus(int userId, int status) {
        // 根据userId和status更新封禁状态
        mangoUserService.updateBanStatus(userId, status);
        //获取用户
        MangoUser mangoUser = mangoUserService.selectUserByUserId(userId);
        //反馈给用户
        return mangoUser.getUserAllow();
    }

    @PostMapping("/updateUserRole")
    public int updateUserRole(int userId, int role) {
        // 根据userId和status更新封禁状态
        mangoUserService.updateUserRole(userId, role);
        //获取用户
        MangoUser mangoUser = mangoUserService.selectUserByUserId(userId);
        //反馈给用户
        return mangoUser.getUserIsAdmin();
    }


}
