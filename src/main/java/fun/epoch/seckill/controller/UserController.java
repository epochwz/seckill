package fun.epoch.seckill.controller;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.core.web.response.Response;
import fun.epoch.seckill.common.Constant;
import fun.epoch.seckill.controller.params.UserParams;
import fun.epoch.seckill.controller.vo.UserVO;
import fun.epoch.seckill.service.UserService;
import fun.epoch.seckill.service.model.UserModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.util.Random;

import static fun.epoch.seckill.common.Constant.CURRENT_USER;
import static fun.epoch.seckill.common.Constant.Patterns.PATTERN_PASSWORD;
import static fun.epoch.seckill.common.Constant.Patterns.PATTERN_TELPHONE;

@Validated
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private HttpSession session;

    @PostMapping("/getotp") // 获取短信验证码
    public Response<String> getOtp(
            @Pattern(regexp = PATTERN_TELPHONE, message = "手机号码格式错误") @RequestParam String telphone
    ) {
        // 六位数字验证码
        String otpCode = userService.getOtpCode(telphone);
        // 绑定验证码 & 手机号码
        session.setAttribute(telphone, otpCode);
        // 此处直接返回
        return Response.success(otpCode);
    }

    @PostMapping("/register")
    public Response<UserVO> register(@Validated @RequestBody UserParams params) {
        String otpCode = (String) session.getAttribute(params.getTelphone());
        if (!params.getOtpCode().equals(otpCode)) {
            return Response.error("验证码错误，请重试");
        }

        UserModel userModel = BeanConverter.convert(params, new UserModel());
        userModel.setRegisterMode(Constant.RegisterMode.TELPHONE);

        return Response.success(UserVO.of(userService.register(userModel)));
    }

    @PostMapping("/login")
    public Response<UserVO> login(
            @RequestParam @Pattern(regexp = PATTERN_TELPHONE, message = "手机号码格式错误") String telphone,
            @RequestParam @Pattern(regexp = PATTERN_PASSWORD, message = "账号密码格式错误：要求 [6,32] 个非空字符") String password
    ) {
        UserVO userVO = UserVO.of(userService.login(telphone, password));
        session.setAttribute(CURRENT_USER, userVO);
        return Response.success(userVO);
    }

    @GetMapping("/logout")
    public Response<?> logout() {
        session.removeAttribute(CURRENT_USER);
        return Response.success();
    }
}
