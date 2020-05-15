package fun.epoch.seckill.service.impl;

import fun.epoch.core.utils.MD5Utils;
import fun.epoch.core.web.exception.BusinessException;
import fun.epoch.seckill.dao.entity.User;
import fun.epoch.seckill.dao.entity.UserPassword;
import fun.epoch.seckill.dao.repository.UserPasswordRepository;
import fun.epoch.seckill.dao.repository.UserRepository;
import fun.epoch.seckill.service.UserService;
import fun.epoch.seckill.service.model.UserModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;

import static fun.epoch.core.web.response.DefaultResponseCode.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserPasswordRepository userPasswordRepository;

    @Override
    public String getOtpCode(String telphone) {
        userRepository.findByTelphone(telphone).ifPresent(user -> {
            throw new BusinessException(CONFLICT, "手机号码已经被注册");
        });
        // 生成六位数字验证码
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    @Override
    public UserModel getUserInfo(Integer userId) {
        return userRepository.findById(userId).map(UserModel::of).orElseThrow(() -> new BusinessException(NOT_FOUND, "用户不存在"));
    }

    @Override
    @Transactional
    public UserModel register(UserModel userModel) {
        userModel.setPassword(MD5Utils.encodeUTF8(userModel.getPassword()));
        User user = userModel.to();

        try {
            userRepository.save(user);
            userModel.setId(user.getId());
            userPasswordRepository.save(userModel.toUserPassword());
        } catch (DataAccessException e) {
            throw new BusinessException(CONFLICT, "注册失败：用户昵称已经被注册", e);
        } catch (Exception e) {
            throw new BusinessException(INTERNAL_SERVER_ERROR, "注册失败", e);
        }

        return getUserInfo(user.getId());
    }

    @Override
    public UserModel login(String telphone, String password) {
        User user = userRepository.findByTelphone(telphone)
                .orElseThrow(() -> new BusinessException(UN_AUTHORIZED, "账号不存在"));

        UserPassword userPassword = userPasswordRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BusinessException(UN_AUTHORIZED, "未设置密码"));

        if (!MD5Utils.encodeUTF8(password).equals(userPassword.getPassword())) {
            throw new BusinessException(UN_AUTHORIZED, "密码不正确");
        }

        return UserModel.of(user);
    }
}
