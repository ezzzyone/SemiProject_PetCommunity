package com.pet.home.sell.purchase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pet.home.member.MemberDTO;
import com.pet.home.sell.SellItemDTO;
import com.pet.home.util.SellPager;

@Repository
public class PurchaseDAO {

	@Autowired
	private SqlSession session;
	
	private final String NAMESPACE = "com.pet.home.sell.purchase.PurchaseDAO.";
	
	public int setPurchase (PurchaseDTO purchaseDTO) {
		return session.insert(NAMESPACE+"setPurchase", purchaseDTO);
	}
	
	public List<PurchaseDTO> getPurchaseList(PurchaseDTO purchaseDTO) throws Exception{
		return session.selectList(NAMESPACE+"getPurchaseList", purchaseDTO);
	}
	
	public PurchaseDTO getPurchaseDetail(PurchaseDTO checkDTO) throws Exception{
		return session.selectOne(NAMESPACE+"getPurchaseDetail", checkDTO);
	}
	
	public int setPurchaseDelete(PurchaseDTO checkDTO) throws Exception {
		return session.delete(NAMESPACE+"setPurchaseDelete", checkDTO); 
	}
	
	public int setPurchaseStatus(String merchant_uid) throws Exception{
		return session.update(NAMESPACE+"setPurchaseStatus", merchant_uid);
	}
	
	public List<PurchaseDTO> getSellerPurchaseList(String userId) throws Exception{
		return session.selectList(NAMESPACE+"getSellerPurchaseList", userId);
	}
	
	
	public List<PurchaseDTO> getPDTest(PurchaseDTO purchaseDTO) throws Exception{
		return session.selectList(NAMESPACE+"getPDTest", purchaseDTO);
	}
		
}
