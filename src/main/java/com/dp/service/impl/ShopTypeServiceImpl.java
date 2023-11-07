package com.dp.service.impl;



import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.dp.dto.Result;
import com.dp.entity.ShopType;
import com.dp.mapper.ShopTypeMapper;
import com.dp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dp.utils.RedisConstants;
import jodd.util.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    public StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryTypeList() {
        
        // 从redis中获取商铺类型缓存
        String key = RedisConstants.CACHE_SHOP_KEY + "typeList";
        String jsonShopType = stringRedisTemplate.opsForValue().get(key);
        if (StringUtil.isNotBlank(jsonShopType)) {
            List<ShopType> shopTypes = JSONUtil.toList(jsonShopType, ShopType.class);
            return Result.ok(shopTypes);
        }
        List<ShopType> shopTypes = query().orderByAsc("sort").list();
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(shopTypes));
        return Result.ok(shopTypes);
    }
}
