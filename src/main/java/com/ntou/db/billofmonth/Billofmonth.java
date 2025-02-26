package com.ntou.db.billofmonth;

import com.ntou.tool.JsonTool;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="billofmonth",schema="dbo")
public class Billofmonth {
	@Id @Column(name="uuid") private String uuid;
	@Column(name="cid") private String cid;
	@Column(name="cardType") private String cardType;
	@Column(name="writeDate") private String writeDate;
	@Column(name="billData") private String billData;
	@Column(name="billMonth") private String billMonth;
	@Column(name="amt") private String amt;
	@Column(name="paidAmount") private String paidAmount;
	@Column(name="notPaidAmount") private String notPaidAmount;
	@Column(name="cycleRate") private String cycleRate;
	@Column(name="cycleAmt") private String cycleAmt;
	@Column(name="spaceCycleRate") private String spaceCycleRate;
	@Column(name="spaceAmt") private String spaceAmt;
	@Column(name="payDate") private String payDate;

	@Override
	public String toString() {return JsonTool.format2Json(this);}
}
