package kr.or.ddit.validator.rules.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.or.ddit.validator.DefaultGroup;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
	public String message() default "Null값을 허용하지 않는 필드임.";
	public Class[] groups() default {DefaultGroup.class};
}
