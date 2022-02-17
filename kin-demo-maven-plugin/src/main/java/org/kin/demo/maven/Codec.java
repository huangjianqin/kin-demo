package org.kin.demo.maven;

import java.lang.annotation.*;

/**
 * @author huangjianqin
 * @date 2022/2/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Codec {
}
