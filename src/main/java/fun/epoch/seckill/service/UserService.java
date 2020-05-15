package fun.epoch.seckill.service;

import fun.epoch.seckill.service.model.UserModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static fun.epoch.seckill.common.Constant.Patterns.PATTERN_PASSWORD;
import static fun.epoch.seckill.common.Constant.Patterns.PATTERN_TELPHONE;

@Validated
public interface UserService {
    /**
     * 获取验证码
     *
     * @param telphone 手机号码
     * @return 验证码
     */
    String getOtpCode(@NotBlank @Pattern(regexp = PATTERN_TELPHONE, message = "手机号码格式错误") String telphone);

    /**
     * 获取用户信息
     *
     * @param userId 用户主键
     * @return 用户信息
     */
    UserModel getUserInfo(@NotNull(message = "获取用户信息失败：用户 ID 不能为空") Integer userId);

    /**
     * 注册
     *
     * @param userModel 用户信息
     */
    UserModel register(@Valid UserModel userModel);

    /**
     * 登录
     *
     * @param telphone 手机号码
     * @param password 账号密码
     * @return 用户信息
     */
    UserModel login(
            @NotBlank @Pattern(regexp = PATTERN_TELPHONE, message = "手机号码格式错误") String telphone,
            @NotBlank @Pattern(regexp = PATTERN_PASSWORD, message = "账号密码格式错误：要求 [6,32] 个非空字符") String password
    );
}
