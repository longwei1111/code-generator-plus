package com.lw.codegenerator.core.constant;

/**
 * @Classname CommonConstant
 * @Description 公共常量类
 * @Date 2019/12/10 17:57
 * @Author lw
 */
public class CommonConstant {

	// 项目基础包名称
	public static final String BASE_PACKAGE = "com.lw.codegenerator";

	// Model所在包
	public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";

	// Mapper所在包
	public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";

    // Facade所在包
    public static final String FACADE_PACKAGE = BASE_PACKAGE + ".facade";

    // FacadeImpl所在包
    public static final String FACADE_IMPL_PACKAGE = FACADE_PACKAGE + ".impl";

	// Service所在包
	public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";

	// ServiceImpl所在包
	public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";

	// Controller所在包
	public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";

	// Mapper插件基础接口的完全限定名
	public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.universal.MyMapper";

}
