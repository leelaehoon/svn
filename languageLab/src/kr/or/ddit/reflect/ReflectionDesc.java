package kr.or.ddit.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Reflection : 객체에 대한 구체적인 정보가 없는 상태에서 해당 객체의 타입이나 속성, 행동에 대한 정보를 추측해 가는 과정
 * 		        JAVA에서는 java.lang.reflect 패키지에서 해당 API들이 제공됨
 * 
 */
public class ReflectionDesc {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Object retValue = ReflectionTest.getObject();
		Class type = retValue.getClass();
		System.out.println(type);
		Field[] fields = type.getDeclaredFields();
		if (fields!=null) {
			for (Field tmp : fields) {
				String fldName = tmp.getName();
//				String getterName = "get" + (fldName.charAt(0)+"").toUpperCase()+fldName.substring(1);
				try {
//					Method getter = type.getDeclaredMethod(getterName);
					PropertyDescriptor pd = new PropertyDescriptor(fldName, type);
					Method getter = pd.getReadMethod();
					Object getterValue = getter.invoke(retValue);
					Class fldType = tmp.getType();
					
					System.out.printf("%s %s = %s\n", fldType.toString(), fldName, Objects.toString(getterValue));
				} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
