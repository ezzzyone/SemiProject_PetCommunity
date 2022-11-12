package com.pet.home.sell.purchase;

import java.sql.Date;
import java.util.List;

import com.pet.home.member.MemberDTO;
import com.pet.home.sell.SellItemDTO;
import com.pet.home.sell.file.SellFileDTO;

public class PurchaseDTO {
	
	private Long purchaseNum;
	//구매번호(PK)
	private String imp_uid;
	//가맹점번호
    private String merchant_uid;
    //결제번호
    private Long itemNum;
    private Long amount;
    private Long itemPrice;
    private String userId;
    private String revStartDate;
    private String revEndDate;
    private Long adultsCount;
    private Long dogCount;
    private Date purchaseDate;
    private Long purchaseStatus;
    //결제 완료: 1, 결제 취소: 0
    private PurchaseCancelDTO cancelDTO;
    
    
    
    
    private MemberDTO memberDTO;
    
    private SellItemDTO itemDTO;
    
    private List<SellFileDTO> fileDTOs;
    
    
    
    
    
    
	public Long getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Long getPurchaseNum() {
		return purchaseNum;
	}
	public void setPurchaseNum(Long purchaseNum) {
		this.purchaseNum = purchaseNum;
	}
	public PurchaseCancelDTO getCancelDTO() {
		return cancelDTO;
	}
	public void setCancelDTO(PurchaseCancelDTO cancelDTO) {
		this.cancelDTO = cancelDTO;
	}
	public List<SellFileDTO> getFileDTOs() {
		return fileDTOs;
	}
	public void setFileDTOs(List<SellFileDTO> fileDTOs) {
		this.fileDTOs = fileDTOs;
	}

	public MemberDTO getMemberDTO() {
		return memberDTO;
	}
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}
	public SellItemDTO getItemDTO() {
		return itemDTO;
	}
	public void setItemDTO(SellItemDTO itemDTO) {
		this.itemDTO = itemDTO;
	}
	
	
	
	public Long getPurchaseStatus() {
		return purchaseStatus;
	}
	public void setPurchaseStatus(Long purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getImp_uid() {
		return imp_uid;
	}
	public void setImp_uid(String imp_uid) {
		this.imp_uid = imp_uid;
	}
	public String getMerchant_uid() {
		return merchant_uid;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public Long getItemNum() {
		return itemNum;
	}
	public void setItemNum(Long itemNum) {
		this.itemNum = itemNum;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRevStartDate() {
		return revStartDate;
	}
	public void setRevStartDate(String revStartDate) {
		this.revStartDate = revStartDate;
	}
	public String getRevEndDate() {
		return revEndDate;
	}
	public void setRevEndDate(String revEndDate) {
		this.revEndDate = revEndDate;
	}
	public Long getAdultsCount() {
		return adultsCount;
	}
	public void setAdultsCount(Long adultsCount) {
		this.adultsCount = adultsCount;
	}
	public Long getDogCount() {
		return dogCount;
	}
	public void setDogCount(Long dogCount) {
		this.dogCount = dogCount;
	}
    
}
