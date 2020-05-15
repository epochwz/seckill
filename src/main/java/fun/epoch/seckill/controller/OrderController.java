package fun.epoch.seckill.controller;

import fun.epoch.core.web.response.Response;
import fun.epoch.seckill.common.Constant;
import fun.epoch.seckill.controller.vo.OrderVO;
import fun.epoch.seckill.controller.vo.UserVO;
import fun.epoch.seckill.service.OrderService;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static fun.epoch.core.web.response.DefaultResponseCode.NEED_LOGIN;

@Validated
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private HttpSession session;

    @PostMapping(value = "/create")
    public Response<?> createOrder(@RequestParam Integer itemId,
                                   @RequestParam @Range(min = 1, message = "下单商品数量不能小于 1") Integer amount,
                                   @RequestParam(name = "promoId", required = false) Integer promoId) {
        UserVO userVO = (UserVO) session.getAttribute(Constant.CURRENT_USER);

        if (userVO == null) return Response.error(NEED_LOGIN, "下单前请先登录");

        return Response.success(OrderVO.of(orderService.createOrder(userVO.getId(), itemId, amount, promoId)));
    }
}
