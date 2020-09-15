package com.uustop.project.system.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uustop.common.utils.RedisUtils;
import com.uustop.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.uustop.common.utils.ServletUtils;
import com.uustop.common.utils.StringUtils;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;

import java.io.Serializable;

/**
 * 登录验证
 *
 * @author uustop
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            Session session = subject.getSession();
            Serializable id = session.getId();
            User user = (User) subject.getPrincipal();
            //把sessionId 放入redis中
            redisUtils.rpush(user.getUserId() + user.getLoginName() + user.getPhonenumber(), "shiro:session:"+id.toString());
            //设置过期时间
            redisUtils.expire(user.getUserId() + user.getLoginName() + user.getPhonenumber(), 3600*12);
            return success();
        } catch (AuthenticationException e) {
            String msg = "此账户不存在";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "/error/unauth";
    }
}
