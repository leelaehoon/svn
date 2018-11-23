package kr.or.ddit.web.calculate;

public enum Operator {

	
	ADD("+", (a, b) -> {return a + b;}),
	MINUS("-", (a, b) -> {return a - b;}), 
	MULTIPLY("*", (a, b) -> {return a * b;}), 
	DIVIDE("/", (a, b) -> {return a / b;});
	
	private String sign;
	private RealOperator realOperator;
	
	Operator(String sign, RealOperator realOperator) {
		this.sign = sign;
		this.realOperator = realOperator;
	}
	
	public String getSign() {
		return this.sign;
	}
	public int operate(int leftOp, int rightOp) {
		return realOperator.operate(leftOp, rightOp);
	}
	
}
