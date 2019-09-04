package com.star.sud.dto;

public class NtfnDevice {

	//Attributes
	/////////////////
	private String ndevId;
	private String nchntypeId;
	private String ndevConfig;
	private int ndevSeq;
	private String ndevDesc;

	//Properties
	//////////////////
	public String getNdevId() {
		return ndevId;
	}

	public void setNdevId(String ndevId) {
		this.ndevId = ndevId;
	}

	public String getNdevConfig() {
		return ndevConfig;
	}

	public void setNdevConfig(String ndevConfig) {
		this.ndevConfig = ndevConfig;
	}

	public int getNdevSeq() {
		return ndevSeq;
	}

	public void setNdevSeq(int ndevSeq) {
		this.ndevSeq = ndevSeq;
	}

	public String getNdevDesc() {
		return ndevDesc;
	}

	public void setNdevDesc(String ndevDesc) {
		this.ndevDesc = ndevDesc;
	}

	public String getNchntypeId() {
		return nchntypeId;
	}

	public void setNchntypeId(String nchntypeId) {
		this.nchntypeId = nchntypeId;
	}
}
