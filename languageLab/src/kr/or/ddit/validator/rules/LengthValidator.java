package kr.or.ddit.validator.rules;

import kr.or.ddit.validator.rules.constraints.Length;

public class LengthValidator extends EachValidator<Length> {

	@Override
	protected boolean isSupported(Class<?> targetType) {
		return CharSequence.class.isAssignableFrom(targetType);
	}

	@Override
	protected boolean validateTarget(Object target, Length rule, StringBuffer message) {
		boolean valid = target != null && target.toString().length() >= rule.min() && target.toString().length() <= rule.max(); 
		if (!valid) message.append(rule.message());
		return valid;
	}

	@Override
	public boolean groupMatching(Class[] groups, Length rule) {
		return groupMatching(groups, rule.groups());
	}
}
