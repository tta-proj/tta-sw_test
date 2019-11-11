package tta.pro;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Cell;
import java.util.List;
import java.util.Vector;

import tta.base.Task;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class Back {
   public void back() {
   MainFrame test = new MainFrame();
   
   test.setTitle("To Do List Program");
   test.Log = new Login(test);
   test.AP = new Add_Change_Panel(test, "Add_Panel");
   test.MP = new Mainpage(test);
   
   test.add(test.MP);
   test.MP.RefreshSubjectTable();
   test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   test.setSize(650, 750);
   test.setVisible(true);
   test.setResizable(false);
   }
}

public class Todolist extends JFrame{
	private JCheckBox chk;
	private JButton Hide_Button;
	private Register_Todo RT;
	private JButton Add_Button;
	private JButton Delete_Button;
	private JButton Change_Button;   
	private Cell cell;  
	   
	Task t_list = new Task();
	
	private JTable table; 
	DefaultTableModel model;
	   
	public String[] column = {"V", "할 일", "마감 기한", "실제 마감일", "완료 여부", "중요도"};
	public Object rowData[][];
	      
	private String Subject_Name = new String();
    
    
    public void HeaderSetting_2() {
       
       chk = new JCheckBox();
       table.getColumn("V").setCellRenderer(dtcr1);       
       table.getColumn("V").setCellEditor(new DefaultCellEditor(chk));
       chk.setHorizontalAlignment(JLabel.CENTER);
       
       DefaultTableCellRenderer Todo = new DefaultTableCellRenderer();
       Todo.setHorizontalAlignment(SwingConstants.CENTER);
       table.getColumn("할 일").setCellRenderer(Todo);
       
    
       DefaultTableCellRenderer Dead = new DefaultTableCellRenderer();
       Dead.setHorizontalAlignment(SwingConstants.CENTER);
       table.getColumn("마감 기한").setCellRenderer(Dead);
       
       
       DefaultTableCellRenderer Actual = new DefaultTableCellRenderer();
       Actual.setHorizontalAlignment(SwingConstants.CENTER);
       table.getColumn("실제 마감일").setCellRenderer(Actual);
       
       DefaultTableCellRenderer Done = new DefaultTableCellRenderer();
       Done.setHorizontalAlignment(SwingConstants.CENTER);
       table.getColumn("완료 여부").setCellRenderer(Done);
       
       
       DefaultTableCellRenderer Import = new DefaultTableCellRenderer();
       Import.setHorizontalAlignment(SwingConstants.CENTER);
       table.getColumn("중요도").setCellRenderer(Import);
       
       table.getTableHeader().setReorderingAllowed(false); // table 속성들 이동 금지
       
       table.getTableHeader().setFont(new Font("맑은고딕",Font.BOLD,15));
       JTableHeader header = table.getTableHeader(); 
       header.setPreferredSize(new Dimension(100,25)); // 헤더높이조절
     
    
		header.setResizingAllowed(false); // 셀 헤더 너비조절 금지
    }
    
    public void HeaderSetting() {
       Color navy = new Color(0,32,96);
       table.getTableHeader().setBackground(navy);
       table.getTableHeader().setForeground(Color.white);
       table.getColumnModel().getColumn(0).setPreferredWidth(10);
       table.getColumnModel().getColumn(1).setPreferredWidth(200);
       table.getColumnModel().getColumn(2).setPreferredWidth(80);
       table.getColumnModel().getColumn(3).setPreferredWidth(80);
       table.getColumnModel().getColumn(4).setPreferredWidth(70);
       table.getColumnModel().getColumn(5).setPreferredWidth(50);
    }
    
