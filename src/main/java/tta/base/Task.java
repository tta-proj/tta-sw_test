package tta.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Task {
	
	String Todo,Done,Sub,VALUE;
	String Dead_day,Actual_day;
	XSSFRow row;
	XSSFCell cell;
    Boolean Is_import;
    File file;
    
       
    // Todolist 등록
    public void WriteTodo(String[] data)
	{
    	ArrayList<Object[]> t_list = new ArrayList<Object[]>();
    	
    	try {
    		String subjectName = data[5];
	   		
			FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/"+ subjectName +".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
		

			int rows = sheet.getPhysicalNumberOfRows();
			Row row = sheet.createRow(rows);
		

			row.createCell(0).setCellValue(data[5]);
			row.createCell(1).setCellValue(data[2]);   	   		
			row.createCell(2).setCellValue(data[0]);   	   			
			row.createCell(3).setCellValue(data[1]);	   			
			row.createCell(4).setCellValue(data[3]);
			row.createCell(5).setCellValue(data[4]);
			

		 
			FileOutputStream fos = new FileOutputStream("./Subject_Dir/ToDolist_Dir/"+ subjectName +".xlsx");
			workbook.write(fos);    
			fos.close();
			fis.close();
			   			
		}
    	catch (Exception e) {
	    	  e.printStackTrace();
	    }
	}
    
    
    // Todolist 수정
    public void ReWriteTodo(int SelectedRowNum, String Subject_Name, String[] data)
    {
    	ArrayList<Object[]> t_list = new ArrayList<Object[]>();
    	
		try {
   	   		
			FileInputStream fis = new FileInputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
		

			int rows = SelectedRowNum+1;
			Row row = sheet.createRow(rows);
			
			row.createCell(4).setCellValue(data[3]);
			row.createCell(1).setCellValue(data[2]); 
			row.createCell(0).setCellValue(Subject_Name);
			row.createCell(5).setCellValue(data[4]); 	   		
			row.createCell(2).setCellValue(data[0]);   	   			
			row.createCell(3).setCellValue(data[1]);	   									
	
			FileOutputStream fos = new FileOutputStream("./Subject_Dir/ToDolist_Dir/"+ Subject_Name +".xlsx");
			workbook.write(fos);    
			fos.close();
			fis.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    // 과목별 todolist
    public List<Object[]> ReadSubjectTodo(String Subject_Name)
    {
    	ArrayList<Object[]> t_list = new ArrayList<Object[]>();
    	
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
                  t_list.add(ob);
                
               }
            }
            fis.close();
        }
        catch (Exception e){
        	e.printStackTrace();
        } 
        
        return t_list;
    }
    
    
    // 완료 목록 숨기기
    public List<Object[]> ReadWithoutDoneTodo(String Subject_Name)
    {
    	ArrayList<Object[]> t_list = new ArrayList<Object[]>();
    	
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
    				t_list.add(obj);
    			}
    		}                
    		fis.close();
    	}
    	catch (Exception ex){
    		ex.printStackTrace();
    	}
          
        return t_list;
    }
    
   
    public void DeleteTodo(String[] data, Vector<Integer> SelectedRowNum)
    {
    	ArrayList<Object[]> t_list = new ArrayList<Object[]>();
    	
    	// trashcan 엑셀 파일로 이동
        try {
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
        
      String Subject_Name = data[5];
      Cell cell;
      
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
          
          for(int searchRow = 1; searchRow < sheet.getPhysicalNumberOfRows();searchRow++) 
          {
        	  row = sheet.getRow(searchRow);
              cell = row.getCell(0);
               
              if (cell.getStringCellValue() == ""){
            	  for(NextRowNum = searchRow; NextRowNum < sheet.getPhysicalNumberOfRows(); NextRowNum++) 
            	  {
            		  NextRow = sheet.getRow(NextRowNum);
            		  NextCell = NextRow.getCell(0);
            		  
            		  if(NextCell.getStringCellValue() != "") {
            			  for(int r = 0; r <row.getPhysicalNumberOfCells(); r++) 
            			  {
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
            
            SortExcel SE = new SortExcel();   
            SE.sort("./Subject_Dir/ToDolist_Dir/", Subject_Name +".xlsx");
                                                      
         } catch (Exception ex) {
            ex.printStackTrace();
         }  
    }
   
    
    // 전체 todolist
	public List<Object[]> ReadAllTodo()
	{
		ArrayList<Object[]> t_list = new ArrayList<Object[]>();
		
		String folder="./Subject_Dir/ToDolist_Dir/"; //읽을 폴더명 세팅
		String file_name; // 읽을 파일이름
		File file=new File(folder);
		
		if(!file.isDirectory()) {
			System.out.println("해당디렉토리가 없습니다");
			System.exit(1);
		}
		
		File []list=file.listFiles();
		
		for(File f:list) {
			
			if(f.isFile() && !f.getName().equals("Trashcan.xlsx")) { // 휴지통은 제외
				
				file_name=f.getName();
			
				System.out.println(folder + file_name);
		        try {

					FileInputStream inputStream = new FileInputStream(folder + file_name);

					XSSFWorkbook wb = new XSSFWorkbook(inputStream);
					

					//sheet수 취득

					int sheetCn = wb.getNumberOfSheets();


					for(int cn = 0; cn < sheetCn; cn++){


						//0번째 sheet 정보 취득

						XSSFSheet sheet = wb.getSheetAt(cn);

						//취득된 sheet에서 rows수 취득
						int rows = sheet.getPhysicalNumberOfRows();

						//취득된 row에서 취득대상 cell수 취득
						int cells = sheet.getRow(cn).getPhysicalNumberOfCells(); 
					
						for (int r = 1; r < rows; r++) { // 파일의 두번째줄부터 불러옴
							row = sheet.getRow(r); // row 가져오기

							if (row != null) {
								for (int c = 0; c < cells; c++) {
									cell = row.getCell(c);
									if (cell != null) {
										String value = null;
										switch (cell.getCellType()) {
										case XSSFCell.CELL_TYPE_FORMULA:
											value = cell.getCellFormula();
											break;

										case XSSFCell.CELL_TYPE_NUMERIC:
											if(!HSSFDateUtil.isCellDateFormatted(cell)) {
												SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
												value = "" + formatter.format(cell.getDateCellValue());						
											}else {
												DecimalFormat df = new DecimalFormat();
												value = "" + df.format(cell.getNumericCellValue());												
											}
											break;
										case XSSFCell.CELL_TYPE_STRING:
											value = "" + cell.getStringCellValue();
											break;
										case XSSFCell.CELL_TYPE_BLANK:
											value = "[null 아닌 공백]";
											break;
										case XSSFCell.CELL_TYPE_ERROR:
											value = "" + cell.getErrorCellValue();
											break;
										default:
										}

										System.out.print(value + "\t");
										VALUE = value;

									} else {
										System.out.print("[null]\t");
										VALUE = null;
									}									
									switch(c) {									
									case 0 : Sub=VALUE;
									break;
									case 1 : Todo=VALUE;
									break;
									case 2 : Dead_day=VALUE;
									break;
									case 3 : Actual_day=VALUE;
									break;
									case 4 : Done = VALUE;
									
									Object[] o = new Object[] {Sub,Todo,Dead_day,Actual_day,Done};
									t_list.add(o);
									
									break;
									}
								} // for(c) 문						
								System.out.print("\n");
							}
						} // for(r) 문
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			} // if(파일일경우)
		} // for(f)
		
		return t_list;
	}
}
