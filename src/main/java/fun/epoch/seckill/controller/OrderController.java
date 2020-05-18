package fun.epoch.seckill.controller;

import fun.epoch.core.cache.redis.Redis;
import fun.epoch.core.serialization.JSON;
import fun.epoch.core.web.exception.BusinessException;
import fun.epoch.core.web.response.Response;
import fun.epoch.seckill.controller.vo.OrderVO;
import fun.epoch.seckill.controller.vo.UserVO;
import fun.epoch.seckill.service.OrderService;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Random;

import static fun.epoch.core.web.response.DefaultResponseCode.NEED_LOGIN;

@Validated
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping(value = "/create")
    public Response<?> createOrder(@RequestParam Integer itemId, @RequestParam String userToken,
                                   @RequestParam @Range(min = 1, message = "下单商品数量不能小于 1") Integer amount,
                                   @RequestParam(name = "promoId", required = false) Integer promoId) {
        UserVO userVO = Optional.ofNullable(Redis.get(userToken))
                .map(str -> JSON.read(str, UserVO.class))
                .orElseThrow(() -> new BusinessException(NEED_LOGIN, "下单前请先登录"));

        return Response.success(OrderVO.of(orderService.createOrder(userVO.getId(), itemId, amount, promoId)));
    }

    @PostMapping(value = "/create/test")
    public Response<?> createOrderTest() {
        return Response.success(OrderVO.of(orderService.createOrder(new Random().nextInt(Integer.MAX_VALUE), 1000000, 1, 1000000)));
    }
}
