package com.jit.wxs.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author zhaojw
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class
POIUtils<T,M> {
	/**
	 * 日期时间标准格式<br>
	 * 2016-10-11 14:20:22
	 */
	private static String dateTimeFormate = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式<br>
	 * 2016-13-22
	 */
	private static String dateFormate = "yyyy-MM-dd";

	/**
	 * 总行数
     */
	private static int totalRows = 65500;
	/**
	 *            列表头
	 *            数据集
	 * @param fileName
	 *            文件名
	 * 
	 */
	public void exportToExcel(HttpServletResponse response, String fileName, String[] headers, Collection<T> dataset) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HSSFWorkbook workbook = createExcelBook(fileName, headers, dataset);
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(sout);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
			}

		}
	}



	public void exportToExcel2(HttpServletResponse response, String fileName, String fileName2, String[] headers, String[] header2, Collection<T> dataset, Collection<M> dataset2) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HSSFWorkbook workbook = createExcelBook(fileName, headers, dataset);
			workbook = createExcelBook2(workbook,fileName2, header2, dataset2);
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(sout);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * 导出Map到Excel
	 *  @author yenbay
	 * @param response 响应
	 * @param fileName 文件名
	 * @param headers  第一个参数key为字段名，后一个参数为对应的中文标题名，有序
	 * @param dataset 数据列表
	 * @param withTime 日期是否包含时间，
	 */
	public static void exportMapToExcel(HttpServletResponse response, String fileName, LinkedHashMap<String, String> headers,
                                        List<Map> dataset, boolean withTime) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HSSFWorkbook workbook = createExcelBook(fileName, headers, dataset,withTime);
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(sout);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
			}

		}
	}
	
	/**
	 * 导出带有合并标题的数据
	 * @author z
	 * @param response 响应
	 * @param fileName 文件名
	 * @param headersMap  第一个参数key为字段名，后一个参数为对应的中文标题名，有序
	 * @param dataList 数据列表
	 * @param footerList 合并列
	 */
	public static void exportMapToExcel(HttpServletResponse response, String fileName, Map<String,List> headersMap,
                                        List<Map> dataList, List<Map> footerList, int footerStart) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HSSFWorkbook workbook = createExcelBook(fileName, headersMap,dataList,footerList,footerStart);
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream sout = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(sout);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
			}

		}
	}
	

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook createExcelBook(String title, String[] headers, Collection<T> dataset) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		HSSFCellStyle style = getHeadHSSFWorkbookStyle(workbook);//设置excel头部样式

		HSSFCellStyle style2 = getHSSFCellStyle(workbook);//设置内容样式

        HSSFPatriarch patriarch = createHSSFPatriarch(sheet);//创建图像处理器
		
		HSSFRow row = sheet.createRow(0);
		setHeadData(row,headers,style);//设置列头数据

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			if(index%totalRows == 0){
				sheet = workbook.createSheet();
				row = sheet.createRow(0);
                patriarch = createHSSFPatriarch(sheet);
				setHeadData(row,headers,style);//设置列头数据
				index = 1;
			}
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = "";
					if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
								index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {

						// 判断值的类型后进行强制类型转换
						if(value==null){
							textValue="";
						}else if(value instanceof java.sql.Timestamp){
							//时间戳
							textValue=value.toString()+"";
						}else if(value instanceof Date){
							//date型时间
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							textValue = formatter.format(value);
						}else{
							//其他情况简单处理
							textValue=value==null?"":value.toString();
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		return workbook;
	}


	@SuppressWarnings("unchecked")
	private HSSFWorkbook createExcelBook2(HSSFWorkbook workbook ,String title, String[] headers, Collection<M> dataset) {
		// 声明一个工作薄
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		HSSFCellStyle style = getHeadHSSFWorkbookStyle(workbook);//设置excel头部样式

		HSSFCellStyle style2 = getHSSFCellStyle(workbook);//设置内容样式

		HSSFPatriarch patriarch = createHSSFPatriarch(sheet);//创建图像处理器

		HSSFRow row = sheet.createRow(0);
		setHeadData(row,headers,style);//设置列头数据

		// 遍历集合数据，产生数据行
		Iterator<M> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			if(index%totalRows == 0){
				sheet = workbook.createSheet();
				row = sheet.createRow(0);
				patriarch = createHSSFPatriarch(sheet);
				setHeadData(row,headers,style);//设置列头数据
				index = 1;
			}
			row = sheet.createRow(index);
			M t = (M) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = "";
					if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
								index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {

						// 判断值的类型后进行强制类型转换
						if(value==null){
							textValue="";
						}else if(value instanceof java.sql.Timestamp){
							//时间戳
							textValue=value.toString()+"";
						}else if(value instanceof Date){
							//date型时间
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							textValue = formatter.format(value);
						}else{
							//其他情况简单处理
							textValue=value==null?"":value.toString();
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		return workbook;
	}

	public static void main(String[] args) {

	}
	/**
	 * 创建图像处理器
	 * @param sheet
     */
	private HSSFPatriarch createHSSFPatriarch(HSSFSheet sheet){
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");
		
		return patriarch;
	}

	/**
	 * 设置列头数据
	 * @param row
	 * @param headers
	 * @param style
     */
	private void setHeadData(HSSFRow row, String[] headers, HSSFCellStyle style){

		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	}

	/**
	 * 设置excel头部样式
	 * @param workbook
     */
	private HSSFCellStyle getHeadHSSFWorkbookStyle(HSSFWorkbook workbook){
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}

	/**
	 * 返回一个cellstyle
	 * @param workbook
	 * @return
     */
	private HSSFCellStyle getHSSFCellStyle(HSSFWorkbook workbook){
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		return style2;
	}

	/**
	 * 创建excel
	 *  
	 * @param title
	 * @param headers 第一个参数key为字段名，后一个参数为对应的中文标题名，有序
	 * @param dataset
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static HSSFWorkbook createExcelBook(String title, LinkedHashMap<String, String> headers, List<Map> dataset, boolean withTime) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		int cellIndex = 0;
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			HSSFCell cell = row.createCell(cellIndex);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(entry.getValue());
			cell.setCellValue(text);
			cellIndex++;
		}

		// 遍历集合数据，产生数据行
		// Iterator<T> it = dataset.iterator();

		int totalRow = 0;
		try {
			for (int i = 0; dataset != null && i < dataset.size(); i++) {
				totalRow++;
				// 遍历行
				Map rowMap = dataset.get(i);
				if (rowMap != null && !rowMap.isEmpty()) {
					row = sheet.createRow(totalRow);
					// 遍历列
					int j = 0;
					for (Map.Entry<String, String> entry : headers.entrySet()) {
						HSSFCell cell = row.createCell(j);
						j++;
						cell.setCellStyle(style2); 
						if(StringUtils.isBlank(entry.getKey())){
							continue;
						}   
						Object value=rowMap.get(entry.getKey());
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if(value==null){
							textValue="";
						}else if(entry.getKey().matches(".*((date)|(dt)|(time))$")){
							//时间格式
							if(value.getClass().toString().toLowerCase().equals("java.sql.timestamp"))
							{
								//时间戳
								textValue=value.toString()+""; 
							}else if(value.getClass().toString().toLowerCase().equals("java.lang.long"))
							{
								//long型时间
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								textValue = formatter.format(new Date(((Long) value)));
							}else if(value.getClass().toString().toLowerCase().equals("java.util.date"))
							{
								//date型时间
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								textValue = formatter.format(value);
							}else{
								//其他日期转字符型
								textValue=value.toString()+"";
							}
							//过滤时间长度
							if(textValue.length()>19){
								textValue=textValue.substring(0, 19);
							}
							if(textValue.length()>10&&!withTime){
								textValue=textValue.substring(0, 10);
							}
						}else{
							//其他情况简单处理
							textValue=value==null?"":value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				}

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// 清理资源

		}
		return workbook;
	}

	/**
	 * 创建excel
	 *
	 * @param title
	 * @param headersList 第一个参数key为字段名，后一个参数为对应的中文标题名，有序
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static HSSFWorkbook createExcelBook(String title, Map<String, List> headersList, List<Map> dataList, List<Map> footerList, int footerStart) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");
		// 产生表格标题行
		int totalRow = 0;
		int endCol=0;
		HSSFRow row=null;
		for(String key:headersList.keySet()){
			List<String> headerList=headersList.get(key);
			row = sheet.createRow(totalRow);
			totalRow++;
			int cellIndex = 0;
			for(String str:headerList){
				HSSFCell cell = row.createCell(cellIndex);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(str);
				cell.setCellValue(text);
				cellIndex++;
			}
			endCol=cellIndex;
		}
		//合并标题
		ExcelCellsCoalition.coalitionTable(sheet, 0, totalRow-1, 0, endCol-1);
		// 遍历集合数据，产生数据行
		try {
			for(Map dataMap:dataList){
				//行
				Collection values=dataMap.values();
				row = sheet.createRow(totalRow);
				totalRow++;
				int j=0;
				for(Object value:values){
					//列
					HSSFCell cell = row.createCell(j);
//					cell.setCellStyle(style2);
					j++;
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if(value==null){
						textValue="";
					}else if(value instanceof java.sql.Timestamp){
						//时间戳
						textValue=value.toString()+"";
					}else if(value instanceof Date){
						//date型时间
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						textValue = formatter.format(value); 
					}else{
						//其他情况简单处理
						textValue=value==null?"":value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					Pattern p = Pattern.compile("^//d+(//.//d+)?$");
					Matcher matcher = p.matcher(textValue);
					if (matcher.matches()) {
						// 是数字当作double处理
						cell.setCellValue(Double.parseDouble(textValue));
					} else {
						HSSFRichTextString richString = new HSSFRichTextString(textValue);
						cell.setCellValue(richString);
					}  
				}
			}
			//生成合计列
			for(Map dataMap:footerList){
				if(!dataMap.isEmpty()){
					//行
					Collection values=dataMap.values();
					row = sheet.createRow(totalRow);
					totalRow++;
					for(int j=0;j<footerStart;j++){
						HSSFCell cell = row.createCell(j);
//						cell.setCellStyle(style2);
					}
					int j=footerStart;
					for(Object value:values){
						//列
						HSSFCell cell = row.createCell(j);
//						cell.setCellStyle(style2);
						j++;
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if(value==null){
							textValue="";
						}else{
							//其他情况简单处理
							textValue=value==null?"":value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}  
					}
				}
				
			}
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// 清理资源

		}
		return workbook;
	}
	

	/**
	 * 导入Excel,支持xls和xlsx格式
	 * 
	 * @param file
	 * @param entity
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> readExcel(MultipartFile file, Class<T> entity, List<String> list) throws Exception {
		List<T> resultList = new ArrayList<T>();
		// 判断是否是excel2007格式
		// 2007xlsx true, 2003xls false, 其他格返回null
		boolean isE2007 = false;
		if (file.getOriginalFilename().endsWith("xls")) {
			// isE2007 = false;
		} else if (file.getOriginalFilename().endsWith("xlsx")) {
			isE2007 = true;
		} else {
			return null;
		}
		try {
			InputStream input = file.getInputStream();// 建立输入流
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			while (rows.hasNext()) {
				Row row = rows.next(); // 获得行数据
				if (row.getRowNum() == 0) {
					continue;
				}
				T obj = (T) entity.newInstance();
				Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
				int cellIndex = 0;
				while (cells.hasNext()) {
					Cell cell = cells.next();
					String result = "";
					if (cell != null) {
						switch (cell.getCellType()) { // 根据cell中的类型来输出数据
						case HSSFCell.CELL_TYPE_NUMERIC:
							result += cell.getNumericCellValue();
							break;
						case HSSFCell.CELL_TYPE_STRING:
							result += cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							result += cell.getBooleanCellValue();
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							result += cell.getCellFormula();
							break;
						default:
							break;
						}
					}
					Method[] methodArr = entity.getMethods();
					String field = list.get(cellIndex);
					String fieldName = field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
					fieldName = "set" + fieldName;
					for (Method m : methodArr) {
						if (m.getName().equals(fieldName)) {
							m.invoke(obj, result);
						}
					}
					cellIndex++;
				}
				resultList.add(obj);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return resultList;
	}
	
	

	/**
	 * @author yenbay 导入Excel,支持xls和xlsx格式 其中返回第一行一般为标题，请自行处理
	 * @param file
	 * @param fieldList
	 *            字段列表
	 * @param dtformat
	 *            日期格式，可以为null，默认未yyyy-MM-dd HH:mm:ss格式
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readExcelToMap(MultipartFile file, List<String> fieldList, String dtformat)
			throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<>();
		// 判断是否是excel2007格式
		// 2007xlsx true, 2003xls false, 其他格返回null
		boolean isE2007 = false;
		if (file.getOriginalFilename().endsWith("xls")) {
			// isE2007 = false;
		} else if (file.getOriginalFilename().endsWith("xlsx")) {
			isE2007 = true;
		} else {
			return null;
		}
		String format = dtformat == null ? dateTimeFormate : dtformat;
		try {
			InputStream input = file.getInputStream();// 建立输入流
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			int colMaxNum = fieldList.size();// 行的最大列数

			while (rows.hasNext()) {
				Row row = rows.next(); // 获得行数据
				Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
				int cellIndex = 0;
				Map<String, Object> rowMap = new HashMap<>();
				while (cells.hasNext() && cellIndex < colMaxNum) {
					Cell cell = cells.next();
					if (cell != null) {
						cellIndex = cell.getColumnIndex();
						// rowMap.put(String.valueOf(cellIndex),getCellFormatValue(cell));
						rowMap.put(fieldList.get(cellIndex).toString(), getCellFormatValue(cell, format));
					}
					cellIndex++;
				}
				resultList.add(rowMap);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(Cell cell, String format) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					cellvalue = sdf.format(date);
				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellvalue = String.valueOf(cell.getBooleanCellValue());
				break;
				// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
				// 默认的Cell值
			default:
				cellvalue = " ";
			}
		}
		return cellvalue;
	}

	/**
	 * 修改Excel单元格
	 * 
	 * @param file
	 * @param row
	 * @param col
	 * @param val
	 */
	public static void editExcel(File file, int row, int col, String val) {
		boolean isE2007 = false;
		if (file.getName().endsWith("xls")) {

		} else if (file.getName().endsWith("xlsx")) {
			isE2007 = true;
		} else {
			return;
		}
		try {
			FileInputStream input = new FileInputStream(file);
			Workbook wb = null;
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);
			Row r = sheet.getRow(row);
			Cell cell = r.createCell(col);
			cell.setCellValue(val);
			input.close();
			try (FileOutputStream fos = new FileOutputStream(file)) {
				wb.write(fos);
				fos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}