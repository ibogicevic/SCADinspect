package scadinspect.data.scaddoc.export.format;

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
    Row[] row = new Row[keys.size()];
    Cell[][] cell = new Cell[row.length][];
    
    if(keys.size() ==0){
      return null;
    }
    
    int rowNum = 1;
    
    for(Module module:modules){
      rowNum++;
      Collection<scadinspect.data.scaddoc.properties.Property> properties = module.getProperties();
      for(String key:keys){
        int columnNum = keys.indexOf(key);
        row[columnNum] = sheet.createRow(columnNum);
        for(scadinspect.data.scaddoc.properties.Property property : properties){
          cell[columnNum] = new Cell[columnNum];
          cell[rowNum][columnNum]=row[columnNum].createCell(rowNum);
          if(property.getKey().equals(key)){
              cell[rowNum][columnNum].setCellValue(property.toString());
          }
        }
      }
    }
    
    return workbook.toString().getBytes();
  }

  @Override
  public byte[] getOutput(Collection<ScadDocuFile> files) throws Exception {
    
    //fileOut = new FileOutputStream(fileName);
    
    for (ScadDocuFile docuFile : files) {
      getOutput(docuFile);
    }
    
    return workbook.toString().getBytes();
  }

}
