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
  
  
  //private String fileName = "report.xls";
  String lineSeparator = System.lineSeparator();
  
  Workbook workbook = new HSSFWorkbook();
  //FileOutputStream fileOut;
  Sheet sheet = workbook.createSheet();

  @Override
  public byte[] getOutput(ScadDocuFile file) throws Exception {
    List<String> keys = new ArrayList<>(file.getAllKeys());
    Collection<Module> modules = file.getModules();
    Row[] row = new Row[keys.size()+1];
    Cell[][] cell = new Cell[row.length][];
    
    if(keys.size() ==0){
      return null;
    }
    
    int rowNum = 0;
    int columnNum = 0;
    
    for(Module module:modules){
      rowNum++;
      row[rowNum] = sheet.createRow(rowNum);
      Collection<scadinspect.data.scaddoc.properties.Property> properties = module.getProperties();
      for(scadinspect.data.scaddoc.properties.Property property:properties){
        if(columnNum>properties.size()-1){
          columnNum=0;
        }else {
          columnNum++;
        }
          cell[rowNum] = new Cell[properties.size()+1];
          cell[rowNum][columnNum]=row[rowNum].createCell(columnNum);
          //cell[rowNum+1][columnNum]=row[columnNum].createCell(rowNum+1);
        for(String key : keys){
          if(property.getKey().equals(key)){
            System.out.println("Key:" + property.getKey());
            System.out.println("Value:" + property.getValue());
            System.out.println("column:"+columnNum+"\nrow:"+rowNum);
              cell[rowNum][columnNum].setCellValue(property.getKey());
              //cell[rowNum+1][columnNum].setCellValue(property.getValue().toString());
          }
        }
      }
    }

    FileOutputStream fo = new FileOutputStream("directExport.xls");
    workbook.write(fo);
    fo.close();

    return workbook.toString().getBytes();
  }

  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files) throws Exception {
    
    //fileOut = new FileOutputStream(fileName);
    
    for (ScadDocuFile docuFile : files) {
      getOutput(docuFile);
    }

    FileOutputStream fileOut = new FileOutputStream("directExport.xls");
    workbook.write(fileOut);
    fileOut.close();

    return workbook.toString().getBytes();
  }

}
