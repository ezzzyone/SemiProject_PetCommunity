package com.pet.home.sell;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.pet.home.member.MemberDTO;
import com.pet.home.sell.file.RvFileDTO;
import com.pet.home.sell.file.SellFileDTO;
import com.pet.home.sell.purchase.PurchaseCancelDAO;
import com.pet.home.sell.purchase.PurchaseCancelDTO;
import com.pet.home.sell.purchase.PurchaseDAO;
import com.pet.home.sell.purchase.PurchaseDTO;
import com.pet.home.sell.sellcategory.CategoryDTO;
import com.pet.home.sell.sellcategory.SellCategoryDTO;
import com.pet.home.util.FileManager;
import com.pet.home.util.Pager;
import com.pet.home.util.SellPager;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;

@Service
public class SellItemService {
	
	@Autowired
	private SellItemDAO itemDAO;
	@Autowired
	private PickDAO pickDAO;
	@Autowired
	private ShopCartDAO shopCartDAO;
	@Autowired
	private ReviewDAO reviewDAO;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private RvCommentDAO rvCommentDAO;
	@Autowired
	private PurchaseDAO purchaseDAO;
	@Autowired
	private PurchaseCancelDAO cancelDAO;
	
	@Autowired
	private SellQnaDAO sellQnaDAO;
	@Autowired
	private SellQnaCommentDAO sellQnaCommentDAO;
	
	
	private IamportClient client;
	
	public IamportClient getClient() {
		this.client = new IamportClient("7768266328715148", "uETnhxe3MbNMjFN4Gs6U5PuiYYR6TWf9SFcGncxj9SWEcDAysad8JZmNnOYpChUkXzIdw7Ld9uTaSWuP");
		return client;
	}
	
	public int setAllCartDelete(ShopCartDTO shopCartDTO) throws Exception{
		int result = shopCartDAO.setAllCartDelete(shopCartDTO);
		return result;
	}
	
	
	public int setItemAdd(SellItemDTO itemDTO, MultipartFile [] files, ServletContext servletContext) throws Exception {
		int result = itemDAO.setItemAdd(itemDTO);
		SellCategoryDTO categoryDTO = new SellCategoryDTO();
		categoryDTO.setItemNum(itemDTO.getItemNum());
		categoryDTO.setCategoryNum(itemDTO.getItemCatg());
		itemDAO.setCategory(categoryDTO);
		
			for(MultipartFile m: files) {
				if(m.isEmpty()) {
					continue;
				}				
				
				String realPath = servletContext.getRealPath("resources/upload/sellfile");
				
				File file = new File(realPath);
				if(!file.exists()) {
					file.mkdirs();
				}
				
				Calendar ca = Calendar.getInstance();
				Long l = ca.getTimeInMillis();
				String oriName = m.getOriginalFilename();
				String fileName = l+"_"+oriName;
				file = new File(file, fileName);
				
				System.out.println(realPath);
				System.out.println(fileName);
				
				m.transferTo(file);
				SellFileDTO fileDTO = new SellFileDTO();
				fileDTO.setFileName(fileName);
				fileDTO.setOriName(m.getOriginalFilename());
				fileDTO.setItemNum(itemDTO.getItemNum());
				itemDAO.setAddSellFile(fileDTO);
				System.out.println("저장");
			}//for end
			
		return result;
	}
	
	public List<SellItemDTO> getItemList(SellPager sellPager) throws Exception {
		sellPager.getRowNum();
		sellPager.getNum(itemDAO.getItemCount(sellPager));
		return itemDAO.getItemList(sellPager);
	}
	
	public List<SellItemDTO> getSellerList(SellPager sellPager) throws Exception {
		sellPager.getRowNum();
		System.out.println("Seller totalCount: "+itemDAO.getSellerItemCount(sellPager));
		sellPager.getNum(itemDAO.getSellerItemCount(sellPager));
		System.out.println("totalPage :"+sellPager.getTotalPage());
		return itemDAO.getSellerList(sellPager);
	}
	
	public CategoryDTO getCategory(Long itemCatg) throws Exception{
		return itemDAO.getCategory(itemCatg);
	}
	
	public SellItemDTO getDetailOne(SellItemDTO dto) throws Exception{
		return itemDAO.getDetailOne(dto);
	}
	
