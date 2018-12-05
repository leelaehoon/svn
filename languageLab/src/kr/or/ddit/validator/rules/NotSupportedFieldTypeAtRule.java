package kr.or.ddit.validator.rules;

import java.lang.annotation.Annotation;

public class NotSupportedFieldTypeAtRule extends RuntimeException {
	
	public NotSupportedFieldTypeAtRule(Class targetType, Annotation rule) {
		super(
			String.format("%s 타입은 %s 룰의 검증 대상이 될 수 없음.", targetType.getSimpleName(), rule.annotationType().getSimpleName())
			);
	}
}
