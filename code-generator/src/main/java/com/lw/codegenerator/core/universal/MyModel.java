package com.lw.codegenerator.core.universal;

import com.alibaba.fastjson.JSON;

/**
 * @Classname Model
 * @Description model,重写toString方法
 * @Date 2019/12/10 17:57
 * @Author lw
 */
public class MyModel {

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+":"+JSON.toJSONString(this)+"\n";
    }

}