    public void setSubject_Name(String Subject_Name) {
          this.Subject_Name = Subject_Name;          
    }
    
   
    public void RefreshTable() {
       table.setBackground(Color.WHITE);
       table.setAutoCreateRowSorter(true);
       DefaultTableModel model = new DefaultTableModel(rowData, column) {            
         public boolean isCellEditable(int row, int column) {
            if(column == 0) {
               return true;
            }
            return false;
         }
      };
      table.setModel(model);
      
       table.revalidate();
       table.repaint();
      
       TableRowSorter tablesorter = new TableRowSorter(table.getModel());
       table.setRowSorter(tablesorter);
       table.setRowHeight(30); // 셀높이조절
       
       HeaderSetting_2();
       HeaderSetting();
       
       
       List<Object[]> tasklist = t_list.ReadSubjectTodo(Subject_Name);
       
       for(int i=0; i<tasklist.size(); i++)
       {
    	   model.addRow(tasklist.get(i));
       }
       
   }
      
     
    DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer(){      
         public Component getTableCellRendererComponent
         (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
         {
            chk = new JCheckBox();
            chk.setSelected(((Boolean)value).booleanValue());
            chk.setHorizontalAlignment(JLabel.CENTER);
            return chk;            
         }
    };
   public Todolist(final String Subject_Name) {    
          this.Subject_Name = Subject_Name;
          
          this.addWindowListener(new WindowAdapter(){ 
                public void windowClosing(WindowEvent e) { 
                   Back re = new Back();
                   re.back();
                }
            });
          
       Color navy = new Color(0,32,96);
               
       JLabel Title_Label = new JLabel(Subject_Name + " To do LIST");
       Title_Label.setFont(new Font("HY견고딕",Font.BOLD,30));
       Title_Label.setForeground(navy);
       this.add(Title_Label);
       
       Add_Button = new JButton("등록");
       Add_Button.setBackground(Color.white);
       Add_Button.setFont(new Font("맑은고딕",Font.BOLD,20));
       
       Delete_Button = new JButton("삭제");
       Delete_Button.setBackground(Color.white);
       Delete_Button.setFont(new Font("맑은고딕",Font.BOLD,20));
       
       Change_Button = new JButton("수정");
       Change_Button.setBackground(Color.white);
       Change_Button.setFont(new Font("맑은고딕",Font.BOLD,20));
       
       Hide_Button = new JButton("숨기기");
       Hide_Button.setBackground(Color.white);
       Hide_Button.setFont(new Font("맑은고딕",Font.BOLD,20));
       
       add(Add_Button);
       add(Delete_Button);
       add(Change_Button);
       add(Hide_Button);
              
       model = new DefaultTableModel(rowData,column)
          {       
             public boolean isCellEditable(int rowData, int column)
             { 
                if(column>0) {
                   return false; 
                         }
             else {
                return true;
                }
                
             }
          };
          
       table= new JTable(model);
       JScrollPane scrollpane =new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
       this.add(scrollpane);
       table.setRowSorter(new TableRowSorter(model)); 
      
           
       HeaderSetting();
       
       HeaderSetting_2();         

       RefreshTable();
         
       
       this.setLayout(null);
       Add_Button.setBounds(368,160,80,30);
       Delete_Button.setBounds(458,160,80,30);
       Change_Button.setBounds(548,160,80,30);
       Hide_Button.setBounds(238,160,120,30);
       scrollpane.setBounds(10, 200, 620, 350);
       Title_Label.setBounds(160, 50, 450, 40);
       Container c = this.getContentPane();
       c.setBackground(Color.white);

       
       setSize(650,750);
      
       
       Hide_Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               JButton Hide_Button = (JButton)e.getSource();
               
               if(Hide_Button.getText().equals("숨기기")) {   
            	   Hide_Button.setText("보여주기");
            	   model = (DefaultTableModel)table.getModel();
            	   model.setNumRows(0);
            	   
            	   List<Object[]> tasklist = t_list.ReadWithoutDoneTodo(Subject_Name);
                   
                   for(int i=0; i<tasklist.size(); i++)
                   {
                	   model.addRow(tasklist.get(i));
                   }
              	         
               }
               else {       
            	   Hide_Button.setText("숨기기");
            	   
            	   model = (DefaultTableModel)table.getModel();
                   model.setNumRows(0);
           
                   List<Object[]> tasklist = t_list.ReadSubjectTodo(Subject_Name);
                   
                   for(int i=0; i<tasklist.size(); i++)
                   {
                	   model.addRow(tasklist.get(i));
                   }    
               }       
         }
        
       });
             
       Add_Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
               
            setVisible(false);
            RT = new Register_Todo(Subject_Name);
            RT.setVisible(true);
            
         }
       });
      
       Change_Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
               
               UIManager UI =new UIManager();
               Color navy = new Color(0,32,96);
               Font message = new Font("맑은고딕",Font.BOLD,20);
               UI.put("OptionPane.messageForeground", navy);
               UI.put("OptionPane.messageFont", message);
               
            int SelectedNum = 0;
            int SelectedRowNum = 0;
            boolean Select = Boolean.FALSE;
            for(int i=0; i < table.getRowCount() ; i++) {
               if(table.getValueAt(i, 0) == Boolean.TRUE) {
                  SelectedNum++;
                  SelectedRowNum = i;
               }
            }
            
            if(SelectedNum == 0 || SelectedNum > 1) {
               if(SelectedNum == 0) {
               JOptionPane.showMessageDialog(null , "항목을 선택해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);   }
               if(SelectedNum > 1) {
               JOptionPane.showMessageDialog(null , "하나의 항목만 선택해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);}
            }            
                        
            else {
               Select = Boolean.TRUE;
            }
            
            if(Select == Boolean.TRUE) {
               String[] data = new String[5];
            for(int i = 0; i < 5; i++) {
               data[i] = table.getValueAt(SelectedRowNum, i+1).toString();
            }
             setVisible(false);
             new Change_Todo(SelectedRowNum, data, Subject_Name).setVisible(true);
               }
            }
         
       });
   
   
      Delete_Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
               
               UIManager UI =new UIManager();
               Color navy = new Color(0,32,96);
               Font message = new Font("맑은고딕",Font.BOLD,20);
               UI.put("OptionPane.messageForeground", navy);
               UI.put("OptionPane.messageFont", message);
               int SelectedNum = 0;
               boolean Select = Boolean.FALSE;
               for(int i=0; i < table.getRowCount() ; i++) {
                   if(table.getValueAt(i, 0) == Boolean.TRUE) {
                      SelectedNum++;
                   }
               }
            if(SelectedNum == 0 || SelectedNum > 1) {
            	if(SelectedNum == 0) {
                JOptionPane.showMessageDialog(null , "항목을 선택해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);   }
                if(SelectedNum > 1) {
                JOptionPane.showMessageDialog(null , "하나의 항목만 선택해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);}
             }            
                          
             else if(Hide_Button.getText().equals("보여주기")){
            	 JOptionPane.showMessageDialog(null , "숨겨진 항목이 있습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
             } else {
            	 Select = Boolean.TRUE;
             }
             
           if(Select == Boolean.TRUE) {
            Vector<Integer> SelectedRowNum = new Vector<Integer>();
             
            for(int i=0; i < table.getRowCount() ; i++) {
               if(table.getValueAt(i, 0) == Boolean.TRUE) {
                  
                  SelectedRowNum.add(Integer.valueOf(i));
                  
               }
            }
                        
            int SelectedRowNum2 = 0;
            
            for(int i=0; i < table.getRowCount() ; i++) {
               if(table.getValueAt(i, 0) == Boolean.TRUE) {
                  
                  SelectedRowNum2 = i;
               }
                        
         }
            String[] data = new String[6];
            for(int i = 0; i < 5; i++) {
               data[i] = table.getValueAt(SelectedRowNum2, i+1).toString();
            }
            
            data[5]=Subject_Name;
            
            t_list.DeleteTodo(data, SelectedRowNum);
            

               RefreshTable();        
            	}
            }
      });
   }
}