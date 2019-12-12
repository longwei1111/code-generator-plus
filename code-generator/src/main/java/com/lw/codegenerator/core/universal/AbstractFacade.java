package com.lw.codegenerator.core.universal;

import com.lw.codegenerator.core.ret.RetResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname AbstractFacade<T>
 * @Description Facade抽象类
 * @Date 2019/12/11 15:16
 * @Author lw
 */
public abstract class AbstractFacade<T> implements MyFacade<T> {

    @Autowired
    protected MyService<T> service;

    @Override
    public RetResult<Integer> insert(T model) {
        return new RetResult<Integer>().setData(service.insertOne(model));
    }

    @Override
    public RetResult<Integer> update(T model) {
        return new RetResult<Integer>().setData(service.update(model));
    }

    @Override
    public RetResult<Integer> deleteById(String id) {
        return new RetResult<Integer>().setData(service.deleteById(id));
    }

    @Override
    public RetResult<T> selectById(String id) {
        return new RetResult<T>().setData(service.selectById(id));
    }

    @Override
    public RetResult<Integer> selectCount(T model) {
        return new RetResult<Integer>().setData(service.selectCount(model));
    }
}
