/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.syn.vo;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.fes.vo.FesMarketingActivityVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.system.vo.SysCustomerComplaintVO;
import com.yazuo.erp.system.vo.SysProblemVO;
import com.yazuo.erp.system.vo.SysToDoListVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class SynMerchantVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "商户";
	public static final String ALIAS_MERCHANT_ID = "商户ID";
	public static final String ALIAS_MERCHANT_NAME = "商户名称";
	public static final String ALIAS_BRAND = "品牌名称";
	public static final String ALIAS_MERCHANT_NO = "商户编号";
	public static final String ALIAS_IS_FACE_SHOP = "是否实体店";
	public static final String ALIAS_AD_COLUMN = "广告";
	public static final String ALIAS_PROMPT_BAR = "推广";
	public static final String ALIAS_PARENT = "父商户ID";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_BRAND_SHORT_PINYIN = "集团简称";
	public static final String ALIAS_BRAND_ID = "集团id";
	public static final String ALIAS_THIRDPARTY_MERCHANT_NO = "第三方商户号";
	public static final String ALIAS_SERVICE_YEAR = "服务年份";
	public static final String ALIAS_FREE_MONTH = "门店赠送月份";
	public static final String ALIAS_SERVICE_START_TIME = "服务开始时间";
	public static final String ALIAS_SERVICE_END_TIME = "服务结束时间";
	public static final String ALIAS_MERCHANT_STATUS = "上线状态";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_ATTACHMENT_ID = "attachmentId";
	public static final String COLUMN_MERCHANT_ID = "merchantId";
	public static final String COLUMN_MERCHANT_NAME = "merchantName";
	public static final String COLUMN_BRAND = "brand";
	public static final String COLUMN_MERCHANT_NO = "merchantNo";
	public static final String COLUMN_IS_FACE_SHOP = "isFaceShop";
	public static final String COLUMN_AD_COLUMN = "adColumn";
	public static final String COLUMN_PROMPT_BAR = "promptBar";
	public static final String COLUMN_PARENT = "parent";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_BRAND_SHORT_PINYIN = "brandShortPinyin";
	public static final String COLUMN_BRAND_ID = "brandId";
	public static final String COLUMN_THIRDPARTY_MERCHANT_NO = "thirdpartyMerchantNo";
	public static final String COLUMN_SERVICE_YEAR = "serviceYear";
	public static final String COLUMN_FREE_MONTH = "freeMonth";
	public static final String COLUMN_SERVICE_START_TIME = "serviceStartTime";
	public static final String COLUMN_SERVICE_END_TIME = "serviceEndTime";
	public static final String COLUMN_MERCHANT_STATUS = "merchantStatus";
	public static final String COLUMN_UPDATE_TIME = "updateTime";
	public static final String COLUMN_ATTACHMENT_ID = "attachmentId";

	//columns START
	private java.lang.Integer merchantId; //"商户ID";
	private java.lang.String merchantName; //"商户名称";
	private java.lang.String brand; //"品牌名称";
	private java.lang.String merchantNo; //"商户编号";
	private java.lang.Boolean isFaceShop; //"是否实体店";
	private java.lang.String adColumn; //"广告";
	private java.lang.String promptBar; //"推广";
	private java.lang.Integer parent; //"父商户ID";
	private Integer status; //"状态";
	private java.lang.String brandShortPinyin; //"集团简称";
	private java.lang.Integer brandId; //"集团id";
	private java.lang.String thirdpartyMerchantNo; //"第三方商户号";
	private java.math.BigDecimal serviceYear; //"服务年份";
	private java.math.BigDecimal freeMonth; //"门店赠送月份";
	private java.util.Date serviceStartTime; //"服务开始时间";
	private java.util.Date serviceEndTime; //"服务结束时间";
	private java.lang.String merchantStatus; //"上线状态";
	private java.util.Date updateTime; //"最后修改时间";
	private java.lang.Integer attachmentId; //"attachmentId";
	//columns END
	
	public java.lang.Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(java.lang.Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	private Set sysCustomerComplaints = new HashSet(0);
	public void setFesCustomerComplaints(Set<SysCustomerComplaintVO> fesCustomerComplaint){
		this.sysCustomerComplaints = fesCustomerComplaint;
	}
	
	public Set<SysCustomerComplaintVO> getFesCustomerComplaints() {
		return sysCustomerComplaints;
	}
	
	private Set fesOnlinePrograms = new HashSet(0);
	public void setFesOnlinePrograms(Set<FesOnlineProgramVO> fesOnlineProgram){
		this.fesOnlinePrograms = fesOnlineProgram;
	}
	
	public Set<FesOnlineProgramVO> getFesOnlinePrograms() {
		return fesOnlinePrograms;
	}
	
	private Set fesMarketingActivitys = new HashSet(0);
	public void setFesMarketingActivitys(Set<FesMarketingActivityVO> fesMarketingActivity){
		this.fesMarketingActivitys = fesMarketingActivity;
	}
	
	public Set<FesMarketingActivityVO> getFesMarketingActivitys() {
		return fesMarketingActivitys;
	}
	
	private Set sysProblems = new HashSet(0);
	public void setSysProblems(Set<SysProblemVO> sysProblem){
		this.sysProblems = sysProblem;
	}
	
	public Set<SysProblemVO> getSysProblems() {
		return sysProblems;
	}
	
	private Set fesOpenCardApplications = new HashSet(0);
	public void setFesOpenCardApplications(Set<FesOpenCardApplicationVO> fesOpenCardApplication){
		this.fesOpenCardApplications = fesOpenCardApplication;
	}
	
	public Set<FesOpenCardApplicationVO> getFesOpenCardApplications() {
		return fesOpenCardApplications;
	}
	
	private Set sysToDoLists = new HashSet(0);
	public void setSysToDoLists(Set<SysToDoListVO> sysToDoList){
		this.sysToDoLists = sysToDoList;
	}
	
	public Set<SysToDoListVO> getSysToDoLists() {
		return sysToDoLists;
	}
	
	private Set fesPlans = new HashSet(0);
	public void setFesPlans(Set<FesPlanVO> fesPlan){
		this.fesPlans = fesPlan;
	}
	
	public Set<FesPlanVO> getFesPlans() {
		return fesPlans;
	}

	public SynMerchantVO(){
	}

	public SynMerchantVO(
		java.lang.Integer merchantId
	){
		this.merchantId = merchantId;
	}

	public void setMerchantId(java.lang.Integer value) {
		this.merchantId = value;
	}
	
	public java.lang.Integer getMerchantId() {
		return this.merchantId;
	}
	public void setMerchantName(java.lang.String value) {
		this.merchantName = value;
	}
	
	public java.lang.String getMerchantName() {
		return this.merchantName;
	}
	public void setBrand(java.lang.String value) {
		this.brand = value;
	}
	
	public java.lang.String getBrand() {
		return this.brand;
	}
	public void setMerchantNo(java.lang.String value) {
		this.merchantNo = value;
	}
	
	public java.lang.String getMerchantNo() {
		return this.merchantNo;
	}
	public void setIsFaceShop(java.lang.Boolean value) {
		this.isFaceShop = value;
	}
	
	public java.lang.Boolean getIsFaceShop() {
		return this.isFaceShop;
	}
	public void setAdColumn(java.lang.String value) {
		this.adColumn = value;
	}
	
	public java.lang.String getAdColumn() {
		return this.adColumn;
	}
	public void setPromptBar(java.lang.String value) {
		this.promptBar = value;
	}
	
	public java.lang.String getPromptBar() {
		return this.promptBar;
	}
	public void setParent(java.lang.Integer value) {
		this.parent = value;
	}
	
	public java.lang.Integer getParent() {
		return this.parent;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setBrandShortPinyin(java.lang.String value) {
		this.brandShortPinyin = value;
	}
	
	public java.lang.String getBrandShortPinyin() {
		return this.brandShortPinyin;
	}
	public void setBrandId(java.lang.Integer value) {
		this.brandId = value;
	}
	
	public java.lang.Integer getBrandId() {
		return this.brandId;
	}
	public void setThirdpartyMerchantNo(java.lang.String value) {
		this.thirdpartyMerchantNo = value;
	}
	
	public java.lang.String getThirdpartyMerchantNo() {
		return this.thirdpartyMerchantNo;
	}
	public void setServiceYear(java.math.BigDecimal value) {
		this.serviceYear = value;
	}
	
	public java.math.BigDecimal getServiceYear() {
		return this.serviceYear;
	}
	public void setFreeMonth(java.math.BigDecimal value) {
		this.freeMonth = value;
	}
	
	public java.math.BigDecimal getFreeMonth() {
		return this.freeMonth;
	}
	public void setServiceStartTime(java.util.Date value) {
		this.serviceStartTime = value;
	}
	
	public java.util.Date getServiceStartTime() {
		return this.serviceStartTime;
	}
	public void setServiceEndTime(java.util.Date value) {
		this.serviceEndTime = value;
	}
	
	public java.util.Date getServiceEndTime() {
		return this.serviceEndTime;
	}
	public void setMerchantStatus(java.lang.String value) {
		this.merchantStatus = value;
	}
	
	public java.lang.String getMerchantStatus() {
		return this.merchantStatus;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("MerchantId",getMerchantId())
			.append("MerchantName",getMerchantName())
			.append("Brand",getBrand())
			.append("MerchantNo",getMerchantNo())
			.append("IsFaceShop",getIsFaceShop())
			.append("AdColumn",getAdColumn())
			.append("PromptBar",getPromptBar())
			.append("Parent",getParent())
			.append("Status",getStatus())
			.append("BrandShortPinyin",getBrandShortPinyin())
			.append("BrandId",getBrandId())
			.append("ThirdpartyMerchantNo",getThirdpartyMerchantNo())
			.append("ServiceYear",getServiceYear())
			.append("FreeMonth",getFreeMonth())
			.append("ServiceStartTime",getServiceStartTime())
			.append("ServiceEndTime",getServiceEndTime())
			.append("MerchantStatus",getMerchantStatus())
			.append("AttachmentId",getAttachmentId())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMerchantId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SynMerchantVO == false) return false;
		if(this == obj) return true;
		SynMerchantVO other = (SynMerchantVO)obj;
		return new EqualsBuilder()
			.append(getMerchantId(),other.getMerchantId())
			.isEquals();
	}
}

