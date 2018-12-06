package kr.or.ddit.validator.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.validator.rules.LengthValidator;
import kr.or.ddit.validator.rules.constraints.Length;
import kr.or.ddit.vo.MemberVO;

public class ValidatorTest {
	public static void main(String[] args) {
		MemberVO member = new MemberVO();
		GeneralValidator validator = new GeneralValidator();
		validator.addValidator(Length.class, LengthValidator.class);
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		boolean valid = validator.validate(member, errors, InsertGroup.class);
		System.out.println(valid);
		System.out.println(errors);
	}
}
