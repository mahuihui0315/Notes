package com.mybatis.demo3;

import java.util.List;

/**
 * order持久化接口
 */
public interface OrderMapper {
    /**
     * 获取order列表:resultType
     * @return
     */
    List<Order> getOrderList();

    /**
     * 获取order列表:resultMap
     * @return
     */
    List<Order> getOrderListMap();

    /**
     * 一对一关联查询：resultType
     * @return
     */
    List<Order> getOrderUser();

    /**
     * 一对一关联查询：resultMap
     * @return
     */
    List<Order> getOrderUserMap();
}
