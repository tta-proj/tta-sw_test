package tta.pro;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class LoginTest {
	
	private Login log;

	@Before
	public void Setup() {
		MainFrame fm = new MainFrame();
		log = new Login(fm);	
	}

	@Test //로그인
	public void testLogin() {
		String result = log.Login("tta", "tta123");
		assertEquals("로그인 성공", result);
	}
	

}