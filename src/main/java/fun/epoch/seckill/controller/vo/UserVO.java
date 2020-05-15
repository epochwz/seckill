package fun.epoch.seckill.controller.vo;

import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.seckill.service.model.UserModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserVO implements Serializable {
    private Integer id;
    private String username;
    private String telphone;
    private Byte gender;
    private Integer age;

    public static UserVO of(@NonNull UserModel userModel) {
        return BeanConverter.convert(userModel, new UserVO());
    }
}
