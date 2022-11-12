package com.pet.home.sell;

public class ShopCartDTO {
	private Long shopNum;
	private Long itemNum;
	private String userId;
	private Long totalPrice;
	private Long totalCount;
	private String shopStatus;
	private String revStartDay;
	private String revEndDay;
	private Long adultsNum;
	private Long dogNum;
	
	public String getRevStartDay() {
		return revStartDay;
	}
	public void setRevStartDay(String revStartDay) {
		this.revStartDay = revStartDay;
	}
	public String getRevEndDay() {
		return revEndDay;
	}
	public void setRevEndDay(String revEndDay) {
		this.revEndDay = revEndDay;
	}
	public Long getAdultsNum() {
		return adultsNum;
	}
	public void setAdultsNum(Long adultsNum) {
		this.adultsNum = adultsNum;
	}
	public Long getDogNum() {
		return dogNum;
	}
	public void setDogNum(Long dogNum) {
		this.dogNum = dogNum;
	}
	public Long getShopNum() {
		return shopNum;
	}
	public void setShopNum(Long shopNum) {
		this.shopNum = shopNum;
	}
	public Long getItemNum() {
		return itemNum;
	}
	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public String getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}
}
