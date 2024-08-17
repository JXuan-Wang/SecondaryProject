package org.example.systemserver.commons;

import java.lang.annotation.*;

/**
 * 数据范围注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    // 要在sql语句的末尾添加一个过滤条件

    /**
     * 表的别名
     */
    String tableAlias() default "";

    /**
     * 表的字段名
     */
    String tableField() default "";
}
