package com.emc.plants.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity(name="PAYMNT_PROC_MSG")
@Table(name="PAYMNT_PROC_MSG", schema="APP")
public class PaymentProcessMessage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="PaymntProcMsgSeq")
	@TableGenerator(name="PaymntProcMsgSeq", table="IDGENERATOR", pkColumnName="IDNAME", 
			pkColumnValue="PAYMNTPROCMSG", valueColumnName="IDVALUE", schema="APP")
	@Column(name="idPAYMNT_PROC_MSG")
	private long idPaymntProcMsg;
	
	@Column(name="PAYMNT_MSG")
	private String paymntProcMsg;

	public long getIdPaymntProcMsg() {
		return idPaymntProcMsg;
	}

	public void setIdPaymntProcMsg(long idPaymntProcMsg) {
		this.idPaymntProcMsg = idPaymntProcMsg;
	}

	public String getPaymntProcMsg() {
		return paymntProcMsg;
	}

	public void setPaymntProcMsg(String paymntProcMsg) {
		this.paymntProcMsg = paymntProcMsg;
	}
	
	

}
