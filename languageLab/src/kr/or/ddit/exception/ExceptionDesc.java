package kr.or.ddit.exception;

/**
 * 예외 : 프로그램 내에서 발생하는 모든 비정상 상황
 * Throwable - Error     : 시스템 문제로 인해 발생한 상황으로 직접 처리하지 않음 
 * 			 - Exception(checked exception) : 경우에 따라 직접 처리할 수도 있는 상황
 * 					- RuntimeException(unchecked exception) : 직접 처리할 수도 있지만 명시적으로 처리하지 않는 경우, JVM이 제어권을 획득함
 * 
 * 1) checked exception   : IOException, SQLException, ServletException
 * 						  : 발생가능성이 있는 코드에서 예외를 처리하지 않을 경우, 컴파일 에러
 * 2) unchecked exception : NullPoionterException, IllegalArgumentException, ArithmeticException, ArrayIndexOutofBoundException
 * 						  : 묵시적으로 throws가 추가되어 있는 상태
 * ** 예외 처리 방법
 * 1) try~catch~finally : 예외 발생시 직접 처리
 * 2) throws            : 예외 발생시 호출자에게 위임 
 */
public class ExceptionDesc {
	public static void main(String[] args) {
//		try {
			String result = test();
			System.out.println(result);
//		} catch (Exception e) {
//			System.err.println(e.getClass().getSimpleName());
//		}
	}
	
	public static String test() {
//		try {
			if (1==1) {
				throw new CustomException("강제 발생 예외");
			}
			return "테스트";
//		} catch (IOException e) {
//			System.err.println(e.getMessage());
//			throw new RuntimeException(e);
//		}
	}
}