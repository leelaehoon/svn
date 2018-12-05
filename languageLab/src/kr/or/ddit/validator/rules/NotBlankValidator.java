package kr.or.ddit.validator.rules;

import kr.or.ddit.validator.rules.constraints.NotBlank;

public class NotBlankValidator extends EachValidator<NotBlank> {

	@Override
	protected boolean isSupported(Class<?> targetType) {
		return false;
	}

	@Override
	protected boolean validateTarget(Object target, NotBlank rule, StringBuffer message) {
		return false;
	}

	@Override
	public boolean groupMatching(Class[] groups, NotBlank rule) {
		return false;
	}


}
