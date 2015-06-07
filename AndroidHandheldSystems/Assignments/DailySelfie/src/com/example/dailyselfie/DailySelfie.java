package com.example.dailyselfie;


public class DailySelfie {

	private String mSelfieUri;
	private String mSelfieName;
	private String mSelfieDate;

	public DailySelfie(String mSelfieUri, String mSelfieName, String mSelfieDate) {
		super();
		this.mSelfieUri = mSelfieUri;
		this.mSelfieName = mSelfieName;
		this.mSelfieDate = mSelfieDate;
	}

	public String getmSelfieUrl() {
		return mSelfieUri;
	}

	public void setmSelfieUrl(String mSelfieUrl) {
		this.mSelfieUri = mSelfieUrl;
	}

	public String getmSelfieName() {
		return mSelfieName;
	}

	public void setmSelfieName(String mSelfieName) {
		this.mSelfieName = mSelfieName;
	}

	public String getmSelfieDate() {
		return mSelfieDate;
	}

	public void setmSelfieDate(String mSelfieDate) {
		this.mSelfieDate = mSelfieDate;
	}

}
