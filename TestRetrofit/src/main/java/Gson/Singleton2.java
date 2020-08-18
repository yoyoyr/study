package Gson;

public class Singleton2 {
 
	private static Singleton2 ton = new Singleton2();
	
	private Singleton2(){
		System.out.println("对象创建成功");
	}
	
	public static Singleton2 getInstance(){
		return ton;		
	}
}