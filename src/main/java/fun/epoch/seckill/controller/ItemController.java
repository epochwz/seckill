package fun.epoch.seckill.controller;

import fun.epoch.core.cache.redis.Redis;
import fun.epoch.core.serialization.JSON;
import fun.epoch.core.web.bean.BeanConverter;
import fun.epoch.core.web.response.Response;
import fun.epoch.seckill.controller.params.ItemParams;
import fun.epoch.seckill.controller.vo.ItemVO;
import fun.epoch.seckill.service.ItemService;
import fun.epoch.seckill.service.model.ItemModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ItemController {
    private static final String KEY_PREFIX_ITEM = "seckill:item:";
    @Resource
    private ItemService itemService;

    @GetMapping("/detail")
    public Response<ItemVO> detail(@NotNull(message = "商品 ID 不能为空") Integer id) {
        ItemVO itemVO = JSON.read(Redis.get(KEY_PREFIX_ITEM + id), ItemVO.class);
        if (itemVO == null) {
            itemVO = ItemVO.of(itemService.detail(id));
            Redis.setex(KEY_PREFIX_ITEM + id, JSON.write(itemVO), 60 * 30);
        }
        return Response.success(itemVO);
    }

    @GetMapping("/list")
    public Response<List<ItemVO>> list() {
        return Response.success(ItemVO.of(itemService.list()));
    }

    @PostMapping("/create")
    public Response<ItemVO> create(@Validated @RequestBody ItemParams params) {
        return Response.success(ItemVO.of(itemService.createItem(BeanConverter.convert(params, new ItemModel()))));
    }
}
