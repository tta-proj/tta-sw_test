package tta.base;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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
    
    ArrayList<Object[]> t_list = new ArrayList<Object[]>();
		

    public List<Object[]> getTodo()
    {
    	return t_list;
    }
    
	public void ReadTodo()
	{
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
	}
}
