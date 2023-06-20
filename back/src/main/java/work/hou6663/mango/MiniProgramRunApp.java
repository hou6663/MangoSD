
/*
 * 版权所有（C）2023。保留所有权利。
 *
 * 本软件是个人开源项目，仅供学习和个人使用。
 * 未经许可，严禁将本软件的任何部分用于商业目的或进行售卖。
 *
 * 该软件受到版权法和国际版权条约的保护。
 * 任何未经授权的复制、使用、修改或分发本软件的行为将受到法律追究。
 *
 * 如需获得许可，请联系：572357525@qq.com
 *
 */

package work.hou6663.mango;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
//@MapperScan(basePackages = "work.hou6663.mango.mapper")
@EnableTransactionManagement

public class MiniProgramRunApp extends SpringBootServletInitializer {
    public static void main(String args[]) {
        SpringApplication.run(MiniProgramRunApp.class, args);
    }
}
