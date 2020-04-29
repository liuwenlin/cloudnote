package cn.tedu.note.util.poiUtil;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel文件读取工具类,支持xls,xlsx两种格式
 * @author liuwenlin
 * @version v1.0
 * @date 2020/4/21 22:40
 */
public class ExcelUtil {

    /**
     * excel文件读取指定列的数据
     * @author Andrew
     * @param excelPath        文件名
     * @param args            需要查询的列号
     * @return    ArrayList<ArrayList<String>>    二维字符串数组
     * @throws IOException
     */
    @SuppressWarnings({ "unused" })
    public static ArrayList<ArrayList<String>> excelReader(String excelPath, int ... args) throws IOException {
        // 创建excel工作簿对象
        Workbook workbook = null;
        FormulaEvaluator formulaEvaluator = null;
        // 读取目标文件
        File excelFile = new File(excelPath);
        InputStream is = new FileInputStream(excelFile);
        // 判断文件是xlsx还是xls
        if (excelFile.getName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
            formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        }else {
            workbook = new HSSFWorkbook(is);
            formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        }

        //判断excel文件打开是否正确
        if(workbook == null){
            System.err.println("未读取到内容,请检查路径！");
            return null;
        }
        //创建二维数组,储存excel行列数据
        ArrayList<ArrayList<String>> als = new ArrayList<ArrayList<String>>();
        //遍历工作簿中的sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            //当前sheet页面为空,继续遍历
            if (sheet == null) {
                continue;
            }
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                ArrayList<String> al = new ArrayList<String>();
                // 遍历每一行的每一列
                for(int columnNum = 0 ; columnNum < args.length ; columnNum++){
                    Cell cell = row.getCell(args[columnNum]);
                    al.add(getValue(cell, formulaEvaluator));
                }
                als.add(al);
            }
        }
        is.close();
        return als;
    }

    /**
     * excel文件读取全部信息
     * @author Andrew
     * @param excelPath        文件名
     * @return    ArrayList<ArrayList<String>>    二维字符串数组
     * @throws IOException
     */
    @SuppressWarnings({ "unused" })
    public static ArrayList<ArrayList<String>> excelReader(String excelPath) throws IOException {
        // 创建excel工作簿对象
        Workbook workbook = null;
        FormulaEvaluator formulaEvaluator = null;
        // 读取目标文件
        File excelFile = new File(excelPath);
        InputStream is = new FileInputStream(excelFile);
        // 判断文件是xlsx还是xls
        if (excelFile.getName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
            formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        }else {
            workbook = new HSSFWorkbook(is);
            formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        }

        //判断excel文件打开是否正确
        if(workbook == null){
            System.err.println("未读取到内容,请检查路径！");
            return null;
        }
        //创建二维数组,储存excel行列数据
        ArrayList<ArrayList<String>> als = new ArrayList<ArrayList<String>>();
        //遍历工作簿中的sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            //当前sheet页面为空,继续遍历
            if (sheet == null) {
                continue;
            }
            // 对于每个sheet，读取其中的每一行
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                // 遍历每一行的每一列
                ArrayList<String> al = new ArrayList<String>();
                for(int columnNum = 0 ; columnNum < row.getLastCellNum(); columnNum++){
                    Cell cell = row.getCell(columnNum);
                    al.add(getValue(cell, formulaEvaluator));
                }
                als.add(al);
            }
        }
        is.close();
        return als;
    }

    /**
     * excel文件的数据读取,包括后缀为xls,xlsx
     * @param cell
     * @param formulaEvaluator
     * @return
     */
    @SuppressWarnings("deprecation")
    private static String getValue(Cell cell, FormulaEvaluator formulaEvaluator) {
        if(cell==null){
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                // 判断是日期时间类型还是数值类型
                if (DateUtil.isCellDateFormatted(cell)) {
                    short format = cell.getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;
                    /* 所有日期格式都可以通过getDataFormat()值来判断
                     *     yyyy-MM-dd----- 14
                     *    yyyy年m月d日----- 31
                     *    yyyy年m月--------57
                     *    m月d日  --------- 58
                     *    HH:mm---------- 20
                     *    h时mm分  --------- 32
                     */
                    if(format == 14 || format == 31 || format == 57 || format == 58){
                        //日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }else if (format == 20 || format == 32) {
                        //时间
                        sdf = new SimpleDateFormat("HH:mm");
                    }
                    return sdf.format(cell.getDateCellValue());
                } else {
                    // 对整数进行判断处理
                    double cur = cell.getNumericCellValue();
                    long longVal = Math.round(cur);
                    Object inputValue = null;
                    if(Double.parseDouble(longVal + ".0") == cur) {
                        inputValue = longVal;
                    }
                    else {
                        inputValue = cur;
                    }
                    return String.valueOf(inputValue);
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                //对公式进行处理,返回公式计算后的值,使用cell.getCellFormula()只会返回公式
                return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
            //Cell.CELL_TYPE_BLANK || Cell.CELL_TYPE_ERROR
            default:
                return null;
        }
    }

    /**
     * excel文件的数据导出
     * @param outputFilePath
     * @param data
     */
    public static void exportExcel(String outputFilePath, String sheetName, Object[][] data) throws IOException {
        File file = new File(outputFilePath);
        if (!file.exists()){
            file.createNewFile();
        }

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(sheetName);  //创建table工作薄

        HSSFRow row;

        HSSFCell cell;

        for(int i = 0; i < data.length; i++) {

            row = sheet.createRow(i);//创建表格行

            for(int j = 0; j < data[i].length; j++) {

                cell = row.createCell(j);//根据表格行创建单元格

                cell.setCellValue(String.valueOf(data[i][j]));

            }

        }

        wb.write(new FileOutputStream(outputFilePath));

        wb.close();

        System.out.println("Excel文件已写完!");
    }

    public static void main(String[] args) {
        String filePath = "\\D:\\report.xls";
        try {
            ArrayList<ArrayList<String>> lists = ExcelUtil.excelReader(filePath);
            System.out.println("first row: " + "\n" + lists.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
