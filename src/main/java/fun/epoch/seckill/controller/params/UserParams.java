package fun.epoch.seckill.controller.params;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static fun.epoch.seckill.common.Constant.Patterns.*;

@Getter
@Setter
public class UserParams {
    @NotNull
    @Pattern(regexp = PATTERN_USERNAME, message = "账号名称格式错误：要求 [1,20] 个合法字符 (字母、数字、下划线、中日韩字符)")
    private String username;
    @NotNull
    @Pattern(regexp = PATTERN_PASSWORD, message = "账号密码格式错误：要求 [6,32] 个非空字符")
    private String password;
    @NotNull
    @Pattern(regexp = PATTERN_TELPHONE, message = "手机号码格式错误")
    private String telphone;
    @NotNull
    @Range(max = 150, message = "年龄不合法：正确的取值范围是　[0,150]")
    private Integer age;
    @NotNull
    @Range(max = 2, message = "性别不合法：正确的取值范围是　[0,2]")
    private Byte gender;
    @NotBlank(message = "验证码不能为空")
    private String otpCode;
}
