package tta.pro;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoginTest {

	private Login log;
	
	@Before
	public void setUp() throws Exception {
		MainFrame fm = new MainFrame();
		log = new Login(fm);
	}

	@Test
	public void testCheckedLogin1() {	// ���� �α��� Ȯ��
		String result = log.checkedLogin("tta", "tta1234");	
		assertEquals("sucess", result);
	}
	

}


