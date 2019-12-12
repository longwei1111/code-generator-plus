package com.lw.codegenerator.core.universal;

import com.lw.codegenerator.core.ret.RetResult;

/**
 * @Classname MyFacade
 * @Description Facade层基础接口，其他Facade接口继承该接口
 * @Date 2019/12/11 14:09
 * @Author lw
 */
public interface MyFacade<T>{

    /**
     * 新增
     *
     * @param model
     * @return RetResult<Integer>
     */
    public RetResult<Integer> insert(T model);

    /**
     * 修改
     *
     * @param model
     * @return RetResult<Integer>
     */
    public RetResult<Integer> update(T model);

    /**
     * 删除
     *
     * @param id
     * @return RetResult<Integer>
     */
    public RetResult<Integer> deleteById(String id);

    /**
     * 查询
     *
     * @param id
     * @return RetResult<BankcardAgreement>
     */
    public RetResult<T> selectById(String id);

    /**
     * 查询条数
     *
     * @param model
     * @return RetResult<Integer>
     */
    public RetResult<Integer> selectCount(T model);
}
