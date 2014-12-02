package com.mastek.topcoders.smartkanteen.bean;

import java.io.Serializable;

public class ItemDVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4837502739443633727L;
	private int _iItemId;
	private String _strItemName;
	private String _strItemDesc;
	private double _dblItemPrice;
	private int _iItemPrepTime;
	private String _strTableName;
	
	public String getTableName() {
		return _strTableName;
	}
	public void setTableName(String _strTableName) {
		this._strTableName = _strTableName;
	}
	public int getItemId() {
		return _iItemId;
	}
	public void setItemId(int _iItemId) {
		this._iItemId = _iItemId;
	}
	public String getItemName() {
		return _strItemName;
	}
	public void setItemName(String _strItemName) {
		this._strItemName = _strItemName;
	}
	public String getItemDesc() {
		return _strItemDesc;
	}
	public void setItemDesc(String _strItemDesc) {
		this._strItemDesc = _strItemDesc;
	}
	public double getItemPrice() {
		return _dblItemPrice;
	}
	public void setItemPrice(double _dblItemPrice) {
		this._dblItemPrice = _dblItemPrice;
	}
	public int getItemPrepTime() {
		return _iItemPrepTime;
	}
	public void setItemPrepTime(int _iItemPrepTime) {
		this._iItemPrepTime = _iItemPrepTime;
	}

}
