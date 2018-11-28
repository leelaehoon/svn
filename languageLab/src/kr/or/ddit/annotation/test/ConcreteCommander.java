package kr.or.ddit.annotation.test;

import kr.or.ddit.annotation.FirstAnnotation;

@FirstAnnotation("concreteCommandar")
public class ConcreteCommander implements ICommander {
	
	@Override
	public void command() {

	}

}
