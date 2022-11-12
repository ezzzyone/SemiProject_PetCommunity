package com.pet.home.sell;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShopCartDAO {
	@Autowired
	private SqlSession session;
	private final String NAMESPACE="com.pet.home.sell.ShopCartDAO.";
	
	public int setShopCartAdd(ShopCartDTO shopCartDTO) throws Exception{
		return session.insert(NAMESPACE+"setShopCartAdd", shopCartDTO);
	}
	
	public int setShopCartDelete(ShopCartDTO shopCartDTO) throws Exception{
		return session.delete(NAMESPACE+"setShopCartDelete", shopCartDTO);
	}
	
	public int setShopCartUpdate(ShopCartDTO shopCartDTO) throws Exception{
		return session.update(NAMESPACE+"setShopCartUpdate", shopCartDTO);
	}
	
	public String getShopCartCheck(ShopCartDTO shopCartDTO) throws Exception{
		return session.selectOne(NAMESPACE+"getShopCartCheck", shopCartDTO);
	}
	
	public ShopCartDTO getCartOne(ShopCartDTO shopCartDTO) throws Exception{
		return session.selectOne(NAMESPACE+"getCartOne", shopCartDTO);
	}
	
	public int setAllCartDelete(ShopCartDTO shopCartDTO) throws Exception{
		return session.delete(NAMESPACE+"setAllCartDelete",shopCartDTO);
	}
}