	public int setItemUpdate(SellItemDTO itemDTO, MultipartFile [] files, ServletContext servletContext) throws Exception {
		int result = itemDAO.setItemUpdate(itemDTO);
		
		for(MultipartFile m: files) {
			if(m.isEmpty()) {
				continue;
			}				
			
			String realPath = servletContext.getRealPath("resources/upload/sellfile");
			
			File file = new File(realPath);
			
			if(!file.exists()) {
				file.mkdirs();
			}
			
			Calendar ca = Calendar.getInstance();
			Long l = ca.getTimeInMillis();
			String oriName = m.getOriginalFilename();
			String fileName = l+"_"+oriName;
			file = new File(file, fileName);
			
			System.out.println(realPath);
			System.out.println(fileName);
			
			m.transferTo(file);
			SellFileDTO fileDTO = new SellFileDTO();
			fileDTO.setFileName(fileName);
			fileDTO.setOriName(m.getOriginalFilename());
			fileDTO.setItemNum(itemDTO.getItemNum());
			itemDAO.setAddSellFile(fileDTO);
			System.out.println("저장");
		}//for end
		
		return result;
	}
	
	public int setItemDelete(SellItemDTO itemDTO, ServletContext context) throws Exception {
			itemDTO = itemDAO.getDetailOne(itemDTO);
			List<SellFileDTO> files = itemDTO.getFileDTOs();
			itemDAO.setFileDelete(itemDTO.getItemNum());
		for(SellFileDTO m: files) {
			String path = context.getRealPath("resources/upload/sellfile");
			System.out.println(path);
			File file = new File(path, m.getFileName());
			file.delete();	
			}
		int result = itemDAO.setCategoryDelete(itemDTO.getItemNum());
		return itemDAO.setItemDelete(itemDTO);
	}
	
	public int setUpdateFileDelete(SellFileDTO fileDTO, ServletContext servletContext) throws Exception {
		fileDTO = itemDAO.getSellFileDTO(fileDTO);
		int result = itemDAO.setUpdateFileDelete(fileDTO);		
		String path = servletContext.getRealPath("resources/upload/sellfile");
		System.out.println(path);
		File file = new File(path, fileDTO.getFileName());
		file.delete();		
		return result;
	}

	public List<SellItemDTO> getItems(Map<String, Object> map) throws Exception {
		return itemDAO.getItems(map);
	}
	
	public List<SellItemDTO> getPickStatus(MemberDTO memberDTO) throws Exception{
		return itemDAO.getPickStatus(memberDTO);
	}
	
	public List<SellItemDTO> getShopCartStatus(MemberDTO memberDTO) throws Exception{
		return itemDAO.getShopCartStatus(memberDTO);
	}
	
	public int setPickAdd(PickDTO pickDTO) throws Exception{
		return pickDAO.setPickAdd(pickDTO);
	}
	
	public PickDTO getPickCheck(PickDTO pickDTO) throws Exception{
		return pickDAO.getPickCheck(pickDTO);
	}
	
	public int setPickDelete(PickDTO pickDTO) throws Exception{
		return pickDAO.setPickDelete(pickDTO);
	}
	
	public int setShopCartAdd(ShopCartDTO shopCartDTO) throws Exception{
		return shopCartDAO.setShopCartAdd(shopCartDTO);
	}
	
	public String getShopCartCheck(ShopCartDTO shopCartDTO) throws Exception{
		return shopCartDAO.getShopCartCheck(shopCartDTO);
	}
	
	public int setShopCartDelete(ShopCartDTO shopCartDTO) throws Exception{
		return shopCartDAO.setShopCartDelete(shopCartDTO);
	}
	
	public int setShopCartUpdate(ShopCartDTO shopCartDTO) throws Exception{
		return shopCartDAO.setShopCartUpdate(shopCartDTO);
	}
	
	public int setReviewAdd(ReviewDTO reviewDTO, MultipartFile [] mf, ServletContext servletContext) throws Exception {
		System.out.println("service");
		int result = reviewDAO.setReivewAdd(reviewDTO);
		System.out.println(reviewDTO.getItemNum());
		
			for(MultipartFile m: mf) {
				if(m.isEmpty()) {
					continue;
				}				
				
				String realPath = servletContext.getRealPath("resources/upload/reviewfile");
				
				File file = new File(realPath);
				
				if(!file.exists()) {
					file.mkdirs();
				}
				
				Calendar ca = Calendar.getInstance();
				Long l = ca.getTimeInMillis();
				String oriName = m.getOriginalFilename();
				String fileName = l+"_"+oriName;
				file = new File(file, fileName);
				
				System.out.println(realPath);
				System.out.println(fileName);
				
				m.transferTo(file);
				RvFileDTO rvFileDTO = new RvFileDTO();
				rvFileDTO.setFileName(fileName);
				rvFileDTO.setOriName(m.getOriginalFilename());
				rvFileDTO.setRvNum(reviewDTO.getRvNum());
				reviewDAO.setAddReviewFile(rvFileDTO);
				System.out.println("저장");
			}//for end
			
		return result;
	}
	
