package com.lmdestiny.surveypark.model.statistics;

/**
 *选项统计模型 
 */
public class OptionStatisticsModel {
	//选项的索引
	private int optionIndex;
	//选项的标签
	private String optionLabel;
	//选项的回答人数
	private int count;
	private int matrixRowIndex;
	private String matrixRowLable;
	private int matrixColIndex;
	private String matrixColLabel;
	private String matrixSelectLablel;
	private int matrixSelectIndex;
	
	public int getOptionIndex() {
		return optionIndex;
	}
	public void setOptionIndex(int optionIndex) {
		this.optionIndex = optionIndex;
	}
	public String getOptionLabel() {
		return optionLabel;
	}
	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getMatrixRowIndex() {
		return matrixRowIndex;
	}
	public void setMatrixRowIndex(int matrixRowIndex) {
		this.matrixRowIndex = matrixRowIndex;
	}
	public String getMatrixRowLable() {
		return matrixRowLable;
	}
	public void setMatrixRowLable(String matrixRowLable) {
		this.matrixRowLable = matrixRowLable;
	}
	public int getMatrixColIndex() {
		return matrixColIndex;
	}
	public void setMatrixColIndex(int matrixColIndex) {
		this.matrixColIndex = matrixColIndex;
	}
	public String getMatrixColLabel() {
		return matrixColLabel;
	}
	public void setMatrixColLabel(String matrixColLabel) {
		this.matrixColLabel = matrixColLabel;
	}
	public int getMatrixSelectIndex() {
		return matrixSelectIndex;
	}
	public void setMatrixSelectIndex(int matrixSelectIndex) {
		this.matrixSelectIndex = matrixSelectIndex;
	}
	public String getMatrixSelectLablel() {
		return matrixSelectLablel;
	}
	public void setMatrixSelectLablel(String matrixSelectLablel) {
		this.matrixSelectLablel = matrixSelectLablel;
	}
}
