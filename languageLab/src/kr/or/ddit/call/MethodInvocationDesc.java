package kr.or.ddit.call;

/**
 * 메소드 호출시 아규먼트(인자)를 전달하는 방법
 * 1) Call By Value : 인자의 타입이 기본형일때
 * 					: 호출자쪽에서 전달할 값을 복사해서 인자를 넘기는 방식.
 * 2) Call By Reference : 인자의 타입이 객체 참조형일때
 *						: 호출자쪽에서 전달할 객체의 주소를 복사해서 인자를 넘기는 방식. 
 *
 */
public class MethodInvocationDesc {
	public static void main(String[] args) {
		int number = 1;
		callByValueTest(number);
		System.out.println(number);
		StringBuffer sb = new StringBuffer();
		
		callByReferenceTest(sb);
		
		System.out.println(sb);
	}
	
	private static void callByReferenceTest(StringBuffer sb) {
		sb.append("메서드 내에서 추가된 데이터");
		
	}
	
	private static int callByValueTest(int number) {
		for (int cnt = 0; cnt < 10; cnt++) {
			number += 1;
		}
		System.err.println(number);
		return number;
	}
}
