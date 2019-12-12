package com.lw.codegenerator.client;

import com.google.common.base.CaseFormat;
import com.lw.codegenerator.core.constant.CommonConstant;
import freemarker.template.TemplateExceptionHandler;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname CodeGenerator
 * @Description 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller等，简化开发
 * @date 2019/12/10 17:57
 * @author lw
 */
public class CodeGeneratorClient {

    // JDBC配置
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lw_test?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    // 模板位置
    private static final String TEMPLATE_FILE_PATH = "src/main/resources/template";
    // java文件路径
    private static final String JAVA_PATH = "src/main/java";
    // 资源文件路径
    private static final String RESOURCES_PATH = "src/main/resources";

    // 生成的Facade存放路径
    private static final String PACKAGE_PATH_FACADE = packageConvertPath(CommonConstant.FACADE_PACKAGE);
    // 生成的Facade实现存放路径
    private static final String PACKAGE_PATH_FACADE_IMPL = packageConvertPath(CommonConstant.FACADE_IMPL_PACKAGE);
    // 生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(CommonConstant.SERVICE_PACKAGE);
    // 生成的Service实现存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(CommonConstant.SERVICE_IMPL_PACKAGE);
    // 生成的Controller存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CommonConstant.CONTROLLER_PACKAGE);

    // @author
    private static final String AUTHOR = "lw";
    // @date
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());

    /**
     * genCode("输入表名")
     * @param args
     */
    public static void main(String[] args) {
        genCode("c_bankcard_agreement");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 支持多表代码生成，如输入表名称 "bankcard_agreement" 将生成：
     *      CBankcardAgreement、CBankcardAgreementMapper、CBankcardAgreementMapper.xml
     *      CBankcardAgreementService、CBankcardAgreementServiceImpl、CBankcardAgreementController
     *      CBankcardAgreementFacade、CBankcardAgreementFacadeImpl
     *
     * @author lw
     * @param tableNames 数据表名称...
     * @date 2019/12/10 20:36
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCode(tableName);
        }
    }

    /**
     * 通过数据表名称生成代码
     *
     * @param tableName 数据表名称
     */
    public static void genCode(String tableName) {
        // 生成Model和Mapper
        genModelAndMapper(tableName);
        // 生成Facade和FacadeImpl
        genFacade(tableName);
        // 生成Service和ServiceImpl
        genService(tableName);
        // 生成Controller
        genController(tableName);
    }

    /**
     * 生成model和mapper
     *
     * @param tableName:表名
     */
    public static void genModelAndMapper(String tableName) {
        Context context = getContext();

        // JDBC连接配置
        JDBCConnectionConfiguration jdbcConnectionConfiguration = getJDBCConnectionConfiguration();
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        // 插件配置
        PluginConfiguration pluginConfiguration = getPluginConfiguration();
        context.addPluginConfiguration(pluginConfiguration);
        //  java模型生成器配置
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = getJavaModelGeneratorConfiguration();
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        // sql map生成器配置
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = getSqlMapGeneratorConfiguration();
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        // java客户端生成器配置
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = getJavaClientGeneratorConfiguration();
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        // 表相关配置
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(null);
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings = null;
        MyBatisGenerator generator = null;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }

        String modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    /**
     * 生成Facade
     *
     * @param tableName:表名
     */
    public static void genFacade(String tableName){
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", CommonConstant.BASE_PACKAGE);
            data.put("basePackageFacade", CommonConstant.FACADE_PACKAGE);
            data.put("basePackageFacadeImpl", CommonConstant.FACADE_IMPL_PACKAGE);
            data.put("basePackageModel", CommonConstant.MODEL_PACKAGE);
            data.put("basePackageService", CommonConstant.SERVICE_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_FACADE + modelNameUpperCamel + "Facade.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("facade.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Facade.java 生成成功");

            File file1 = new File(JAVA_PATH + PACKAGE_PATH_FACADE_IMPL + modelNameUpperCamel + "FacadeImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("facade-impl.ftl").process(data, new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "FacadeImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Facade失败", e);
        }
    }

    /**
     * 生成service
     *
     * @param tableName:表名
     */
    public static void genService(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", CommonConstant.BASE_PACKAGE);
            data.put("basePackageService", CommonConstant.SERVICE_PACKAGE);
            data.put("basePackageServiceImpl", CommonConstant.SERVICE_IMPL_PACKAGE);
            data.put("basePackageModel", CommonConstant.MODEL_PACKAGE);
            data.put("basePackageDao", CommonConstant.MAPPER_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    /**
     * 生成controller
     *
     * @param tableName:表名
     */
    public static void genController(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("baseRequestMapping", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", CommonConstant.BASE_PACKAGE);
            data.put("basePackageController", CommonConstant.CONTROLLER_PACKAGE);
            data.put("basePackageService", CommonConstant.SERVICE_PACKAGE);
            data.put("basePackageModel", CommonConstant.MODEL_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    private static Context getContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("mysql");
        context.setTargetRuntime("MyBatis3Simple");
        // 指明数据库的用于标记数据库对象名的符号,MYSQL默认是反引号(``)
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        return context;
    }

    private static JDBCConnectionConfiguration getJDBCConnectionConfiguration() {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        return jdbcConnectionConfiguration;
    }

    private static PluginConfiguration getPluginConfiguration() {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", CommonConstant.MAPPER_INTERFACE_REFERENCE);
        return pluginConfiguration;
    }

    private static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(CommonConstant.MODEL_PACKAGE);
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        return javaModelGeneratorConfiguration;
    }

    private static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
        return sqlMapGeneratorConfiguration;
    }

    private static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(CommonConstant.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "true");
        return javaClientGeneratorConfiguration;
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}