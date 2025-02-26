package com.ntou.db.cuscredit;

import com.ntou.tool.JsonTool;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(CuscreditKey.class)
@Table(name="cuscredit",schema="dbo")
public class Cuscredit {
	@Column(name="chName") private String chName					= "";//VARCHAR(50)	中文姓名
	@Column(name="enName") private String enName					= "";//VARCHAR(50)	VARCHAR(50)
	@Id @Column(name="cid") private String cid  					= "";//VARCHAR(20)	使用者身分證字號(限本國人士)
	@Column(name="cidReissueDate") private String cidReissueDate			= "";//VARCHAR(10)	身分證換發日期 民國yyy/mm/dd
	@Column(name="cidReissueLocation") private String cidReissueLocation		= "";//VARCHAR(10)	身分證換發地點
	@Column(name="cidReissueStatus") private String cidReissueStatus			= "";//VARCHAR(5)	身分證換發狀態
	@Column(name="birthDate") private String birthDate				= "";//VARCHAR(10)	出生日期西元年月日 yyyy/mm/dd
	@Column(name="maritalStatus") private String maritalStatus			= "";//VARCHAR(2)	婚姻；已婚：01、未婚：02
	@Column(name="education") private String education				= "";//VARCHAR(2)	學歷；00：無、01：國小、02：國中、03：高中、04：大學、05：碩士、06：博士
	@Column(name="currentResidentialAdd") private String currentResidentialAdd	= "";//VARCHAR(255)	現居住址
	@Column(name="residentialAdd") private String residentialAdd			= "";//VARCHAR(255)	戶籍地址
	@Column(name="cellphone") private String cellphone				= "";//VARCHAR(10)	連絡手機號碼
	@Column(name="email") private String email					= "";//VARCHAR(100)	連絡電子信箱
	@Column(name="companyName") private String companyName				= "";//VARCHAR(100)	申請人公司名稱
	@Column(name="companyIndustry") private String companyIndustry			= "";//VARCHAR(2)	申請人行業別(01：服務業、02：工業、03：金融保險業)
	@Column(name="occupation") private String occupation				= "";//VARCHAR(10)	申請人職業
	@Column(name="department") private String department				= "";//VARCHAR(10)	申請人部門
	@Column(name="jobTitle") private String jobTitle				    = "";//VARCHAR(10)	申請人職稱
	@Column(name="dateOfEmployment") private String dateOfEmployment		    = "";//VARCHAR(10)	到職日
	@Column(name="companyAddress") private String companyAddress			= "";//VARCHAR(255)	公司地址
	@Column(name="companyPhoneNum") private String companyPhoneNum			= "";//VARCHAR(20)	電話021234567#3654
	@Column(name="annualIncome") private String annualIncome				= "";//VARCHAR(5)	年收入/萬
	@Column(name="cardApprovalStatus") private String cardApprovalStatus		= "";//VARCHAR(2)	信用卡審核狀態；00：處理中、01：審核過、02：審核不通過
	@Column(name="ApplyRemark") private String ApplyRemark				= "";//VARCHAR(20)	信用卡申請狀態備註，如審核不通過的原因
	@Column(name="activationRecord") private String activationRecord			= "";//VARCHAR(2)	信用卡開卡紀錄(00：未開卡、01：已開卡)
	@Column(name="eventCode") private String eventCode				= "";//VARCHAR(10)	活動代碼
	@Column(name="regidate") private String regidate 				= "";//VARCHAR(23)	信用卡銀行通過(核發)時間yyyy/MM/dd HH:mm:ss.SSS
	@Column(name="issuing_bank") private String issuing_bank 			= "";//VARCHAR(15)	核卡銀行(swiftCode)
	@Column(name="cardNum") private String cardNum					= "";//VARCHAR(20)	信用卡號碼
	@Column(name="securityCode") private String securityCode				= "";//VARCHAR(5)	信用卡安全碼
	@Column(name="status") private String status					= "";//VARCHAR(2)	信用卡狀態(00：正常,99：註銷)
	@Id @Column(name="cardType") private String cardType					= "";//VARCHAR(5)	卡別
	@Column(name="remark") private String remark					= "";//VARCHAR(20)	備註

	@Override
	public String toString() {return JsonTool.format2Json(this);}
}
