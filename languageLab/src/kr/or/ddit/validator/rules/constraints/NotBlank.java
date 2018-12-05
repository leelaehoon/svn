package kr.or.ddit.validator.rules.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.or.ddit.validator.DefaultGroup;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
	public String message() default "문자열 필드가 비어있음.";
	public Class[] groups() default {DefaultGroup.class};
}
