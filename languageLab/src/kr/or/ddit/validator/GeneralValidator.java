package kr.or.ddit.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.validator.rules.EachValidator;
import kr.or.ddit.validator.rules.NotBlankValidator;
import kr.or.ddit.validator.rules.NotNullValidator;
import kr.or.ddit.validator.rules.constraints.NotBlank;
import kr.or.ddit.validator.rules.constraints.NotNull;

public class GeneralValidator {
	private Map<Class<? extends Annotation>, EachValidator<? extends Annotation>> validators;
	{
		validators = new HashMap<>();
		validators.put(NotNull.class, new NotNullValidator());
		validators.put(NotBlank.class, new NotBlankValidator());
	}
	/**
	 * 객체 검증 대상에 대해 검증을 수행할 메서드
	 * @param target 검증 대상 객체 ex) Command Object
	 * @param errors 검증 결과 메시지 객체
	 * @param groups 비즈니스 로직에 따른 검증 그룹 설정 ex) InsertGroup, UpdateGroup
	 * @return
	 */
	public boolean validate(Object target, Map<String, List<CharSequence>> errors, Class...groups) {
		boolean valid = true;
		Class<?> targetType = target.getClass();
		Field[] fields = targetType.getDeclaredFields();
		if (fields!=null) {
			for (Field field : fields) {
				valid = validateField(target, field, errors);
			}
		}
		return valid;
	}
	/**
	 * target 객체가 가진 각 필드에 대해 검증을 수행할 메서드
	 * @param target 검증 대상 객체
	 * @param field 검증 대상 객체가 가진 필드
	 * @param errors 검증 결과 메시지 객체
	 * @return
	 */
	private boolean validateField(Object target, Field field, Map<String, List<CharSequence>> errors) {
		boolean valid = false;
		
		return valid;
	}
}