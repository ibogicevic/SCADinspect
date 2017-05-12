package scadinspect.data.scaddoc.export.format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hpsf.Property;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import scadinspect.data.scaddoc.Module;
import scadinspect.data.scaddoc.ScadDocuFile;



public class ExcelExporter implements Exporter{

  private String fileName = "excelExport.xls";
  String lineSeparator = System.lineSeparator();
  
  Workbook workbook = new HSSFWorkbook();
  Sheet sheet = workbook.createSheet();

  @Override
  public byte[] getOutput(ScadDocuFile file) throws Exception {
    List<String> keys = new ArrayList<>(file.getAllKeys());
    Collection<Module> modules = file.getModules();
    Row[] row = new Row[keys.size()*3];
    Cell[][] cell = new Cell[row.length][];
    
    if(keys.size() ==0){
      return null;
    }
    
    int rowKeyNum = 0;
    int rowValueNum = 1;
    int columnNum = 0;
    
    for(Module module:modules){
      row[rowKeyNum] = sheet.createRow(rowKeyNum);
      row[rowValueNum] = sheet.createRow(rowValueNum);
      Collection<scadinspect.data.scaddoc.properties.Property> properties = module.getProperties();
      cell[rowKeyNum] = new Cell[properties.size()+1];
      cell[rowValueNum] = new Cell[properties.size()+1];
      for(scadinspect.data.scaddoc.properties.Property property:properties){
        cell[rowKeyNum][columnNum]=row[rowKeyNum].createCell(columnNum);
        cell[rowValueNum][columnNum]=row[rowValueNum].createCell(columnNum);
        for(String key : keys){
          if(property.getKey().equals(key)){
            System.out.println("Key:" + property.getKey());
            System.out.println("Value:" + property.getValue());
            System.out.println("column:"+columnNum+"\nrow:"+rowKeyNum);
              cell[rowKeyNum][columnNum].setCellValue(property.getKey());
              cell[rowValueNum][columnNum].setCellValue(property.getValue().toString());
          }
        }
          columnNum++;
      }
      columnNum=0;
      rowKeyNum+=3;
      rowValueNum+=3;
    }

    FileOutputStream fileOut = new FileOutputStream(fileName);
    workbook.write(fileOut);
    fileOut.close();

    return workbook.toString().getBytes();
  }

  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files) throws Exception {
    
    for (ScadDocuFile docuFile : files) {
      getOutput(docuFile);
    }

    return workbook.toString().getBytes();
  }

}
