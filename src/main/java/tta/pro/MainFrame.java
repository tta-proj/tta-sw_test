package tta.pro;

import tta.base.SortExcel;

import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Add_Change_Panel extends JPanel{

	private static final long serialVersionUID = 1L;

	private JLabel Title_Label;
	
	private JButton Reg_Button;
	private JButton Cancel_Button;
	private JLabel Sub_Label;
	private JLabel Prof_Label;
	
	private JLabel Day_Label;
	private JLabel Time_Label;
	private JLabel Year_Label;
	private JLabel Sem_Label;
	
	private JTextField Sub_Text;
	private JTextField Prof_Text;
	private JTextField Time_Text;
	private JComboBox<String> Day_Combo;
	private JComboBox<String> Year_Combo;
	private JComboBox<String> Sem_Combo;
	
	public MainFrame win;
	
	private Font TitleFont = new Font("HY견고딕",Font.BOLD, 30);
	private Font ButtonFont = new Font("맑은 고딕",Font.BOLD, 20);
	private Font SubFont = new Font("맑은 고딕",Font.BOLD, 15);
	
	private Color MainColor = new Color(0, 32, 96);
	
	private int RowNum;
	public String[] Reg_Info={"", "", "", ""};

	
	private String PreName = null;
	public String PanelName;
	public void showPanel(MainFrame win) {
		this.win=win;
		setLayout(null);
		
		Sub_Label = new JLabel("프로젝트 명 : ");
		Sub_Label.setBounds(100,200,100,40);
		Sub_Label.setFont(SubFont);
		add(Sub_Label);
		
		Sub_Text = new JTextField(20);
		Sub_Text.setBounds(180,200,350,50);
		Sub_Text.setFont(SubFont);
		Sub_Text.setText("");

		add(Sub_Text);
		
		Prof_Label = new JLabel("담당자 : ");
		Prof_Label.setBounds(100,260,100,40);
		Prof_Label.setFont(SubFont);
		add(Prof_Label);
		
		Prof_Text = new JTextField(20);
		Prof_Text.setBounds(180,260,350,50);
		Prof_Text.setFont(SubFont);
		Prof_Text.setText("");

		add(Prof_Text);
	
		Year_Label = new JLabel("년도 : ");
		Year_Label.setBounds(100,460,100,40);
		Year_Label.setFont(SubFont);
		add(Year_Label);
		
		String[] Years = new String[10];
		Integer temp = 2017;
		for(int i=0; i<10;i++) {
			Years[i]=String.valueOf(temp);
			temp++;
		}
		Year_Combo = new JComboBox<String>(Years);

		Year_Combo.setBounds(180,460,140,50);
		Year_Combo.setFont(SubFont);
		add(Year_Combo);
		
		
		Sem_Label = new JLabel("분기 : ");
		Sem_Label.setBounds(350,460,100,40);
		Sem_Label.setFont(SubFont);
		add(Sem_Label);
		
		String[] Sems = {"1분기","2분기","3분기","4분기"}; 
		Sem_Combo = new JComboBox<String>(Sems);

		Sem_Combo.setBounds(400,460,140,50);
		Sem_Combo.setFont(SubFont);
		add(Sem_Combo);		

		//확인 버튼
		Reg_Button = new JButton("확인");
		Reg_Button.setSize(150,50);
		Reg_Button.setLocation(180,600);
		Reg_Button.setFont(ButtonFont);
		Reg_Button.addActionListener(new OkActionListener(Sub_Text, Prof_Text, Year_Combo, Sem_Combo));
		add(Reg_Button);
				
				
		//취소 버튼
		Cancel_Button = new JButton("취소");
		Cancel_Button.setSize(150,50);
		Cancel_Button.setLocation(370,600);
		Cancel_Button.setFont(ButtonFont);
		Cancel_Button.addActionListener(new CancelActionListener());
		add(Cancel_Button);
		setBackground(Color.WHITE);
		setBounds(0,0,650,750);
		
	}
	
	public Add_Change_Panel(MainFrame win, String PanelName) {
		if(PanelName == "Add_Panel") {
			Title_Label = new JLabel("프로젝트 등록");
			Title_Label.setFont(TitleFont);
			Title_Label.setForeground(MainColor);
			Title_Label.setBounds(240,40,400,60);
			add(Title_Label);
			
			showPanel(win);	
		}
	}
	
	public void changeRegText(String panelName) {
		if(panelName == "Add_Panel") {
			this.Reg_Button.setText("확인");
		}
		else if (panelName == "Change_Panel") {
			this.Reg_Button.setText("수정완료");
		}
	}
	
	public void setRowNum(int RowNum) {
		this.RowNum = RowNum;
	}
	
	public void setRegInfo (String[] S_Reg_Info) {
		this.Reg_Info = S_Reg_Info;
	}
	
	public void setPanelName (String PanelName) {
		this.PanelName = PanelName;
	}
	
	public void setPreName(String PreName) {
		this.PreName = PreName; 
	}
	
	public void ChangeTitle(String Title) {
		Title_Label.setText(Title);
	}
	
	public void ChangeMod(String[] Reg_info) {
		Sub_Text.setText(Reg_Info[0]);
		Prof_Text.setText(Reg_Info[1]);
		Year_Combo.setSelectedItem(Reg_Info[2]);
		Sem_Combo.setSelectedItem(Reg_Info[3]);
		
		Sub_Text.repaint();
		Prof_Text.repaint();
		Year_Combo.repaint();
		Sem_Combo.repaint();
	}
	
	//등록버튼 ActionListener
	class OkActionListener extends JFrame implements ActionListener{
		private JTextField Sub_Text;
		private JTextField Prof_Text;
		private JComboBox<String> Year_Combo;
		private JComboBox<String> Sem_Combo;
		boolean addFlag;
		
		public OkActionListener(JTextField Sub_Text, JTextField Prof_Text, JComboBox<String> Year_Combo, JComboBox<String> Sem_Combo){
			this.Sub_Text = Sub_Text;
			this.Prof_Text = Prof_Text;
			this.Year_Combo = Year_Combo;
			this.Sem_Combo = Sem_Combo;
		}
		
		public void actionPerformed(ActionEvent e) {
			Reg_Info[0] = Sub_Text.getText();
			Reg_Info[1] = Prof_Text.getText();
			Reg_Info[2] = Year_Combo.getSelectedItem().toString();
			Reg_Info[3] = Sem_Combo.getSelectedItem().toString();
            UIManager UI =new UIManager();
            Color navy = new Color(0,32,96);
            Font message = new Font("맑은고딕",Font.BOLD,20);
            UI.put("OptionPane.messageForeground", navy);
            UI.put("OptionPane.messageFont", message);
			
			//인자 확인
			for(int i = 0; i < 4; i++) {
				if(Reg_Info[i].equals("")) {
					JOptionPane.showMessageDialog(null , "필수 입력 사항입니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
					addFlag = Boolean.FALSE;
					break;
				}
				addFlag = Boolean.TRUE;
			}
			if(addFlag == Boolean.TRUE) {
				// 엑셀에 추가
				String FilePath = "./Subject_Dir/";			
				String FilePath2 = "./Subject_Dir/Todolist_Dir/";			
				
				// ToDoList 엑셀 생성
				File destFile = new File(FilePath2 + Reg_Info[0] + ".xlsx");
				File OriFile = new File(FilePath2 + PreName + ".xlsx");
				
				//출력
				FileInputStream inputStream;
				FileOutputStream outFile;
				
				try {
					if(PanelName == "Add_Panel") {
						destFile.createNewFile();
						XSSFWorkbook workbook = new XSSFWorkbook();
						Sheet sheet=workbook.createSheet();
						sheet.createRow(0);
						Row row = sheet.createRow(0);
						row.createCell(0).setCellValue("프로젝트");
						row.createCell(1).setCellValue("할 일");
						row.createCell(2).setCellValue("마감 기한");
						row.createCell(3).setCellValue("실제 마감일");
						row.createCell(4).setCellValue("완료 여부");
						row.createCell(5).setCellValue("중요도");
						
						
						try {
							outFile = new FileOutputStream(FilePath2 + Reg_Info[0] + ".xlsx");
							workbook.write(outFile);
							outFile.close();
							workbook.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else if(PanelName == "Change_Panel"){
						OriFile.renameTo(destFile);
					}
					
					inputStream = new FileInputStream(FilePath + "Subject_List.xlsx");
					XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
					Sheet sheet = workbook.getSheetAt(0);
					int rows=0;
					
					
					if(PanelName == "Add_Panel") {
						rows = sheet.getPhysicalNumberOfRows();
					}
					else if(PanelName == "Change_Panel")
						rows = RowNum+1;
					Row row = sheet.createRow(rows);
					row.createCell(0).setCellValue(Reg_Info[0]);
					row.createCell(1).setCellValue(Reg_Info[1]);
					row.createCell(2).setCellValue(Reg_Info[2]);
					row.createCell(3).setCellValue(Reg_Info[3]);
					
					outFile = new FileOutputStream(FilePath + "Subject_List.xlsx");
					workbook.write(outFile);	
					outFile.close();
					workbook.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
				
				Sub_Text.setText("");
				Prof_Text.setText("");
				Year_Combo.setSelectedIndex(0);
				Sem_Combo.setSelectedIndex(0);
				
				
				win.change("Mainpage");
			}
		}
	}
	class CancelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			win.change("Mainpage");
		}
	}
	
	
}


class Mainpage extends JPanel{
	private MainFrame win;
	
	private JLabel Id_Label;
	
	private JButton Add_Button = new JButton("등록");
	private JButton Change_Button = new JButton("수정");
	private JButton Delete_Button = new JButton("삭제");
	private JButton ShowAll_Button = new JButton("전체 To Do List 조회");
	private JButton TrashCan_Button = new JButton();
	private JLabel Title_Label = new JLabel();
	private JScrollPane Subject_Scroll;
	
	private Font TitleFont = new Font("HY견고딕",Font.BOLD, 30);
	private Font ButtonFont = new Font("맑은 고딕",Font.BOLD, 20);
	private Font SubFont = new Font("맑은 고딕",Font.BOLD, 15);
	
	private Color MainColor = new Color(0, 32, 96);
	
	private ImageIcon TrashCan_Icon = new ImageIcon("./TrashCan.png");
	
	//추가 JTable로 구현
	private JTable Subject_Table;
	private final String [] col = {"V", "프로젝트", "담당자", "년도", "분기"};
	private Object [][] datas;
	
	//엑셀 읽기 변수
	private Row row;
	private Cell cell;
	String FilePath = "./Subject_Dir/";
	Object ReadData[] = new Object[5];

	DefaultTableModel model;
	
	@SuppressWarnings("serial")
	public Mainpage(MainFrame win) {
		this.win=win;
		setLayout(null);
		
		model = new DefaultTableModel(datas, col);
		//테이블
		Subject_Table = new JTable(model) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
					case 0:
						return Boolean.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return String.class;
					case 4:
						return String.class;
					default:
						return Boolean.class;
				}
			}
		};

		JTableHeader header = Subject_Table.getTableHeader();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)header.getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		header.setDefaultRenderer(renderer);
		
		header.setBackground(MainColor);
		header.setForeground(Color.WHITE);
		header.setFont(SubFont);
		
		//컬럼 크기, 이동변경 불가
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		
		Subject_Table.addMouseListener(new SelectSubjectMouseListener());
		
		//ID
		Id_Label = new JLabel();
		Id_Label.setFont(SubFont);
		Id_Label.setBounds(25,100,150,40);
		add(Id_Label);
		
		// 제목
		Title_Label = new JLabel("프로젝트 LIST");
		Title_Label.setFont(TitleFont);
		Title_Label.setForeground(MainColor);
		Title_Label.setBounds(210,40,400,60);
		add(Title_Label);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		
		// 버튼
		Add_Button.addActionListener(new RegActionListener());
		Change_Button.addActionListener(new ChangeActionListener());	 
		Delete_Button.addActionListener(new DeleteActionListener());
		
		Add_Button.setFont(ButtonFont);
		Change_Button.setFont(ButtonFont);
		Delete_Button.setFont(ButtonFont);
		ShowAll_Button.setFont(ButtonFont);
		
		
		p1.add(Add_Button);
		p1.add(Change_Button);
		p1.add(Delete_Button);
		p1.setBackground(Color.WHITE);
		p1.setBounds(357,100,270,50);
		add(p1);
		
				
		//스크롤 추가 
		Subject_Scroll = new JScrollPane(Subject_Table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Subject_Scroll.setBounds(25,150,600,400);
		Subject_Scroll.getVerticalScrollBar().setVisible(true);
		Subject_Scroll.getHorizontalScrollBar().setVisible(true);
		
		add(Subject_Scroll);
		
				
		ShowAll_Button.setBounds(25, 600, 220, 50);
		ShowAll_Button.addActionListener(new ShowAllActionListener());

		add(ShowAll_Button);

		setBackground(Color.WHITE);
		
	}

	
	public void RefreshSubjectTable() {
		// 엑셀로부터 정보 읽기
		try {
			DefaultTableModel model = new DefaultTableModel(datas, col) {;
				@Override
				public boolean isCellEditable(int row, int column) {
					if(column == 0) {
						return true;
					}
					return false;
				}
			};
			Subject_Table.setModel(model);
			
			FileInputStream inputStream = new FileInputStream(FilePath + "Subject_List.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();					//row for
			for(int i=1 ; i<rows; i++) {
				row=sheet.getRow(i);
				if(row != null) {
					// cell for
					ReadData[0]=Boolean.FALSE;  // 체크박스
					for(int j=0;j<cells;j++) {
						cell = row.getCell(j);
						if(cell !=null) {
							String value = null;
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_FORMULA:
								value = cell.getCellFormula();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								value = "" + cell.getNumericCellValue();
								break;
							case Cell.CELL_TYPE_STRING:
								value = "" + cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_BLANK:
								value = "";
								break;
							case Cell.CELL_TYPE_ERROR:
								value = "" + cell.getErrorCellValue();
								break;
							default:
							}
					ReadData[j+1]= value;		
					}
				}
			}
			model.addRow(ReadData);
				
		}
		//스트링 컬럼 속성
		DefaultTableCellRenderer dtcr2 = new DefaultTableCellRenderer();
		dtcr2.setHorizontalAlignment(SwingConstants.CENTER);
		dtcr2.setFont(SubFont);
		Subject_Table.getColumn("V").setPreferredWidth(40);
		Subject_Table.getColumn("프로젝트").setCellRenderer(dtcr2);
		Subject_Table.getColumn("프로젝트").setPreferredWidth(150);
		Subject_Table.getColumn("담당자").setCellRenderer(dtcr2);
		Subject_Table.getColumn("년도").setCellRenderer(dtcr2);
		Subject_Table.getColumn("분기").setCellRenderer(dtcr2);
		Subject_Table.setRowHeight(50);
		Subject_Table.setFont(SubFont);
			
		// 테이블 정렬
		Subject_Table.setBackground(Color.WHITE);
		Subject_Table.setAutoCreateRowSorter(true);
		TableRowSorter tablesorter = new TableRowSorter(Subject_Table.getModel());
		Subject_Table.setRowSorter(tablesorter);
		
		// 테이블 새로고침
		Subject_Table.revalidate();
		Subject_Table.repaint();
		
		} catch (Exception e) {			
			e.printStackTrace();		
		}					
	}
	// 정렬 후 작업을 위함
	// 정렬했다면 Subject_Table의 현재 상태로 Subject_List.xlsx 수정
	void RefreshSubjectExel() {
		if(Subject_Table.getRowCount() > 1) {
			String FilePath = "./Subject_Dir/";			
			File destFile = new File(FilePath +"Subject_List.xlsx");
			//출력
			FileOutputStream outFile;
			try {
				FileInputStream inputStream = new FileInputStream(destFile);
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
				Sheet sheet = workbook.getSheetAt(0);
				int rows = 1;
				Row row;
				for(int i = 0; i < Subject_Table.getRowCount(); i++) {
					row  = sheet.getRow(rows);
					row.getCell(0).setCellValue(Subject_Table.getValueAt(i, 1).toString());
					row.getCell(1).setCellValue(Subject_Table.getValueAt(i, 2).toString());
					row.getCell(2).setCellValue(Subject_Table.getValueAt(i, 3).toString());
					row.getCell(3).setCellValue(Subject_Table.getValueAt(i, 4).toString());
					rows++;
				}
				
				outFile = new FileOutputStream(FilePath + "Subject_List.xlsx");
				workbook.write(outFile);	
				outFile.close();
				workbook.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		}
	}
		
	void setID(String Id) {
		Id_Label.setText("ID : " + Id);
		Id_Label.repaint();
	}
	
	void RemoveFile(String FilePath, String FileName) {
		File destFile = new File(FilePath + FileName + ".xlsx");
		destFile.delete();
	}
	
	// 등록, 수정, 삭제 , 휴지통 버튼 ActionListener
	class RegActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			RefreshSubjectExel();
			
			int SelectedRowNum = Subject_Table.getRowCount();
			String [] S_Reg_Info = {"","","월", "", "2018", "1분기"};
			win.change("Add_Panel", SelectedRowNum, S_Reg_Info);
		}
	}
	
	class ChangeActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int SelectedNum = 0;
			int SelectedRowNum = 0;
            UIManager UI =new UIManager();
            Color navy = new Color(0,32,96);
            Font message = new Font("맑은고딕",Font.BOLD,20);
            UI.put("OptionPane.messageForeground", navy);
            UI.put("OptionPane.messageFont", message);
			boolean Select_Flag = Boolean.FALSE;
			for(int i=0; i < Subject_Table.getRowCount() ; i++) {
				if(Subject_Table.getValueAt(i, 0) == Boolean.TRUE) {
					SelectedNum++;
					SelectedRowNum = i;
				}
			}
			if(SelectedNum == 0) {
				JOptionPane.showMessageDialog(null , "항목을 선택해주세요." , "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(SelectedNum > 1) {
				JOptionPane.showMessageDialog(null , "하나의 항목만 선택해주세요." , "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				Select_Flag = Boolean.TRUE;
			}
			
			if(Select_Flag == Boolean.TRUE) {
				RefreshSubjectExel();
				

				String[] S_Reg_Info = new String[4];			
				for(int i = 0; i < 4; i++) {
					S_Reg_Info[i] = Subject_Table.getValueAt(SelectedRowNum, i+1).toString();
				}
				win.change("Change_Panel", SelectedRowNum, S_Reg_Info);
			}
		}
	}
	class DeleteActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			RefreshSubjectExel();

			int SelectedNum = 0;
            UIManager UI =new UIManager();
            Color navy = new Color(0,32,96);
            Font message = new Font("맑은고딕",Font.BOLD,20);
            UI.put("OptionPane.messageForeground", navy);
            UI.put("OptionPane.messageFont", message);
			Vector<Integer> SelectedRowNum = new Vector<Integer>();
			boolean Select_Flag = Boolean.FALSE;
			for(int i=0; i < Subject_Table.getRowCount() ; i++) {
				if(Subject_Table.getValueAt(i, 0) == Boolean.TRUE) {
					SelectedNum++;
					SelectedRowNum.add(Integer.valueOf(i));
				}
			}
			
			if(SelectedNum == 0) {
				JOptionPane.showMessageDialog(null , "항목을 선택해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				Select_Flag = Boolean.TRUE;
			}
			
			if(Select_Flag == Boolean.TRUE) {
				
				// 엑셀선택
				String FilePath = "./Subject_Dir/";			
				String FilePath2 = "./Subject_Dir/Todolist_Dir/";			
				
				//출력
				FileOutputStream outFile;
				try {
					FileInputStream inputStream = new FileInputStream(FilePath + "Subject_List.xlsx");
					XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
					Sheet sheet = workbook.getSheetAt(0);
					int rows=0;
					Iterator <Integer> it = SelectedRowNum.iterator();
					Row row; 
					while(it.hasNext()) {
						rows = it.next().intValue()+1;
						row = sheet.getRow(rows);
						
						//To Do List 엑셀 삭제 
						RemoveFile(FilePath2, sheet.getRow(rows).getCell(0).getStringCellValue());
						
						//선택한 프로젝트의 Row ""으로 초기화 (Subject_List.xlsx)
						row.getCell(0).setCellValue("");
						row.getCell(1).setCellValue("");
						row.getCell(2).setCellValue("");
						row.getCell(3).setCellValue("");
					}
					
					// 공백으로 만들고 파일 닫기
					outFile = new FileOutputStream(FilePath + "Subject_List.xlsx");
					workbook.write(outFile);	
					outFile.close();
					workbook.close();
					
					
					// 공백 정렬
					SortExcel SE = new SortExcel();	
					SE.sort(FilePath, "Subject_List.xlsx");
					
				} catch (Exception ex) {
					ex.printStackTrace();
				} 	
				RefreshSubjectTable();
			}
		}
	}
	

	class ShowAllActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			win.change("ShowAll_Todo");
		}
	}
	
	// 프로젝트 더블클릭시 ToDoList 페이지 전환 마우스리스너
	private class SelectSubjectMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JTable t = (JTable)e.getSource();
			if(e.getClickCount() == 2) {
				//TableModel m = t.getModel();
				Point pt = e.getPoint();
				int r = t.rowAtPoint(pt);
				int c = t.columnAtPoint(pt);
				
				if(r >= 0 && c >0) {
					int row = t.convertColumnIndexToModel(r);
					win.change("ToDoList", Subject_Table.getValueAt(row, 1).toString());
				}
			}
		}
	}
	
}


