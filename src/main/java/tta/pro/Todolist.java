package tta.pro;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.util.Iterator;
import java.util.Vector;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
          try {
                 FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
                 XSSFWorkbook workbook = new XSSFWorkbook(fis);
                 Sheet sheet = workbook.getSheetAt(0);
                 int rows = sheet.getPhysicalNumberOfRows();
                 
                 for(int i=1;i<rows;i++) {
                    Row row = sheet.getRow(i);             
                    if(row==null)
                       rows++;
                    else {
                       Object[] ob = {false,row.getCell(1),row.getCell(2),row.getCell(3),row.getCell(4),row.getCell(5)} ;
                       model.addRow(ob);   
                     
                    }

                 } 
               
              fis.close();
              }
              catch (Exception e){
                    e.printStackTrace();
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
              		
            	 try {
            	FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				Sheet sheet = workbook.getSheetAt(0);   
			
           		int rows = sheet.getPhysicalNumberOfRows();
           		for(int i=1;i<rows;i++) {
           			Row row = sheet.getRow(i);           			
           			if(row.getCell(4).toString().equals("완료"))
           				continue;          			
           			else {
           				Object[] obj = {false,row.getCell(1),row.getCell(2),row.getCell(3),row.getCell(4),row.getCell(5)};
           				model.addRow(obj);
           			}
           		}                
           		fis.close();
                   }
                   catch (Exception ex){
                         ex.printStackTrace();
                   }                
               }
               else {                  
                   		
       	 try {
       		Hide_Button.setText("숨기기");
       		FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			model = (DefaultTableModel)table.getModel();
            model.setNumRows(0);
			Sheet sheet = workbook.getSheetAt(0);   
       	
			int rows = sheet.getPhysicalNumberOfRows();
      		
      		for(int i=1;i<rows;i++) {
      			Row row = sheet.getRow(i);      			
      			Object[] obj = {false,row.getCell(1),row.getCell(2),row.getCell(3),row.getCell(4),row.getCell(5)} ;
  				model.addRow(obj);
      		}                
      		fis.close();
              }
              catch (Exception ex){
                    ex.printStackTrace();
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
            try {
               
                  data[5]=Subject_Name;
               
                  FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/Trashcan.xlsx");
                  XSSFWorkbook workbook = new XSSFWorkbook(fis);
                  Sheet sheet = workbook.getSheetAt(0);

                  int rows = sheet.getPhysicalNumberOfRows();
                  Row row = sheet.createRow(rows);
                  
                  row.createCell(0).setCellValue(data[5]);
                  row.createCell(1).setCellValue(data[0]);
                  row.createCell(2).setCellValue(data[1]);               
                  row.createCell(3).setCellValue(data[2]);                  
                  row.createCell(4).setCellValue(data[3]);               
                  row.createCell(5).setCellValue(data[4]);
                  
              
               FileOutputStream fos = new FileOutputStream("./Subject_Dir/ToDolist_Dir/Trashcan.xlsx");
                  workbook.write(fos);    
                  fos.close();
                  fis.close();
            } catch (Exception ex) {
               ex.printStackTrace();
            } 
                        
               try {
                  FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
                  XSSFWorkbook workbook = new XSSFWorkbook(fis);
                  Sheet sheet = workbook.getSheetAt(0);
                  int rows=0;
                  Iterator <Integer> it = SelectedRowNum.iterator();
                  Row row; 
                  Row NextRow = sheet.getRow(0);
                  Cell NextCell = NextRow.getCell(0);
                     
                  while(it.hasNext()) {
                     rows = it.next().intValue()+1;                     
                     row = sheet.getRow(rows);
                  
                     row.getCell(0).setCellValue("");
                     row.getCell(1).setCellValue("");
                     row.getCell(2).setCellValue("");
                     row.getCell(3).setCellValue("");
                     row.getCell(4).setCellValue("");
                     row.getCell(5).setCellValue("");
                     
                  }
      
                  int   NextRowNum=0;
                                    
                  for(int searchRow = 1; searchRow < sheet.getPhysicalNumberOfRows();searchRow++) {
                     row = sheet.getRow(searchRow);
                     cell = row.getCell(0);
                     
                     if (cell.getStringCellValue() == ""){
                        for(NextRowNum = searchRow; NextRowNum < sheet.getPhysicalNumberOfRows(); NextRowNum++) {
                           
                           NextRow = sheet.getRow(NextRowNum);
                           NextCell = NextRow.getCell(0);
                           if(NextCell.getStringCellValue() != "") {
                              
                              for(int r = 0; r <row.getPhysicalNumberOfCells(); r++) {
                                 NextCell = NextRow.getCell(r);
                                 row.getCell(r).setCellValue(NextCell.getStringCellValue());
                                 NextCell.setCellValue("");
                              }
                              break;
                           }
                        }
                     }
                  }
                  
                  for(int i=0; i < SelectedRowNum.size() ; i++) {                    
                     row = sheet.getRow(sheet.getLastRowNum());
                     sheet.removeRow(row);
                  }
            
                  FileOutputStream fos = new FileOutputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
                  workbook.write(fos);
                  fos.close();
                  fis.close();
                  
                  sortExcel SE = new sortExcel();   
                  SE.sort("./Subject_Dir/ToDolist_Dir/", Subject_Name +".xlsx");
                                                            
               } catch (Exception ex) {
                  ex.printStackTrace();
               } 
               RefreshTable();        
            	}
            }
      });
   }
}