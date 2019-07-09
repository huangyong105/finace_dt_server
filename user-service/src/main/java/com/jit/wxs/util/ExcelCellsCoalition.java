package com.jit.wxs.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
   * @类 名： ExcelCellsCoalition
   * @功能描述： TODO
   * @作者信息：zhaojw
   * @创建时间： 2017年3月2日下午3:43:06
   * @修改备注：
   */
public class ExcelCellsCoalition
{
	public ExcelCellsCoalition()
	{
	}

	/**
	 * 判断两个区域是否存在交集
	 * 
	 * @param r1
	 * @param r
	 * @return
	 */
	private static boolean isCross(CellRangeAddress r1, CellRangeAddress r)
	{
		if ((r.getFirstColumn() >= r1.getFirstColumn() && r.getFirstColumn() <= r1.getLastColumn())
		        || (r.getLastColumn() >= r1.getFirstColumn() && r.getLastColumn() <= r1.getLastColumn()))
		{
			if ((r.getFirstRow() >= r1.getFirstRow() && r.getFirstRow() <= r1.getLastRow())
			        || (r.getLastRow() >= r1.getFirstRow() && r.getLastRow() <= r1.getLastRow()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否需要新创建一个区域，如果不需要就修改原来区域的边界
	 * 
	 * @param list
	 * @param r
	 */
	private static void addCellRange(List<CellRangeAddress> list, CellRangeAddress r)
	{
		if (list.size() == 0)
		{
			list.add(r);
			return;
		}
		for (int i = 0; i < list.size(); i++)
		{
			CellRangeAddress r1 = list.get(i);
			if (ExcelCellsCoalition.isCross(r1, r))
			{
				int firstRow = r.getFirstRow() > r1.getFirstRow() ? r1.getFirstRow() : r.getFirstRow();
				int lastRow = r.getLastRow() > r1.getLastRow() ? r.getLastRow() : r1.getLastRow();
				int firstCol = r.getFirstColumn() > r1.getFirstColumn() ? r1.getFirstColumn() : r.getFirstColumn();
				int lastCol = r.getLastColumn() > r1.getLastColumn() ? r.getLastColumn() : r1.getLastColumn();
				r1.setFirstColumn(firstCol);
				r1.setLastColumn(lastCol);
				r1.setFirstRow(firstRow);
				r1.setLastRow(lastRow);
				return;
			}
		}
		list.add(r);
	}

	/**
	 * 合并单元格，兼容WPS，WPS表格的不支持多次合并同一个单元格，需要一次性统计需要合并的区域
	 * 
	 * @param sheet
	 *            工作表
	 * @param startrows
	 *            开始行
	 * @param endrows
	 *            结束行
	 * @param statcols
	 *            开始列
	 * @param endcols
	 *            结束列
	 */
	public static void wpsCoalitionTable(HSSFSheet sheet, int startrows, int endrows, int statcols, int endcols)
	{
		if (sheet.getRow(startrows).getLastCellNum() < endcols)
		{
			endcols = sheet.getRow(startrows).getLastCellNum();
		}
		if (sheet.getLastRowNum() < endrows)
		{
			endrows = sheet.getLastRowNum();
		}
		// 扫描相等的区域，如果相邻的两个但愿格内容相同讲会被合并
		HSSFCell cell_temp;
		String cell_value = "";
		HSSFCell cell;
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		/**
		 * 扫描一行中需要合并的区间
		 */
		for (int x = startrows; x <= endrows; x++)
		{
			cell_temp = sheet.getRow(x).getCell(statcols);
			if (cell_temp.getCellType() == HSSFCell.CELL_TYPE_STRING)
			{
				cell_value = cell_temp.getStringCellValue().trim();
			}
			else
			{
				cell_value = String.valueOf(cell_temp.getNumericCellValue()).trim();
			}
			cell_value = cell_value.replaceAll(" ", "");
			for (int k = statcols + 1; k <= endcols; k++)
			{
				cell = sheet.getRow(x).getCell(k);
				if (Objects.nonNull(cell) && cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					if (Objects.nonNull(cell) && Objects.nonNull(cell.getStringCellValue()) && cell.getStringCellValue().trim().equals(cell_value))
					{
						CellRangeAddress r = new CellRangeAddress(x, x, (short) (k - 1), (short) k);
						ExcelCellsCoalition.addCellRange(list, r);
						// list.add(r);
					}
					cell_value = cell.getStringCellValue().trim();
				}
				else
				{
					if (cell != null)
					{
						String value = String.valueOf(cell.getNumericCellValue()).trim();
						value = value.replaceAll(" ", "");
						if (value != null && value.equals(cell_value))
						{
							CellRangeAddress r = new CellRangeAddress(x, x, (short) (k - 1), (short) k);
							ExcelCellsCoalition.addCellRange(list, r);
							// list.add(r);
						}
						cell_value = value;
					}
				}
				cell_temp = cell;
			}
		}
		/**
		 * 扫描一列中需要合并的区间
		 */
		for (int i = statcols; i <= endcols; i++)
		{
			cell_temp = sheet.getRow(startrows).getCell(i);
			if (cell_temp.getCellType() == HSSFCell.CELL_TYPE_STRING)
			{
				cell_value = cell_temp.getStringCellValue().trim();
			}
			else
			{
				cell_value = String.valueOf(cell_temp.getNumericCellValue()).trim();
			}
			cell_value = cell_value.replaceAll(" ", "");
			for (int k = startrows + 1; k <= endrows; k++)
			{
				cell = sheet.getRow(k).getCell(i);
				if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					if (cell != null && cell.getStringCellValue().trim().equals(cell_temp.getStringCellValue()))
					{
						CellRangeAddress r = new CellRangeAddress(k - 1, k, (short) i, (short) i);
						ExcelCellsCoalition.addCellRange(list, r);
						// list.add(r);
					}
					cell_value = cell.getStringCellValue().trim();
				}
				else
				{
					if (cell != null)
					{
						String value = String.valueOf(cell.getNumericCellValue()).trim();
						value = value.replaceAll(" ", "");
						if (value != null && value.equals(cell_value))
						{
							CellRangeAddress r = new CellRangeAddress(k - 1, k, (short) i, (short) i);
							ExcelCellsCoalition.addCellRange(list, r);
							// list.add(r);
						}
						cell_value = value;
					}

				}
				cell_temp = cell;
			}
		}

		/**
		 * 扫描是否有重复的区域
		 */
		/*
		 * boolean isDelete = false; for(int i = list.size(); i > 0; i--) {
		 * CellRangeAddress r = (CellRangeAddress)list.get(i-1); for(int k = 0;
		 * k < i - 1; k++) { isDelete = false; CellRangeAddress r1 =
		 * (CellRangeAddress)list.get(k); if(r.getFirstColumn() <
		 * r1.getFirstColumn() && r.getLastColumn() > r1.getLastColumn()) {
		 * r1.setFirstColumn(r.getFirstColumn()); isDelete = true; }
		 * if(r.getFirstColumn() < r1.getLastColumn() && r.getLastColumn() >
		 * r1.getLastColumn()) { r1.setLastColumn(r.getLastColumn()); isDelete =
		 * true; } if(r.getFirstRow() < r1.getFirstRow() && r.getLastRow() >
		 * r1.getFirstRow()) { r1.setFirstRow(r.getFirstRow()); isDelete = true;
		 * } if(r.getFirstRow() < r1.getLastRow() && r.getLastRow() >
		 * r1.getLastRow()) { r1.setLastRow(r.getLastRow()); isDelete = true; }
		 * if(isDelete) { list.remove(r); } } }
		 */

		/**
		 * 执行合并操作
		 */
		for (int i = 0; i < list.size(); i++)
		{
			CellRangeAddress r = (CellRangeAddress) list.get(i);
			int index = 0;
			for (int k = r.getFirstRow(); k <= r.getLastRow(); k++)
			{
				for (int l = r.getFirstColumn(); l <= r.getLastColumn(); l++)
				{
					if (index > 0)
					{
						sheet.getRow(k).getCell(l).setCellValue("");
					}
					index++;
				}
			}
			// System.out.println(r.getFirstRow() + "_"+ r.getLastRow() + "_"+
			// r.getFirstColumn() + "_"+ r.getLastColumn());
			// Region r2 = new Region(r.getFirstRow(),
			// (short)r.getFirstColumn(), r.getLastRow(),(short)
			// r.getLastColumn());
			sheet.addMergedRegion(r);
		}
		// sheet.addMergedRegion(new
		// CellRangeAddress(0,0,0,sheet.getRow(startrows).getLastCellNum()));
		// sheet.addMergedRegion(new CellRangeAddress(1,1,0,(short)1));
		// sheet.addMergedRegion(new CellRangeAddress(2,2,0,(short)1));

		// 设置横向打印
		sheet.getPrintSetup().setLandscape(true);
		// 设置打印纸为A4
		sheet.getPrintSetup().setPaperSize(sheet.getPrintSetup().A4_PAPERSIZE);
		// 页码
		sheet.getPrintSetup().setUsePage(true);
		// 水平居中
		sheet.setHorizontallyCenter(true);

		// 设置页面边框
		sheet.setMargin((short) 0, 0.25);
		sheet.setMargin((short) 1, 0.25);
		sheet.setMargin((short) 2, 1);
		sheet.setMargin((short) 3, 1);
	}

	/**
	 * 合并单元格 将需要合并的范围内，相邻的两个值相同的单元格合并
	 * 
	 * @param sheet
	 *            HSSFSheet 需要合并的工作表
	 * @param startrows
	 *            int 开始行
	 * @param endrows
	 *            int 结束行
	 * @param statcols
	 *  	int 开始列
	 * @param endcols
	 *            int 结束列
	 * @param cols
	 *            int 总列数
	 * @return HSSFSheet
	 */
	@Deprecated
	public static HSSFSheet coalitionTable(HSSFSheet sheet, int startrows, int endrows, int statcols, int endcols)
	{
		try {
			/**
			 * 行合并 
			 */
			for(int i=startrows;i<=endrows;i++){
				HSSFRow rows = sheet.getRow(i);
				HSSFCell cell = rows.getCell(statcols);
				if(cell==null){
					continue;
				}
				String name1=cell.getStringCellValue();
				for(int j=statcols+1;j<=endcols;j++){
					cell=rows.getCell(j);
					if(cell==null){
						continue;
					}
					String name2=cell.getStringCellValue();
					if(name2.equals(name1) || "".equals(name2)){
						sheet.addMergedRegion(new CellRangeAddress(i, i, j-1, j));
					}
					name1=name2;
				}
			}
			/**
			 * 列合并
			 */
			for(int i=statcols;i<=endcols;i++){
				HSSFRow rows= sheet.getRow(startrows);
				HSSFCell cell = rows.getCell(i);
				if(cell==null){
					continue;
				}
				String name1=cell.getStringCellValue();
						
				for(int j=startrows+1;j<=endrows;j++){
					rows=sheet.getRow(j);
					cell=rows.getCell(i);
					if(cell==null){
						continue;
					}
					String name2=cell.getStringCellValue();
					if(name2.equals(name1) || "".equals(name2)){
						sheet.addMergedRegion(new CellRangeAddress(j-1,j,i,i));
					}
					name1=name2;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		for (int x = startrows; x <= endrows; x++)
//		{
//			HSSFRow rows = sheet.getRow(x);
//			HSSFCell cell = rows.getCell(endcols);
//			String name1 = "";
//			if (cell != null)
//			{
//				name1 = cell.getStringCellValue();
//			}
//			for (int l = endcols - 1; l > statcols; l--)
//			{
//				String name = (rows.getCell(l)).getStringCellValue();
//				if (name.equals(name1) || "".equals(name))
//				{
//					// sheet.addMergedRegion(new Region(x, (short)l, x,
//					// (short)(l + 1)));
//					// 将后面重复的单元格设置成空字符串
//					// rows.getCell((short)(l + 1)).setCellValue("");
//
//					sheet.addMergedRegion(new CellRangeAddress(x, x, l, (l + 1)));
//				}
//				name1 = name;
//			}
//		}

//		/**
//		 * 列合并
//		 */
//		int sumrow = endrows;
//		for (int l = endcols - 1; l >= statcols; l--)
//		{
//			HSSFRow r = sheet.getRow(sumrow - 1);
//			String name1 = "";
//			HSSFCell cell = r.getCell(endcols);
//			if (cell != null)
//			{
//				name1 = cell.getStringCellValue();
//			}
//			for (int x = sumrow; x >= startrows; x--)
//			{
//				HSSFRow rows = sheet.getRow(x);
//				String name = "";
//				name = (rows.getCell(l)).getStringCellValue();
//				if (name.equals(name1) && (!"".equals(name1) || l != 0))
//				{
//					sheet.addMergedRegion(new CellRangeAddress(x, x + 1, l, l));
//				}
//				name1 = name;
//
//			}
//		}
//
//		// 页码
//		sheet.getPrintSetup().setUsePage(true);
//		// 水平居中
//		sheet.setHorizontallyCenter(true);
//
//		// 设置页面边框
//		sheet.setMargin((short) 0, 0.6);
//		sheet.setMargin((short) 1, 0.6);
//		sheet.setMargin((short) 2, 0.8);
//		sheet.setMargin((short) 3, 0.8);
		return sheet;
	}
	
}