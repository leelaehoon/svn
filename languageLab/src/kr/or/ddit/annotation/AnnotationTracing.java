package kr.or.ddit.annotation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AnnotationTracing {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<Class> clzList = getClassesWithAnnotation("kr.or.ddit.annotation.test", FirstAnnotation.class);
		
		if (clzList!=null) {
			for (Class clz : clzList) {
				FirstAnnotation annotation = (FirstAnnotation) clz.getAnnotation(FirstAnnotation.class);
				System.out.printf("value:%s, length:%d\n",annotation.value(), annotation.length());
				List<Field> fldList = getFieldsWithAnnotation(clz, SecondAnnotation.class);
				if (fldList!=null) {
					for (Field fld : fldList) {
						SecondAnnotation annotation2 = fld.getAnnotation(SecondAnnotation.class);
						System.out.println(fld);
					}
				}
			}
		}
		

		
	}
	public static List<Field> getFieldsWithAnnotation(Class clz, Class annotationType) {
		Field[] fields = clz.getDeclaredFields();
		List<Field> fldList = new ArrayList<>();
		if (fields!=null) {
			for (Field fld : fields) {
				Annotation annotation = fld.getAnnotation(annotationType);
				if (annotation!=null) {
					fldList.add(fld);
				}
			}
		}
		return fldList;
	}
	
	public static List<Class> getClassesWithAnnotation(String base, Class annotationType) {
		URL baseURL = AnnotationTracing.class.getResource("/" + base.replace(".", "/"));
		File baseFolder = new File(baseURL.getFile());
		String[] classList = baseFolder.list((dir, name)->{
			boolean flag = false;
			flag = name.endsWith(".class");
			return flag;
		});
		
		List<Class> clzList = new ArrayList<>();
		if (classList!=null) {
			for (String classFileName : classList) {
				int lastIndex = classFileName.lastIndexOf(".class");
				String qualifiedName = base + "." + classFileName.substring(0, lastIndex);
				try {
					Class clz = Class.forName(qualifiedName); // class loading
					Annotation annotation = clz.getAnnotation(annotationType);
					if (annotation!=null) {
						clzList.add(clz);
					}
				} catch (ClassNotFoundException e) {
					continue;
				} 
			}
		}
		return clzList;
	}
}