	public List<ReviewDTO> getReviewList(com.pet.home.util.CommentPager commentPager)throws Exception{
		commentPager.getRowNum();
		Long totalCount = reviewDAO.getReviewListTotalCount(commentPager);
		System.out.println(totalCount);
		commentPager.makePage(totalCount);
		return reviewDAO.getReviewList(commentPager);
	}
	
	public SellItemDTO getMap() throws Exception{
		return itemDAO.getMap();
	}
	
	public ReviewDTO getReviewUpdate(ReviewDTO reviewDTO) throws Exception{
		return reviewDAO.getReviewUpdate(reviewDTO);
	}
	
	public int setFileDelete(RvFileDTO rvFileDTO,ServletContext servletContext) throws Exception{
		rvFileDTO = reviewDAO.getFileDetail(rvFileDTO);
		int result = reviewDAO.setFileDelete(rvFileDTO);
		String path = "resources/upload/reviewfile";
		
		if(result > 0) {
			fileManager.deleteFile(servletContext, rvFileDTO, path);
		}
		
		return result;
	}
	
	public int setReviewUpdate(ReviewDTO reviewDTO, MultipartFile [] mf, ServletContext servletContext) throws Exception{
		int result = reviewDAO.setReviewUpdate(reviewDTO);
		for(MultipartFile m: mf) {
			if(m.isEmpty()) {
				continue;
			}				
			
			String realPath = servletContext.getRealPath("resources/upload/reviewfile");
			
			File file = new File(realPath);
			
			if(!file.exists()) {
				file.mkdirs();
			}
			
			Calendar ca = Calendar.getInstance();
			Long l = ca.getTimeInMillis();
			String oriName = m.getOriginalFilename();
			String fileName = l+"_"+oriName;
			file = new File(file, fileName);
			
			System.out.println(realPath);
			System.out.println(fileName);
			
			m.transferTo(file);
			RvFileDTO rvFileDTO = new RvFileDTO();
			rvFileDTO.setFileName(fileName);
			rvFileDTO.setOriName(m.getOriginalFilename());
			rvFileDTO.setRvNum(reviewDTO.getRvNum());
			reviewDAO.setAddReviewFile(rvFileDTO);
			System.out.println("저장");
		}//for end
		
		return result;
	}
	
	public int setReviewDelete(ReviewDTO reviewDTO) throws Exception{
		return reviewDAO.setReviewDelete(reviewDTO);
	}
	
	public int setReviewCommentAllDelete(RvCommentDTO rvCommentDTO) throws Exception{
		return rvCommentDAO.setReviewCommentAllDelete(rvCommentDTO);
	}
	
	public int setReviewCommentDelete(RvCommentDTO rvCommentDTO) throws Exception{
		return rvCommentDAO.setReviewCommentDelete(rvCommentDTO);
	}
	
	public int setReviewCommentAdd(RvCommentDTO rvCommentDTO) throws Exception{
		return rvCommentDAO.setReviewCommentAdd(rvCommentDTO);
	}
	
	public int setReviewCommentUpdate(RvCommentDTO rvCommentDTO) throws Exception{
		return rvCommentDAO.setReviewCommentUpdate(rvCommentDTO);
	}
	
	
	
	
	
	
	
	
	public int setSellQnaAdd(SellQnaDTO sellQnaDTO) throws Exception {
		System.out.println("service");
		int result = sellQnaDAO.setSellQnaAdd(sellQnaDTO);
		return result;
	}
	
	public List<SellQnaDTO> getSellQnaList(com.pet.home.util.CommentPager commentPager)throws Exception{
		commentPager.getRowNum();
		Long totalCount = sellQnaDAO.getSellQnaListTotalCount(commentPager);
		commentPager.makePage(totalCount);
		return sellQnaDAO.getSellQnaList(commentPager);
	}
	
	public SellQnaDTO getSellQnaUpdate(SellQnaDTO sellQnaDTO) throws Exception{
		return sellQnaDAO.getSellQnaUpdate(sellQnaDTO);
	}

	
	public int setSellQnaUpdate(SellQnaDTO sellQnaDTO) throws Exception{
		int result = sellQnaDAO.setSellQnaUpdate(sellQnaDTO);
		return result;
	}
	
	public int setSellQnaDelete(SellQnaDTO sellQnaDTO) throws Exception{
		return sellQnaDAO.setSellQnaDelete(sellQnaDTO);
	}
	
	public int setSellQnaCommentAllDelete(SellQnaCommentDTO sellQnaCommentDTO) throws Exception{
		return sellQnaCommentDAO.setSellQnaCommentAllDelete(sellQnaCommentDTO);
	}
	
