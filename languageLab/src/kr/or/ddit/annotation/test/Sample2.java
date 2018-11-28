package kr.or.ddit.annotation.test;

import kr.or.ddit.annotation.FirstAnnotation;
import kr.or.ddit.annotation.SecondAnnotation;

@FirstAnnotation(value="sample2", length=5)
public class Sample2 {
	@SecondAnnotation
	private String test1;
	private String test2;
	
	public String getTest1() {
		return test1;
	}
	public void setTest1(String test1) {
		this.test1 = test1;
	}
	public String getTest2() {
		return test2;
	}
	public void setTest2(String test2) {
		this.test2 = test2;
	}
}
