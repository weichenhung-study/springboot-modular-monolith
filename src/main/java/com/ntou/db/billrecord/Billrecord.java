package com.ntou.db.billrecord;

import com.ntou.tool.JsonTool;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="billrecord",schema="dbo")
public class Billrecord {
	@Id @Column(name="uuid") private String uuid				= ""; //VARCHAR(36)	, --交易編號UUID
	@Column(name="buyChannel") private String buyChannel		= ""; //VARCHAR(2) 	, --消費通路(00:實體, 01:線上)
	@Column(name="buyDate") private String buyDate			= ""; //VARCHAR(23)	, --消費時間yyyy/MM/dd HH:MM:ss.SSS
	@Column(name="reqPaymentDate") private String reqPaymentDate	= ""; //VARCHAR(23)	, --請款時間yyyy/MM/dd HH:MM:ss.SSS
	@Column(name="cardType") private String cardType			= ""; //VARCHAR(5)	, --卡別
	@Column(name="shopId") private String shopId			= ""; //VARCHAR(20)	, --消費店家(統編)
	@Column(name="cid") private String cid				= ""; //VARCHAR(10)	, --消費者(身分證)
	@Column(name="buyCurrency") private String buyCurrency		= ""; //VARCHAR(10)	, --消費幣別
	@Column(name="buyAmount") private String buyAmount		= ""; //VARCHAR(10)	, --消費金額
	@Column(name="disputedFlag") private String disputedFlag		= ""; //VARCHAR(2) 	, --爭議款項註記(00:正常,01異常)
	@Column(name="status") private String status			= ""; //VARCHAR(2) 	, --狀態(00:正常,99:註銷)
	@Column(name="actuallyDate") private String actuallyDate		= ""; //VARCHAR(23)	, --交易完成的時間yyyy/MM/dd HH:MM:ss.SSS
	@Column(name="remark") private String remark			= ""; //VARCHAR(50)	, --備註
	@Column(name="issuingBank") private String issuingBank		= ""; //VARCHAR(50)	, --發卡銀行(swiftCode)
	@Column(name="cardNum") private String cardNum			= ""; //VARCHAR(20)	, --卡號
	@Column(name="securityCode") private String securityCode		= ""; //VARCHAR(10)	, --安全碼

	@Override
	public String toString() {return JsonTool.format2Json(this);}
}