public class MainFrame extends JFrame{
	public Add_Change_Panel AP = null;
	public Add_Change_Panel CP = null;
	public Mainpage MP = null;
	public Login Log = null;
	public ShowAll_Todo SA = null;
	public Todolist TD = null;
		
	public void change(String panelName)	{
		// 프로젝트 페이지 전환
		if(panelName.equals("Mainpage")) {
			getContentPane().removeAll();
			getContentPane().add(MP);
			MP.setID(Log.getID());
			MP.RefreshSubjectTable();
			revalidate();
			repaint();
		}
		//전체 To Do List보기 전환
		else if(panelName.equals("ShowAll_Todo")) {
			//this.setVisible(true);
			this.SA = new ShowAll_Todo();
			SA.setVisible(true);
		}
				
	}
	// 특정 프로젝트의 To Do List보기 전환
	public void change(String panelName, String Subject_Name) {
		if(panelName.equals("ToDoList")) {
			this.setVisible(false);
			this.TD = new Todolist(Subject_Name);
			this.TD.setSubject_Name(Subject_Name);
			this.TD.revalidate();
			this.TD.repaint();
			this.TD.setVisible(true);
		}
	}
	
	// 수정 페이지 전환
	public void change(String panelName, int SelectedRowNum, String[] Reg_Info) {
		//선택된 프로젝트의 줄수 전달
		//추가 화면 전환
		if(panelName.equals("Add_Panel")) {	
			AP.setPanelName("Add_Panel");
			AP.setRegInfo(Reg_Info);
			AP.ChangeMod(Reg_Info);
			AP.changeRegText("Add_Panel");
			AP.ChangeTitle("프로젝트 등록");
			AP.repaint();
			getContentPane().removeAll();
			getContentPane().add(AP);
			revalidate();
			repaint();
		}
		// 수정 페이지 전환
		else if(panelName.equals("Change_Panel")) {			
			AP.setRowNum(SelectedRowNum);
		
			AP.setRegInfo(Reg_Info);
			AP.setPanelName("Change_Panel");
			AP.setPreName(Reg_Info[0]);
			AP.ChangeMod(Reg_Info);
			AP.ChangeTitle("프로젝트 수정");
			AP.changeRegText("Change_Panel");
			AP.repaint();
			getContentPane().removeAll();
			getContentPane().add(AP);
			revalidate();
			repaint();
		}
	}
	

