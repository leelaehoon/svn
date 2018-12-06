package kr.or.ddit.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
	
	public EachValidator addValidator(Class<? extends Annotation> ruleType, Class<? extends EachValidator> validatorType) {
		try {
			return validators.put(ruleType, validatorType.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
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
				System.out.println("검사" +field.getName());
				try {
					valid = valid && validateField(target, field, errors, groups);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
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
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private boolean validateField(Object commandObject, Field field, Map<String, List<CharSequence>> errors, Class...groups) throws IllegalArgumentException, IllegalAccessException {
		boolean valid = true;
		Annotation[] annotations = field.getAnnotations();
		System.out.println(field.getName());
		System.out.println("어노테이션 갯수" +annotations.length);
		if (annotations != null && annotations.length > 0) {
			for (Annotation annotation : annotations) {
				EachValidator validator = validators.get(annotation.annotationType());
				if (validator == null) continue;
				System.out.println("몇번");
				StringBuffer message = new StringBuffer();
				field.setAccessible(true); // modifier를 강제로 public으로 변경
				Object target = field.get(commandObject);
				if (!validator.groupMatching(groups, annotation)) continue;
				valid = valid && validator.validate(target, field.getType(), annotation, message);
				if (!valid) {
					List<CharSequence> messageList = errors.get(field.getName());
					if (messageList == null ) {
						messageList = new ArrayList<>();
						errors.put(field.getName(), messageList);
					}
					messageList.add(message);
				}
			}
		}
		return valid;
	}
}