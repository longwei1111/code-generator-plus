package com.lw.codegenerator.core.universal.base;

import java.util.List;

/**
 * @Classname MyBaseMapper<T>
 * @Description 自定义Mapper层接口
 * @Date 2019/12/10 17:57
 * @Author lw
 */
public interface MyBaseMapper<T> {

    /**
     * @Description: 新增
     *
     * @param record
     * @Reutrn Integer
     */
    public Integer insertOne(T record);

    /**
     * @Description: 更新
     *
     * @param record
     * @Reutrn Integer
     */
    public Integer update(T record);

    /**
     * @Description: 通过主鍵刪除
     *
     * @param id
     * @Reutrn Integer
     */
    public Integer deleteById(String id);

    /**
     * @Description: 通过ID查找
     *
     * @param id
     * @Reutrn T
     */
    public T selectById(String id);

    /**
     * @Description: 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return List<T>
     */
    public List<T> selectList(T record);

    /**
     * @Description: 根据实体中的属性值进行条数查询
     *
     * @param record
     * @return Integer
     */
    public Integer selectCount(T record);

}
