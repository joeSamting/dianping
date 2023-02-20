package com.hmdp.service.impl;



import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import jodd.util.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    public StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryTypeList() {
        
        // 从redis中获取商铺类型缓存
        String key = RedisConstants.CACHE_SHOP_KEY + "list";
        String jsonShopType = stringRedisTemplate.opsForValue().get(key);
        if (StringUtil.isNotBlank(jsonShopType)) {
            List<Shop> shops = JSONUtil.toList(jsonShopType, Shop.class);
            return Result.ok(shops);
        }
        List<ShopType> shops = query().orderByAsc("sort").list();
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(shops));
        return Result.ok(shops);
    }
}
