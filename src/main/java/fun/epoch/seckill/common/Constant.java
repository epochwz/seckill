package fun.epoch.seckill.common;

/**
 * Description: 系统常量
 * <p>
 * Created by epoch
 */
public class Constant {
    public static final String CURRENT_USER = "CURRENT_USER";

    /**
     * 注册方式
     */
    public interface RegisterMode {
        String TELPHONE = "telphone";
    }

    /**
     * 促销状态
     */
    public interface PromoStatus {
        int UN_START = 1;   // 未开始
        int STARTING = 2;   // 进行中
        int DISABLED = 3;   // 已结束
    }

    /**
     * 校验规则
     */
    public interface Patterns {
        String PATTERN_USERNAME = "^[0-9a-zA-Z_\\p{InCJKUnifiedIdeographs}-]{1,20}$";
        String PATTERN_PASSWORD = "^\\S{6,32}$";
        String PATTERN_TELPHONE = "^[1][3-9]\\d{9}$";
    }
}
