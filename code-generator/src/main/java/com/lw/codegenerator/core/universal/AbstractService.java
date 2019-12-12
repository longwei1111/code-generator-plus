package com.lw.codegenerator.core.universal;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Classname AbstractService<T>
 * @Description Service抽象类
 * @Date 2019/12/10 17:57
 * @Author lw
 */
public abstract class AbstractService<T> implements MyService<T> {

	@Autowired
	protected MyMapper<T> mapper;

	@Override
    public Integer insertOne(T model) {
        return mapper.insertOne(model);
    }

	@Override
	public Integer deleteById(String id) {
		return mapper.deleteById(id);
	}

	@Override
	public Integer update(T model) {
		return mapper.update(model);
	}

	@Override
	public T selectById(String id) {
		return mapper.selectById(id);
	}

	@Override
	public List<T> selectList(T record){
		return mapper.selectList(record);
	}

	@Override
    public Integer selectCount(T record){
	    return mapper.selectCount(record);
    }
}