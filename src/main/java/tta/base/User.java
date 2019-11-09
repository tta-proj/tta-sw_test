package tta.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class User {

	Hashtable<String, String> userStaff = new Hashtable<String, String>();//
	
	String Todo,Done,Sub,VALUE;
	String Dead_day,Actual_day;
	XSSFRow row;
	XSSFCell cell;
    Boolean Is_import;
    File file;
    
    
    public Hashtable<String, String> getStaff()
    {
    	return userStaff;
    }
	
	public void ReadStaff()
	{
		String folder="./Subject_Dir/"; //읽을 폴더명 세팅
		String file_name = "User_List.xlsx"; // 읽을 파일이름
		File file=new File(folder);
		
		if(!file.isDirectory()) {
			System.out.println("해당디렉토리가 없습니다");
			System.exit(1);
		}
		
		if(!file.exists()) {
			System.out.println("해당 파일이 없습니다");
			System.exit(1);
		}
		
	
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
								String temp_id = row.getCell(0).getStringCellValue();
								String temp_pw = row.getCell(1).getStringCellValue();
								
								userStaff.put(temp_id, temp_pw);
							}
						}
					}
				}
				
        } catch (Exception e) {

			e.printStackTrace();
		}
				
	}	
	
}
