package com.lw.codegenerator.core.universal;

import java.util.List;

/**
 * @Classname Service<T>
 * @Description Service层基础接口，其他Service接口继承该接口
 * @Date 2019/12/10 17:57
 * @Author lw
 */
public interface MyService<T> {

    /**
     * @Description: 新增
     *
     * @param model
     * @Reutrn Integer
     */
    public Integer insertOne(T model);

    /**
     * @Description: 更新
     *
     * @param model
     * @Reutrn Integer
     */
    public Integer update(T model);

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
