package com.sparta.commonmodule.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //생성한 어노테이션을 사용할 수 있는곳(적용대상)
@Retention(RetentionPolicy.RUNTIME) //어노테이션 적용 및 유지범위
public @interface RoleCheck {
    String value(); //@RoleCheck("ROLE_MASTER")형식으로 값을 입력할수있게 구현
}
