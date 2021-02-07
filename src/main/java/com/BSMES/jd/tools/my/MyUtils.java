package com.BSMES.jd.tools.my;



import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自写实用小工具
 * @author DNYY
 */
public class MyUtils {

    /**
     * 修改Collection<T> T中的所有属性 把obj为中为null的属性全部设置为null
     * @param array 数组
     * @param obj   示例T的属性
     * @param <T>
     * @return
     */
    public static <T> Collection<T> AllSet(Collection<T> array, T obj) {
        if (array==null || Objects.isNull(obj)){
            return null;
        }
        //获取其类名
        Class<?> c = obj.getClass();
        //获取其属性集合
        Field[] fs = c.getDeclaredFields();
        //遍历其属性
        for (Field f : fs) {
            //得到此属性的值
            Object val = null;
            try {
                val = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //判断其属性是否为空,如果为空
            if(Objects.isNull(val)){
                for (T a:array) {
                    //把a的该属性设置为空
                    try {
                        f.set(a,null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return array;
    }

    /**
     * 修改List<T> T中的所有属性
     * @param array  数组
     * @param fields 要保留信息的列明
     * @param <T>
     * @return
     */
    public static <T> Collection<T> AllSet(Collection<T> array,Collection<String> fields) {
        if (array==null || fields==null){
            return null;
        }
        //获取其类名
        Class<?> c = array.getClass();
        //获取其属性集合
        Field[] fs = c.getDeclaredFields();
        //遍历其属性
        for (Field f : fs) {
            for (String field : fields) {
                if (!(f.getName() == field)){
                    for (T a:array) {
                        //把a的该属性设置为空
                        try {
                            f.set(a,null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return array;
    }

    /**
     * 将Object转化为Map
     * @param obj
     * @param isline 是否要将驼峰转换为蛇形
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj,Boolean isline){
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
//        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName;
            if (isline){
                fieldName = MyUtils.humpToLine(field.getName());
            }else{
                fieldName = field.getName();
            }

            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 根据对象的属性名来获取对应的值(不用考虑对象的类型)
     * @param fieldName 对象的属性名(String)
     * @param object 对象
     * @return
     */
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            return  field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据对象的属性名来设置对应的值(不用考虑对象的类型)
     * @param fieldName 对象的属性名(String)
     * @param object 对象
     * @param value  对应的值
     * @return
     */
    public static void setFieldValueByFieldName(String fieldName,Object object,Object value){
        try {
            Field field = object.getClass().getField(fieldName);
            field.setAccessible(true);
            //设置对象的访问权限，保证对private的属性的访问
            field.set(object,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定字符串的后缀名
     * @param fileName
     * @param bite
     * @return
     */
    public static String getlastName(String fileName,String bite){
        int i = fileName.lastIndexOf(bite);
        String string = null;
        if (i==-1){
            string = null;
        }else{
            string=fileName.substring(i+1);
        }
        return string;
    }

    /**
     * 判断字符串是否为空,是空返回false 非空返回true
     * @param string
     * @return
     */
    public static Boolean StringIsNull(String string){
        if (string==null || "".equals(string) || string.length()==0) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * 给一个字符串创建一个字符串的类
     */
    public static Object createClassByString(String classname) throws Exception {
        //加载要反射的类
        Class cls = Class.forName(classname);
        //找到类的构造方法
        Object instance = cls.newInstance();

        return instance;

    }

    /**
     * 数字前面自动补零
     * @param number 数字
     * @return
     */
    public static String geFourNumber(int number,int log){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(log);
        formatter.setGroupingUsed(false);
        return formatter.format(number);
    }

    /**
     * 蛇形转驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str) {

        Pattern linePattern = Pattern.compile("_(\\w)");

        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转蛇形
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");

        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     *
     * @param list  要导出的数据
     * @param map   要导出数据的列明中文与英文   key 列明  val 中文注释
     * @param fileName  导出后文件名
     * @param <T>
     */
    public static <T> void exportExcel(List<T> list, LinkedHashMap<String, String> map, String fileName, HttpServletResponse response,HashMap<String,Object> params) throws IOException, ParseException {
        //文件地址
        String excel = null;
        XSSFWorkbook wb = null;
        XSSFSheet sheet = null;
//        String excel = "E://java/jd/src/main/resources/static/modle1.xlsx";
//        String excel = "D://FAFMES/static/modle1.xlsx";
        Boolean flag = params.containsKey("address") && params.get("address")!=null;

        if (params.containsKey("address") && params.get("address")!=null){
            excel = params.get("address").toString();

            File fi = new File(excel);

            wb = new XSSFWorkbook(new FileInputStream(fi));
            sheet = wb.getSheetAt(0);
        }else{
            //新建一张表
            wb = new XSSFWorkbook();
//            Sheet sheet = wb.createSheet("Goods");
            sheet = wb.createSheet("Goods");

            //合并单元格
            CellRangeAddress region1 = new CellRangeAddress(0, 1, (short) 0, (short) 13);
            sheet.addMergedRegion(region1);

//            sheet.setDefaultColumnWidth(200*256);
//            sheet.setDefaultRowHeight((short) (30*20));
        }

        //设置样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置单元格格式为文本
//        XSSFDataFormat format = wb.createDataFormat();
//        cellStyle.setDataFormat(format.getFormat("@"));





        //setCellStyle
        XSSFFont cellFont = wb.createFont();
        cellFont.setFontName("Courier New");
        cellFont.setBold(false);
        cellStyle.setFont(cellFont);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont cellFont2 = wb.createFont();
        cellFont2.setFontName("Arial");
        cellFont2.setFontHeightInPoints((short) 32);
        cellFont2.setBold(true);
        cellStyle2.setFont(cellFont);

        //定好格式

        SimpleDateFormat time = new SimpleDateFormat("yyyy年MM月dd日");

        String date = params.get("time").toString();         //获得你要处理的时间 Date型


        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        String sorg = "";

        if(params.get("sorg")!=null){
            sorg = params.get("sorg").toString();
        }

        Row titleRow2 = sheet.createRow(1);
        Cell cell2 = titleRow2.createCell(0);
        cell2.setCellValue(params.get("title").toString());
        cell2.setCellStyle(cellStyle2);

        Row titleRow3 = sheet.createRow(2);
        Cell cell0 = titleRow3.createCell(0);
        Cell cell3 = titleRow3.createCell(map.size()-1);
        cell0.setCellValue("车间:"+sorg);
        cell3.setCellValue(time.format(date1));

//        cell0.setCellStyle(cellStyle);
//        cell3.setCellStyle(cellStyle);


        if (list!=null && list.size()>0){
            for(int i = 3 ; i <= list.size()+3 ; i++){
                //使用迭代器 遍历 HashMap
                Iterator iter = map.keySet().iterator();
                //列名
                Row titleRow = sheet.createRow(i);
                Integer item = 0;
                while(iter.hasNext()){
                    //创建第一行，起始为0
                    String key = (String) iter.next();
                    String val = map.get(key);
                    if (i==3){
                        //定义第一列的信息
                        Cell cell = titleRow.createCell(item);
                        cell.setCellValue(val);
                        cell.setCellStyle(cellStyle);
                    }else{
//                        titleRow.createCell(item).setCellValue(key);
                        //定义后面几列的信息
                        Object value = null;
                        try{
                            value = MyUtils.getFieldValueByFieldName(key,list.get(i-4));
                        }catch (Exception e){
                            value = "";
                        }
                        Cell cell = titleRow.createCell(item);
                        if (value!=null){
                            try {
                                if(key.equals("mdNo")){
                                    cell.setCellValue(value.toString());
                                }else{
                                    double bigval = new BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_UP).doubleValue();
                                    cell.setCellValue(bigval);
                                }

//                                BigDecimal bigval2 = new BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_UP).stripTrailingZeros();
//                                cell.setCellValue(bigval2.toPlainString());
                            }catch (Exception e){
                                cell.setCellValue(value.toString());
//                                e.printStackTrace();
                            }
                        }
                        cell.setCellStyle(cellStyle);

//                        try{
////                            cell.setCellValue(String.format("%.2f",value));
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }

                    }
                    item++;
                }

            }


            //起始的三行需要格式化一下




//            for(int i = 0 ; i <= list.size() ; i++){
//
//            }
        }

        OutputStream outputStream =null;

        try{
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}

