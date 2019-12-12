package ${basePackageFacadeImpl};

import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageService}.${modelNameUpperCamel}Service;
import ${basePackageFacade}.${modelNameUpperCamel}Facade;
import ${basePackage}.core.universal.AbstractFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @Description: ${modelNameUpperCamel}Facade接口实现类
*
* @author ${author}
* @date ${date}
*/
@Service
public class ${modelNameUpperCamel}FacadeImpl extends AbstractFacade<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Facade {

    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

}