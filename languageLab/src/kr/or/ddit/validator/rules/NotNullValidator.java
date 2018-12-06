package kr.or.ddit.validator.rules;

import kr.or.ddit.validator.rules.constraints.NotNull;

/**
 * 모든 객체 형태의 필드에 대해 Null여부를 판단할 validator
 * 
 */
public class NotNullValidator extends EachValidator<NotNull> {

	@Override
	protected boolean isSupported(Class<?> targetType) {
		return Object.class.isAssignableFrom(targetType);
	}

	@Override
	protected boolean validateTarget(Object target, NotNull rule, StringBuffer message) {
		boolean valid = target!=null;
		if (!valid) message.append(rule.message());
		return valid;
	}

	@Override
	public boolean groupMatching(Class[] groups, NotNull rule) {
		return groupMatching(groups, rule.groups());
	}

}
