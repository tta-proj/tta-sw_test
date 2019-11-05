package tta.pro;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JPanel{
	private JTextField id = new JTextField(20);
	private JPasswordField pw= new JPasswordField("",15);
	private MainFrame win;
	private String SavedID;
	
	public String Login(String ID, String PW) {
		if(ID.isEmpty())
			return ("id ���Է�");
		if(!ID.matches("[0-9|a-z|A-Z|��-��|��-��|��-��]*"))
			return ("Ư������ ����");
		else if(ID.equals("tta") && PW.equals("tta123"))
			return ("�α��� ����");
		else 
			return("���Է�");
	}
		
	public Login(MainFrame win) {
		this.win = win;
		this.setBackground(Color.WHITE);
		setLayout(null);
		Font default_font = new Font("���� ���",Font.BOLD,20);
		Color default_color = new Color(0,32,96);		
		
		JLabel Title_label = new JLabel("To do List");
		Title_label.setFont(new Font("HY�߰��",Font.BOLD,30));
		Title_label.setForeground(default_color);
		
		JLabel ID_label= new JLabel("���̵�");
		ID_label.setFont(default_font);
		ID_label.setForeground(default_color);
		
		JLabel PW_label= new JLabel("��й�ȣ");
		PW_label.setFont(default_font);
		PW_label.setForeground(default_color);
		
		JButton btn1 = new JButton("�α���");
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
	}
		
	public String getID() {
		return SavedID;
	}
    
	class loginAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String ID = id.getText();
			String PW = new String(pw.getPassword());

			String log = Login(ID, PW);
			
			if(log.contentEquals("id ���Է�"))
				JOptionPane.showMessageDialog(null , "id�� �Է����ּ���");
			else if(log.contentEquals("Ư������ ����"))
				JOptionPane.showMessageDialog(null , "Ư�����ڴ� �Է� �Ұ����մϴ�.");
			else if(log.contentEquals("�α��� ����")) {
				SavedID = "tta";
				win.change("Mainpage");
			}
			else 
				JOptionPane.showMessageDialog(null , "���̵� �� ��й�ȣ�� Ʋ�Ƚ��ϴ�.");			
		}
	}
	
}


