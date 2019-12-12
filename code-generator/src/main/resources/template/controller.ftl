package ${basePackageController};

import ${basePackage}.core.ret.RetResult;
import ${basePackage}.core.ret.RetResponse;
import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageService}.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description: ${modelNameUpperCamel}Controller类
*
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {

    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/insert")
    public RetResult<Integer> insert(${modelNameUpperCamel} ${modelNameLowerCamel}) throws Exception{
    	Integer count = ${modelNameLowerCamel}Service.insertOne(${modelNameLowerCamel});
        return RetResponse.makeOKRsp(count);
    }

    @PostMapping("/update")
    public RetResult<Integer> update(${modelNameUpperCamel} ${modelNameLowerCamel}) throws Exception {
        Integer count = ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return RetResponse.makeOKRsp(count);
    }

    @PostMapping("/deleteById")
    public RetResult<Integer> deleteById(@RequestParam String id) throws Exception {
        Integer count = ${modelNameLowerCamel}Service.deleteById(id);
        return RetResponse.makeOKRsp(count);
    }

    @PostMapping("/selectById")
    public RetResult<${modelNameUpperCamel}> selectById(@RequestParam String id) throws Exception {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.selectById(id);
        return RetResponse.makeOKRsp(${modelNameLowerCamel});
    }

    @PostMapping("/selectCount")
    public RetResult<Integer> selectCount(${modelNameUpperCamel} ${modelNameLowerCamel}) throws Exception {
        Integer count = ${modelNameLowerCamel}Service.selectCount(${modelNameLowerCamel});
        return RetResponse.makeOKRsp(count);
    }
}