package kr.or.ddit.validator.rules;

import kr.or.ddit.validator.rules.constraints.NotBlank;

public class NotBlankValidator extends EachValidator<NotBlank> {

	@Override
	protected boolean isSupported(Class<?> targetType) {
		return String.class.isAssignableFrom(targetType);
	}

	@Override
	protected boolean validateTarget(Object target, NotBlank rule, StringBuffer message) {
		boolean valid = target != null && target.toString().trim().length() > 0;
		if (!valid) {
			message.append(rule.message());
		}
		return valid;
	}

	@Override
	public boolean groupMatching(Class[] groups, NotBlank rule) {
		return groupMatching(groups, rule.groups());
	}
}
