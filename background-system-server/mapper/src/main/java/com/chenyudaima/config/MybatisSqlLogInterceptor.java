package com.chenyudaima.config;


import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.util.*;

/**
 * 用于监控Sql执行时间作为日志
 * 可以使用自定义注解指定监控mapper的日志
 * 不能捕获Sql执行异常
 */

@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
})
public class MybatisSqlLogInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(MybatisSqlLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //com.chenyudaima.mapper.UserMapper.insertUser
        String id = mappedStatement.getId();

        //SELECT、DELETE、INSERT、UPDATE
        String sqlType = mappedStatement.getSqlCommandType().name();

        BoundSql boundSql = statementHandler.getBoundSql();

        //sql语句
        String sql = boundSql.getSql();

        //参数
        //Map map = (MapperMethod.ParamMap) boundSql.getParameterObject();

        long time = System.currentTimeMillis();

        Object proceed = invocation.proceed();

        log.debug("\nSQL执行日志\nclassName: {}\ntype: {}\nsql: {}\ntime: {}毫秒", id, sqlType, sql, System.currentTimeMillis() - time);

        return proceed;
    }

    /**
     * 让mybatis判断，是否要进行拦截，然后做出决定是否生成一个代理
     *
     * 每经过一个拦截器对象都会调用插件的plugin方法，也就是说，该方法会调用4次。根据@Intercepts注解来决定是否进行拦截处理
     *
     * 判断是否拦截这个类型对象（根据@Intercepts注解决定），然后决定是返回一个代理对象还是返回原对象
     *
     * 故我们在实现plugin方法时，要判断一下目标类型，是本插件要拦截的对象时才执行Plugin.wrap方法，否则的话，直接返回目标本身
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {}
}
