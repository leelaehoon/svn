package kr.or.ddit.validator.rules;

import java.lang.annotation.Annotation;

/**
 * 각 검증 어노테이션을 필드에 사용했을 때 실제 검증을 수행할 Validator들의 상위
 * 
 */
public abstract class EachValidator<T extends Annotation> {
	/**
	 * 현재 Rule(Constraint)이 특정 필드 타입을 대상으로 검증을 수행할 수 있는지 여부
	 * @param targetType : 검증 대상 필드 타입
	 * @return
	 */
	protected abstract boolean isSupported(Class<?> targetType);
	/**
	 * 각 필드에 대해 검증을 수행하기 위해 호출할 메서드
	 * @param target 필드 값
	 * @param targetType 필드 타입
	 * @param rule 필드에 설정된 검증 어노테이션
	 * @param message call by reference방식으로 전달할 검증 결과 메시지 객체
	 * @return
	 */
	public boolean validate(Object target, Class targetType, T rule, StringBuffer message) {
		boolean valid = false;
		if (!isSupported(targetType)) {
			throw new NotSupportedFieldTypeAtRule(targetType, rule);
		}
		valid = validateTarget(targetType, rule, message);
		return valid;
	}
	/**
	 * 각 필드에 대해 검증 룰(제약조건)에 따라 실제 검증을 수행할 메서드 <br />
	 * Template Method Pattern Hook Method의 역할을 수행함.
	 * @param target
	 * @param rule
	 * @param message
	 * @return
	 */
	protected abstract boolean validateTarget(Object target, T rule, StringBuffer message);
	/**
	 * 비즈니스 로직에 따라 검증 대상이 달라져야 할 때, 그룹별 검증을 수행하기 위해 그룹 매칭 여부를 확인할 메서드 <br />
	 * 실제 매칭 여부는 groupMatching(Class[] groups, Class[] groupsAtRule)에서 확인함
	 * @param groups 비즈니스 로직에 따라 설정된 그룹
	 * @param rule
	 * @return
	 */
	public abstract boolean groupMatching(Class[] groups, T rule);
	/**
	 * 비즈니스 로직에 따라 설정된 검증 그룹과 검증 어노테이션에 설저된 그룹의 매칭 여부 판단
	 * @param groups 비즈니스 로직에 따라 설정된 그룹
	 * @param groupsAtRule 검증 어노테이션에 설정된 그룹
	 * @return
	 */
	protected boolean groupMatching(Class[] groups, Class[] groupsAtRule) {
		boolean match = false;
		for (Class group : groups) {
			for (Class groupAtRule : groupsAtRule) {
				match = group.isAssignableFrom(groupAtRule);
				if (match) break;
			}
			if (match) break;
		}
		return match;
	}
}