	public int setSellQnaCommentDelete(SellQnaCommentDTO sellQnaCommentDTO) throws Exception{
		return sellQnaCommentDAO.setSellQnaCommentDelete(sellQnaCommentDTO);
	}
	
	public int setSellQnaCommentAdd(SellQnaCommentDTO sellQnaCommentDTO) throws Exception{
		return sellQnaCommentDAO.setSellQnaCommentAdd(sellQnaCommentDTO);
	}
	
	public int setSellQnaCommentUpdate(SellQnaCommentDTO sellQnaCommentDTO) throws Exception{
		return sellQnaCommentDAO.setSellQnaCommentUpdate(sellQnaCommentDTO);
	}
	
	
	//지도 매핑용
	public List<SellItemDTO> getAllItemList() throws Exception{
		return itemDAO.getAllItemList();
	}
	
	//결제
	public int setPurchase(PurchaseDTO purchaseDTO) {
		int result = purchaseDAO.setPurchase(purchaseDTO);
		return result;
	}
	
	public List<PurchaseDTO> getPurchaseList(PurchaseDTO purchaseDTO) throws Exception{
		return purchaseDAO.getPurchaseList(purchaseDTO);
	}
	
	public PurchaseDTO getPurchaseDetail(PurchaseDTO purchaseDTO) throws Exception{
		return purchaseDAO.getPurchaseDetail(purchaseDTO); 
	}
	
	public List<PurchaseDTO> getPDTest(PurchaseDTO purchaseDTO) throws Exception{
		return purchaseDAO.getPDTest(purchaseDTO);
	}
	
	//아임포트 서버에서 전액 환불 진행
	public String setPurchaseCancel(IamportResponse<AccessToken> token, String reason, String imp_uid) throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", token.getResponse().getToken());
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		
		params.add("reason", reason);
		params.add("imp_uid", imp_uid);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(params,headers);
		
		String response = restTemplate.postForObject("http://api.iamport.kr/payments/cancel", request, String.class);
		System.out.println("response: "+response);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(response);
		String code = jsonObject.get("code").toString();

		return code;
	}
	
	//결제 취소 리스트 출력
	public List<PurchaseDTO> getPurchaseCancleList (String userId) {
		return cancelDAO.getPurchaseCancleList(userId);
	}
	
	//결제 취소 DB 인서트
	public int setPurchaseCancelOne (PurchaseCancelDTO cancelDTO) {
		return cancelDAO.setPurchaseCancelOne(cancelDTO);
	}
	
	//결제 상태 변경
	public int setPurchaseStatus(String merchant_uid) throws Exception {
		return purchaseDAO.setPurchaseStatus(merchant_uid);
	}
	
	//token
	public IamportResponse<AccessToken> getToken() throws Exception {
		//client 생성
		IamportClient client = getClient();
		
		//토큰 발급
		IamportResponse<AccessToken> token = client.getAuth();
		return token;
	}

	//메인 리스트 출력
	public List<SellItemDTO> getPurchaseListtoMain() throws Exception {
		return itemDAO.getPurchaseListtoMain();
	}
	
	//셀러페이지 상품구매내역 출력
	public List<PurchaseDTO> getSellerPurchaseList(String userId) throws Exception{
		return purchaseDAO.getSellerPurchaseList(userId);
	}
	
	//셀러페이지 상품취소내역 출력
	public List<PurchaseDTO> getSellerPurchaseCancleList(String userId) throws Exception{
		return cancelDAO.getSellerPurchaseCancleList(userId);
	}
	
	//결제되어야 할 금액 계산
	public Long setPrice
	(String itemNum, String revStartDate, String revEndDate, String adultsCount, String dogCount) throws Exception {
		SellItemDTO itemDTO = new SellItemDTO();
		itemDTO.setItemNum(Long.parseLong(itemNum));
		itemDTO = itemDAO.getDetailOne(itemDTO);
		Long itemPrice = itemDTO.getItemPrice();
		
		Date start = new SimpleDateFormat("yyyy-MM-dd").parse(revStartDate);
		Date end = new SimpleDateFormat("yyyy-MM-dd").parse(revEndDate);
		Long diffSec = (end.getTime() - start.getTime()) / 1000; //초 차이
		Long revDays = diffSec / (24*60*60); //일자수 차이
		if(end.getTime() == start.getTime()) {
			revDays = 1L;
		}
		Long totalPrice = (itemPrice * revDays)+(10000*Long.parseLong(adultsCount))+(10000*Long.parseLong(dogCount));
		return totalPrice;
	}
	
	//장바구니 목록 중 한개 가져오기
	public ShopCartDTO getCartOne(ShopCartDTO shopCartDTO) throws Exception{
		return shopCartDAO.getCartOne(shopCartDTO);
	}
}
	
