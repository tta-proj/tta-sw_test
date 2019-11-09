package tta.pro;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tta.base.User;

public class Login extends JPanel{
	private JTextField id = new JTextField(20);
	private JPasswordField pw= new JPasswordField("",15);
	private MainFrame win;
	private String SavedID;
	
	User user = new User();
	Hashtable<String, String> staff;
	

	public String checkedLogin(String s_id, String s_pw)
	{
		
		staff = user.getStaff();
		
		while(true)
		{
			if(staff.containsKey(s_id))
			{
				if(staff.get(s_id).equals(s_pw))
				{
					return("로그인 성공");
				}
				else
					if(s_id.length() !=0 && s_pw.length() ==0)
						return("미입력");	
					else
						return("재입력");
			}
			else
			{
				if(s_id.length() ==0)
					return("미입력");		
				else if (!s_id.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*"))
					return("특수문자 금지");
				else
					return("재입력");
			}
		}
	}
	
	/*
	public String Login(String ID, String PW) {
		if(ID.isEmpty())
			return ("id 미입력");
		if(!ID.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*"))
			return ("특수문자 금지");
		else if(ID.equals("tta") && PW.equals("tta123"))
			return ("로그인 성공");
		else 
			return("재입력");
	}
		*/
	public Login(MainFrame win) {
		this.win = win;
		this.setBackground(Color.WHITE);
		setLayout(null);
		Font default_font = new Font("맑은 고딕",Font.BOLD,20);
		Color default_color = new Color(0,32,96);		
		
		JLabel Title_label = new JLabel("To do List");
		Title_label.setFont(new Font("HY견고딕",Font.BOLD,30));
		Title_label.setForeground(default_color);
		
		JLabel ID_label= new JLabel("아이디");
		ID_label.setFont(default_font);
		ID_label.setForeground(default_color);
		
		JLabel PW_label= new JLabel("비밀번호");
		PW_label.setFont(default_font);
		PW_label.setForeground(default_color);
		
		JButton btn1 = new JButton("로그인");
		btn1.setFont(default_font);
		btn1.setBackground(default_color);
		btn1.setForeground(new Color(255,255,255));
		btn1.addActionListener(new loginAction());

		Title_label.setBounds(190,90,500,200);
		ID_label.setBounds(140, 170, 200, 200);
		PW_label.setBounds(130, 250, 200, 200);
		id.setBounds(220,255,240,40);
		pw.setBounds(220,335,240,40);
		btn1.setBounds(280,430,100,40);
		
		add(PW_label);
		add(id);
		add(ID_label);
		add(pw);
		add(Title_label);
		add(btn1);

		setBounds(0,0,650,750);
		
		user.ReadStaff();
	}
		
	public String getID() {
		return SavedID;
	}
	
	
	class loginAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String ID = id.getText();
			String PW = new String(pw.getPassword());

			String logininfo = checkedLogin(ID, PW);
			
			if(logininfo.contentEquals("미입력"))
				JOptionPane.showMessageDialog(null , "아이디, 비밀번호를 입력해주세요");
			else if(logininfo.contentEquals("특수문자 금지"))
				JOptionPane.showMessageDialog(null , "특수문자는 입력 불가능합니다.");
			else if(logininfo.contentEquals("로그인 성공")) {
				SavedID = ID;
				win.change("Mainpage");
			}
			else 
				JOptionPane.showMessageDialog(null , "아이디 및 비밀번호가 틀렸습니다.");			
		}
	}
}


