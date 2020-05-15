package fun.epoch.seckill.service.model;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.dao.entity.User;
import fun.epoch.seckill.dao.entity.UserPassword;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static fun.epoch.seckill.common.Constant.Patterns.PATTERN_TELPHONE;
import static fun.epoch.seckill.common.Constant.Patterns.PATTERN_USERNAME;

@Getter
@Setter
@ToString
public class UserModel {
    // 用户主键
    private Integer id;
    // 用户名称
    @NotNull
    @Pattern(regexp = PATTERN_USERNAME, message = "账号名称格式错误：要求 [1,20] 个合法字符 (字母、数字、下划线、中日韩字符)")
    private String username;
    // 手机号码
    @NotNull
    @Pattern(regexp = PATTERN_TELPHONE, message = "手机号码格式错误")
    private String telphone;
    // 用户密码
    @NotBlank(message = "密码不能为空")
    private String password;
    // 用户性别
    @NotNull
    @Range(max = 2, message = "性别不合法：正确的取值范围是　[0,2]")
    private Byte gender;
    // 用户年龄
    @NotNull
    @Range(max = 150, message = "年龄不合法：正确的取值范围是　[0,150]")
    private Integer age;
    // 注册方式
    @NotNull
    @NotBlank(message = "注册方式不能为空")
    private String registerMode;
    // 三方认证
    private String thirdPartyId;

    public static UserModel of(@NonNull User user) {
        return BeanConverter.convert(user, new UserModel());
    }

    public UserPassword toUserPassword() {
        UserPassword userPassword = BeanConverter.convert(this, new UserPassword());
        userPassword.setUserId(this.getId());
        return userPassword;
    }

    public User to() {
        return BeanConverter.convert(this, new User());
    }
}