	public void execute() {
		// 파일이 경로에 없으면 생성
		String FilePath = "./Subject_Dir/";
		String FilePath2 = "./Subject_Dir/ToDolist_Dir/";
		File destdir = new File(FilePath);
		File destdir2 = new File(FilePath2);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFWorkbook workbook2 = new XSSFWorkbook();
		
		Sheet sheet;
		Sheet sheet2;
		if(!destdir.exists() || !destdir2.exists()) {
			destdir.mkdirs();
			destdir2.mkdirs();
		}
		File destFile = new File(FilePath + "Subject_List.xlsx");
		File destFile2 = new File(FilePath2 + "Trashcan.xlsx");
		try {
			if(!destFile.exists() || !destFile2.exists()) {
				destFile.createNewFile();
				destFile2.createNewFile();
				sheet=workbook.createSheet();
				
				
				sheet2=workbook2.createSheet();
				sheet2.createRow(0);
				
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue("프로젝트");
				row.createCell(1).setCellValue("담당자");
				row.createCell(2).setCellValue("년도");
				row.createCell(3).setCellValue("분기");
				
				Row row2 = sheet2.createRow(0);
				row2.createCell(0).setCellValue("프로젝트");
				row2.createCell(1).setCellValue("할 일");
				row2.createCell(2).setCellValue("마감 기한");
				row2.createCell(3).setCellValue("실제 마감일");
				row2.createCell(4).setCellValue("완료 여부");
				row2.createCell(5).setCellValue("중요도");
				
				
				FileOutputStream outFile;
				FileOutputStream outFile2;
				
				try {
					outFile = new FileOutputStream(FilePath + "Subject_List.xlsx");
					outFile2 = new FileOutputStream(FilePath2 + "Trashcan.xlsx");
					workbook.write(outFile);
					workbook2.write(outFile2);
					outFile.close();
					outFile2.close();
					workbook.close();
					workbook2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch(IOException ex){
			ex.getMessage();
		}
		
		// GUI 시작
		MainFrame test = new MainFrame();
		
		//test.setTitle("To Do List Program");
		test.Log = new Login(test);
		test.AP = new Add_Change_Panel(test, "Add_Panel");
		test.MP = new Mainpage(test);
		
		test.add(test.Log);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setBackground(Color.WHITE);
	    test.setSize(650, 750);
	    test.setVisible(true);
	    test.setResizable(false);
	    //as
	}
}