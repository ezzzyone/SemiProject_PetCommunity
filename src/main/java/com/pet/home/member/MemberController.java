package com.pet.home.member;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathEvaluationResult.XPathResultType;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.pet.home.admin.AdminDAO;
import com.pet.home.board.event.coupon.CouponDTO;
import com.pet.home.file.FileDTO;
import com.pet.home.sell.PickDTO;
import com.pet.home.sell.ReservationDTO;
import com.pet.home.sell.SellItemController;
import com.pet.home.sell.SellItemService;
import com.pet.home.sell.ShopCartDTO;
import com.pet.home.sell.file.SellFileDTO;
import com.pet.home.sell.purchase.PurchaseCancelDTO;
import com.pet.home.sell.purchase.PurchaseDTO;
import com.pet.home.util.FileManager;
import com.pet.home.util.SellPager;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import okhttp3.Request;

 
@Controller
@RequestMapping(value= "/member/*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FileManager fileManager;
	
	@Autowired 
	private SellItemService sellItemService;
	
	@Autowired
	private AdminDAO adminDAO;
	
	
	// ============= login & Join =================
	
	@GetMapping("role")
	public String getAgree()throws Exception{
		
		return "member/role";
	} 
	
	@GetMapping("login")
	public String login() throws Exception {
		System.out.println("로그인 접속 (GET)");
		
		return "member/login";
	}

	@PostMapping("login")
	public ModelAndView login(HttpServletRequest request, MemberDTO memberDTO) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		System.out.println("DB로그인 접속 (POST)");
		
		memberDTO = memberService.getLogin(memberDTO);
		
		System.out.println("차단여부 : "+memberDTO.getBlock());
		
		// request에 있는 파라미터를 session에 넣음
		HttpSession session = request.getSession();
		
		// DB에서 가져온 DTO데이터를 JSP로 속성만들어서 보내기
		//M.USERNAME, M.USERID, M.EMAIL, M.PHONE, R.ROLENUM, R.ROLENAME 내용물
		
		if (memberDTO!=null && memberDTO.getBlock() == 0) {
			session.setAttribute("member", memberDTO);
			System.out.println("로그인 성공");
		}else if(memberDTO != null && memberDTO.getBlock() == 1){
			mv.addObject("msg", "차단된 아이디 입니다. 고객센터로 문의해주세요.");
			mv.addObject("url","login");
			mv.setViewName("member/alert");
			return mv;
		}else {System.out.println("로그인 실패");
			mv.addObject("msg", "아이디/비밀번호가 틀렸습니다.");
			mv.addObject("url", "login");
			mv.setViewName("member/alert");
			return mv;
		}
		
		mv.addObject("dto", memberDTO);
		mv.setViewName("redirect:../");
		
		return mv;
	}

	@GetMapping("logout")
	public String logout (HttpSession session) throws Exception{
		
		session.invalidate(); //세션 비우기 
		
		return "redirect:../"; 
	}
	
	@GetMapping("idUsed")
	@ResponseBody
	public int idUsed(MemberDTO memberDTO) throws Exception{
		int result = memberService.getIdCount(memberDTO);
		return result;
	}
	
	@GetMapping("join")
	public String join(HttpServletRequest request) throws Exception{
	
		return "member/join";
	}
	
	@PostMapping("join")
	public ModelAndView join(MemberDTO memberDTO, MultipartFile photo, HttpSession session) throws Exception{
		ModelAndView mv = new ModelAndView();
		Calendar ca = Calendar.getInstance();
		//선택 약관동의값 세팅 
		// 체크되지 않으면 0 , 선택되면 1로 설정 
		if(memberDTO.getAgMes()==null) {
			memberDTO.setAgMes(0);
		}else {memberDTO.setAgMes(1);
		}

		//공통 member테이블 생성 
		int result = memberService.setJoin(memberDTO, photo, session.getServletContext());
	
		//게스트 회원일 때 
		if(memberDTO.getRoleNum()==2){
			memberDTO.setGuestId(ca.getTimeInMillis()); //guestId
			memberService.setGuest(memberDTO); 
		}

		  if(result>0) {
		  System.out.println("회원가입 성공!"); 
		  mv.addObject("msg","회원가입 되었습니다.");
		  }else { 
			  System.out.println("회원가입 실패"); 
			  mv.addObject("msg","회원가입에 실패했습니다.");
		  }
		  
			mv.addObject("url", "/");
			mv.setViewName("member/alert");
		  
		return mv;
		
	}
	

	@GetMapping("mypage")
	public ModelAndView mypage(HttpSession session)throws Exception {
		ModelAndView mv = new ModelAndView();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		//메인페이지에 보여줄 팔로우/상품/멤버 숫자 파라미터 설정 
		int followernum = Integer.parseInt(String.valueOf(memberService.getFollowerCount(memberDTO)));
		int followeenum = Integer.parseInt(String.valueOf(memberService.getFolloweeCount(memberDTO)));
		int memnum = Integer.parseInt(String.valueOf(memberService.getMemCount()));
		int sellnum = Integer.parseInt(String.valueOf(memberService.getItemCount()));
		System.out.println("마"+memberDTO.getRoleNum());
		if(memberDTO.getRoleNum()==2){
		memberDTO = memberService.getGuestPage(memberDTO); //역할번호가 2번일 때 회원 마이페이지 
		}else {
		memberDTO = memberService.getMyPage(memberDTO); // 그 외 마이페이지  

		}
		
		
		
		mv.addObject("memnum", memnum);
		mv.addObject("sellnum", sellnum);
		mv.addObject("followeenum", followeenum);
		mv.addObject("followernum", followernum);
		mv.addObject("dto", memberDTO);
		mv.setViewName("member/mypage");
		
		return mv;
	}
	
	// 관리자페이지 회원리스트 
	@GetMapping("memlist")
	public ModelAndView memlist()throws Exception{
		ModelAndView mv = new ModelAndView();
	List<MemberDTO> ar = memberService.getMemList();
	
	mv.addObject("list", ar);
	mv.addObject("what", "memlist");
	mv.setViewName("member/list");
	 
	return mv;
	
	}
	
	// 관리자 페이지 멤버 찾기
	@GetMapping("find")
	public ModelAndView search(MemberDTO memberDTO)throws Exception {
		ModelAndView mv = new ModelAndView();
		List<MemberDTO> ar = memberService.getFindMem(memberDTO);
		
		mv.addObject("list", ar);
		mv.addObject("what", "memlist");
		mv.setViewName("member/list");
		
		return mv;
	}
	
	//관리자 페이지 차단
	@GetMapping("block")
	public ModelAndView setBlock(MemberDTO memberDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = memberService.setBlock(memberDTO);
		
		if(result == 1){
			mv.addObject("msg", "회원이 차단되었습니다.");
		}else {
			mv.addObject("msg", "차단 실패했습니다.");
		}
			mv.addObject("url", "memlist");
			mv.setViewName("member/alert");
			
			return mv;
	}
	
	@GetMapping("unblock")
	public ModelAndView setUnBlock(MemberDTO memberDTO)throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = memberService.setUnBlock(memberDTO);
		
		if(result == 1){
			mv.addObject("msg", "회원이 차단 해제되었습니다.");
		}else {
			mv.addObject("msg", "차단 해제 실패했습니다.");
		}
			mv.addObject("url", "memlist");
			mv.setViewName("member/alert");
			
			return mv;
	}
	
	@PostMapping("delete")
	public String delete(HttpServletRequest request)throws Exception{

		MemberDTO memberDTO = (MemberDTO)request.getSession().getAttribute("member");
		String pw = request.getParameter("pw");
		
		if(memberDTO.getPassword().equals(pw)){
			memberService.setMemDelete(memberDTO);
			
			request.setAttribute("msg", "회원 탈퇴가 완료되었습니다.");
			request.setAttribute("url", "/");
			request.getSession().invalidate(); //세션 비우기 
			
			return "member/alert";
		}else {
			request.setAttribute("msg", "비밀번호가 일치하지 않습니다.");
			request.setAttribute("url", "/member/delete");
			
			return "member/alert";
		}
		
	}
	
	@GetMapping("update")
	public ModelAndView update(HttpSession session)throws Exception {
		ModelAndView mv = new ModelAndView();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		//update에 불러올 정보 
		if(memberDTO.getRoleNum()==2){
		memberDTO = memberService.getGuestPage(memberDTO); 
		}else {
		memberDTO = memberService.getMyPage(memberDTO); 
		}
		
		mv.addObject("dto", memberDTO);
		mv.setViewName("member/update");
		
		return mv;
	}
	
	@PostMapping("update")
	public ModelAndView update(MemberDTO memberDTO, MultipartFile photo, HttpSession session) throws Exception{
		ModelAndView mv = new ModelAndView();
		if(memberDTO.getAgMes()==null) {
			memberDTO.setAgMes(0);
		}else {memberDTO.setAgMes(1);
		}

		//공통 member테이블 먼저 업데이트 
		int result = memberService.setMemUpdate(memberDTO, photo, session.getServletContext());

		if(memberDTO.getRoleNum() == 2){ 
		
		if(memberDTO.getGuestId()==null){
			Calendar ca = Calendar.getInstance();
			memberDTO.setGuestId(ca.getTimeInMillis()); //guestId
			memberService.setGuest(memberDTO); 
		}else {
			memberService.setGuestUpdate(memberDTO); //guest 테이블 생성 
			}
		}
		
		if(result == 1){
			mv.addObject("msg", "정보가 수정되었습니다.");
		}else {
			mv.addObject("msg", "수정 실패했습니다.");
		}
			mv.addObject("url", "mypage");
			mv.setViewName("member/alert");
			
			return mv;
		
	}

	@GetMapping("followee")
	public ModelAndView getFolloweeList(MemberDTO memberDTO,HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView();
		memberDTO = (MemberDTO)session.getAttribute("member");
		
		List<MemberDTO> ar = memberService.getFolloweeList(memberDTO);
		
		mv.addObject("list", ar);
		mv.addObject("what","followee");
		mv.setViewName("member/list");
		return mv;
	}

	@PostMapping("followee") //delete
	@ResponseBody
	public int setFolloweeDelete(MemberDTO memberDTO,String followee, HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView();
	
		memberDTO = (MemberDTO)session.getAttribute("member");

		System.out.println(followee);
		memberDTO.setFollowee(followee);
		int result = memberService.setFolloweeDelete(memberDTO);
		
//		if (result==1) {
//		mv.addObject("msg","followee가 삭제되었습니다.");
//		mv.addObject("url","/member/followee");
//		mv.setViewName("member/alert");}
//		else {
//			mv.addObject("msg","followee 삭제에 실패했습니다.");
//			mv.addObject("url","/member/followee");
//			mv.setViewName("member/alert");
//		}
		return result;
	}
	@GetMapping("follower")
	public ModelAndView getFollowerList(MemberDTO memberDTO, HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView();
		memberDTO = (MemberDTO)session.getAttribute("member");
		List<MemberDTO> ar = memberService.getFollowerList(memberDTO);
		
		mv.addObject("list", ar);
		mv.addObject("what","follower");
		mv.setViewName("member/list");
		return mv;
	}
		
	@PostMapping("follower")
	public ModelAndView setFollowerDelete(MemberDTO memberDTO,String follower, HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView();

		memberDTO = (MemberDTO)session.getAttribute("member");

		System.out.println(follower);
		memberDTO.setFollower(follower);
		int result = memberService.setFollowerDelete(memberDTO);
		
		if (result==1) {
		mv.addObject("msg","follower가 삭제되었습니다.");
		mv.addObject("url","/member/follower");
		mv.setViewName("member/alert");}
		else {
			mv.addObject("msg","follower가 삭제 실패했습니다.");
			mv.addObject("url","/member/follower");
			mv.setViewName("member/alert");
		}
		return mv;
	}
	
		@GetMapping("cart")
		public ModelAndView getShopCartList(MemberDTO memberDTO, HttpSession session)throws Exception{
			ModelAndView mv = new ModelAndView();
			memberDTO = (MemberDTO)session.getAttribute("member");
			try {
				List<CouponDTO> couponList = memberService.getCouponList(memberDTO);
				mv.addObject("couponList", couponList);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			memberDTO = memberService.getShopCartList(memberDTO);
			if(memberDTO != null) {
				MemberDTO memberDTO2 = memberService.getTotalPrice(memberDTO);
				mv.addObject("list", memberDTO);
				mv.addObject("total", memberDTO2);				
			}
			mv.addObject("what","cart");
			mv.setViewName("member/list");
			return mv;
		}
		
	
	
		@PostMapping("cart")
		public ModelAndView setCartDelete(ShopCartDTO shopCartDTO, HttpSession session)throws Exception{
			ModelAndView mv = new ModelAndView();
	
			MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
			shopCartDTO.setUserId(memberDTO.getUserId());
			int result = memberService.setCartDelete(shopCartDTO);
			
			if (result==1) {
				mv.addObject("msg","장바구니가 삭제되었습니다.");
				mv.addObject("url","/member/cart");
				mv.setViewName("member/alert");}
				else {
					mv.addObject("msg","장바구니 삭제 실패했습니다.");
					mv.addObject("url","/member/cart");
					mv.setViewName("member/alert");
				}
			
			return mv;
		}
		
		@GetMapping("pick")
		public ModelAndView getPickList(MemberDTO memberDTO, HttpSession session)throws Exception{
			ModelAndView mv = new ModelAndView();
			memberDTO = (MemberDTO)session.getAttribute("member");
			memberDTO =  memberService.getPickList(memberDTO);

	
			mv.addObject("list", memberDTO);
			mv.addObject("what","pick");
			mv.setViewName("member/list");
			return mv;
		}
		
		@PostMapping("pick")
		public ModelAndView setPickDelete(PickDTO pickDTO, HttpSession session)throws Exception{
			ModelAndView mv = new ModelAndView();
			
			MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
			pickDTO.setUserId(memberDTO.getUserId());
			int result = memberService.setPickDelete(pickDTO);
			
			if (result==1) {
				mv.addObject("msg","찜이 삭제되었습니다.");
				mv.addObject("url","/member/pick");
				mv.setViewName("member/alert");}
				else {
					mv.addObject("msg","찜 삭제 실패했습니다.");
					mv.addObject("url","/member/pick");
					mv.setViewName("member/alert");
				}
			
			return mv;
		}
		
		@GetMapping("coupon") //개인쿠폰 
		public ModelAndView getCouponList(MemberDTO memberDTO,HttpSession session)throws Exception{
			ModelAndView mv = new ModelAndView();
			memberDTO = (MemberDTO)session.getAttribute("member");
			List<CouponDTO> ar = memberService.getCouponList(memberDTO);

			mv.addObject("list", ar);
			mv.addObject("what","coupon");
			mv.setViewName("member/list");
			return mv;
		}
		
		@GetMapping("findlogin")
		public String getFindLogin()throws Exception{

			return "member/findlogin";
		}
		
		@PostMapping("findlogin")
		public ModelAndView getFindLogin(MemberDTO memberDTO,HttpServletRequest request)throws Exception{
			ModelAndView mv = new ModelAndView();
			
			String email = request.getParameter("email");
			String userId = request.getParameter("userId");
			
			memberDTO = memberService.getFindLogin(memberDTO);
			String pw = memberDTO.getPassword();

			if(memberDTO==null || !memberDTO.getEmail().equals(email)) {
				mv.addObject("msg","일치하는 회원이 없습니다.");
				mv.addObject("url","/member/findlogin");
				mv.setViewName("member/alert");
			}else {
				
				Random random = new Random();
				pw =  Integer.toString(random.nextInt(888888));
	
			System.out.println(pw);
			memberDTO.setPassword(pw);
			memberService.setUpdatePw(memberDTO);
			memberService.setEmail(memberDTO, "pwEmail");
			
			mv.addObject("msg","회원 메일로 임시 비밀번호를 전송하였습니다.");
			mv.addObject("url","/member/login");
			mv.setViewName("member/alert");
			
			}
			
			return mv;
		}
		
		@GetMapping("kakao")
		@ResponseBody
		public ModelAndView kakao(String code, HttpServletRequest request)throws Exception{
			ModelAndView mv = new ModelAndView();
			

			// POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
			// Retrofit2
			// OkHttp
			// RestTemplate
			
			RestTemplate rt = new RestTemplate();
			
			// HttpHeader 오브젝트 생성
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			
			// HttpBody 오브젝트 생성
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("client_id", "3de4327e8b367107a94e0ffc38dcc41d");
			params.add("redirect_uri", "http://localhost/member/kakao");
			params.add("code", code);
			
			// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
			HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
					new HttpEntity<MultiValueMap<String, String>>(params, headers);
			
			// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
			ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token",
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class
			);
		
		// Gson, Json Simple, ObjectMapper
				ObjectMapper objectMapper = new ObjectMapper();
				OAuthToken oauthToken = null;
				try {
					oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				RestTemplate rt2 = new RestTemplate();
				
				// HttpHeader 오브젝트 생성
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
				headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
				// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
						new HttpEntity<>(headers2);
				
				// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
				ResponseEntity<String> response2 = rt2.exchange(
						"https://kapi.kakao.com/v2/user/me",
						HttpMethod.POST,
						kakaoProfileRequest2,
						String.class
				);
				
				ObjectMapper objectMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
				// User 오브젝트 : username, password, email
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setUserId(kakaoProfile.getId().toString());
				memberDTO.setUserName(kakaoProfile.getProperties().getNickname());
				memberDTO.setRoleNum(2); // 일반회원 가입
				memberDTO.setAgValue(1); //개인정보 동의값
				memberDTO.setAgMail(1);
				memberDTO.setAgMes(1);
				memberDTO.setBlock(0);
	
					int mem = memberService.getIdCount(memberDTO);
					if(mem ==0) {
						memberService.setKakao(memberDTO);
						System.out.println(mem);
					}
		
				HttpSession session = request.getSession();
				session.setAttribute("member", memberDTO);
	
			mv.addObject("msg","카카오 로그인 완료.");
			mv.addObject("url","/");
			mv.setViewName("member/alert");
			mv.addObject("dto", memberDTO);
			
			return mv;
		}
		
//		@PostMapping("kakao")
//		public ModelAndView kakao(MemberDTO memberDTO, HttpServletRequest request) throws Exception{
//			ModelAndView mv = new ModelAndView();
//			System.out.println("memberDTO.getUserId()"+memberDTO.getUserId());
//			memberDTO.setSearch(memberDTO.getUserId());
//			memberDTO.setRoleNum(2);
//			List<MemberDTO> ar = memberService.getFindMem(memberDTO); //여기서문제
//			//유저아이디는 불러와지는데
//			//값있으면 회원가입 막고
//			//로그아웃 되도록
//			if(ar == null) {
//				memberService.setKakao(memberDTO);
//			}
//	
//			memberDTO = memberService.getLogin(memberDTO);
//			HttpSession session = request.getSession();
//			session.setAttribute("member", memberDTO);
//			
//System.out.println("zㅏ카"+memberDTO.getRoleNum());
//			  if(memberDTO.getRoleNum() != null) {
//			  System.out.println("카카오 로그인 성공!"); 
//			  mv.addObject("msg","카카오 로그인 되었습니다.");
//			  }else { 
//				  System.out.println("카카오 로그인 실패"); 
//				  mv.addObject("msg","카카오 로그인 실패했습니다.");
//			  }
//			  
//				mv.addObject("url", "/");
//				mv.setViewName("member/alert");
//			  
//			return mv;
//			
//		}
		
@GetMapping("test")
public ModelAndView getPickList(MemberDTO memberDTO) throws Exception{
	memberDTO = memberService.getPickList(memberDTO);
	memberDTO = memberService.getShopCartList(memberDTO);
	MemberDTO ar3 = memberService.getTotalPrice(memberDTO);
	ModelAndView mv = new ModelAndView();
	mv.addObject("list", memberDTO);
	mv.addObject("list2", memberDTO);
	mv.addObject("list3", ar3);
	mv.setViewName("member/test");

	return mv;
}


	
	
//결제 내역 리스트	
	@GetMapping("purchaseList")
	public ModelAndView getPurchaseList(HttpSession httpSession) throws Exception {
		System.out.println("purchaseList");
		MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
		System.out.println(memberDTO.getUserId());
		PurchaseDTO purchaseDTO = new PurchaseDTO();
		purchaseDTO.setUserId(memberDTO.getUserId());
		List<PurchaseDTO> purchaseList = sellItemService.getPurchaseList(purchaseDTO);
		ModelAndView mv = new ModelAndView();
		mv.addObject("purchaseList", purchaseList);
		mv.addObject("what","Purchase List");
		mv.setViewName("member/list");
		for(PurchaseDTO c: purchaseList) {
			System.out.println(c.getImp_uid());
		}
		return mv;
	}
	
	//결제 취소 내역 리스트	
		@GetMapping("purchaseCancelList")
		public ModelAndView getPurchaseCancelList(HttpSession httpSession) throws Exception {
			System.out.println("purchaseCancelList");
			MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
			System.out.println(memberDTO.getUserId());
			PurchaseDTO purchaseDTO = new PurchaseDTO();
			purchaseDTO.setUserId(memberDTO.getUserId());
			List<PurchaseDTO> purchaseCancel = sellItemService.getPurchaseCancleList(memberDTO.getUserId());
			ModelAndView mv = new ModelAndView();
			mv.addObject("purchaseList", purchaseCancel);
			mv.addObject("what","Purchase Cancel List");
			mv.setViewName("member/list");
			for(PurchaseDTO c: purchaseCancel) {
				System.out.println(c.getImp_uid());
			}
			return mv;
		}	
	
//결제 상세 내역
	@GetMapping("purchaseDetail")
	public ModelAndView getPurchaseDetail(PurchaseDTO purchaseDTO) throws Exception {
		purchaseDTO = sellItemService.getPurchaseDetail(purchaseDTO);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date sDate = new Date();
		Date eDate = new Date();
		sDate = dateFormat.parse(purchaseDTO.getRevStartDate());
		eDate = dateFormat.parse(purchaseDTO.getRevStartDate());

		String revStartDate = dateFormat.format(sDate);
		String revEndDate = dateFormat.format(eDate);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("check", purchaseDTO);
		mv.addObject("revStartDate", revStartDate);
		mv.addObject("revEndDate", revEndDate);
		
		return mv;
	}
	
	@GetMapping("PDTest")
	public ModelAndView getPDTest(PurchaseDTO purchaseDTO) throws Exception{
		List<PurchaseDTO> pList = sellItemService.getPDTest(purchaseDTO);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pList", pList);
		return modelAndView;		
	}
	
//결제 취소
	@PostMapping("purchaseDelete")
	@ResponseBody
	public ModelAndView setPurchaseDelete(
			@RequestParam String imp_uid, 
			@RequestParam String merchant_uid,
			@RequestParam String reason,
			@RequestParam String amount
			) throws Exception {
		String msg = "";
		ModelAndView mv = new ModelAndView("jsonView");
		
		//토큰 발급
		IamportResponse<AccessToken> token=null;
		token = sellItemService.getToken();
		String code = sellItemService.setPurchaseCancel(token, reason, imp_uid);
			if(code.equals("0")) {
				//구매 상태 변경
				int result = sellItemService.setPurchaseStatus(merchant_uid);
				PurchaseCancelDTO cancelDTO = new PurchaseCancelDTO();
				cancelDTO.setImp_uid(imp_uid);
				cancelDTO.setMerchant_uid(merchant_uid);
				cancelDTO.setReason(reason);
				int cancelResult = sellItemService.setPurchaseCancelOne(cancelDTO);
					if(result>0) {
						msg = "success";
					} else {
						msg = "error";
					}
				} else {
						msg = "error";
					}
				mv.addObject("msg", msg);
				return mv;
		}
	
	//셀러 결제 내역 리스트	
		@GetMapping("purchaseSellerList")
		public ModelAndView getSellerPurchaseList(HttpSession httpSession) throws Exception {
			System.out.println("SellerpurchaseList");
			MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
			System.out.println(memberDTO.getUserId());
			PurchaseDTO purchaseDTO = new PurchaseDTO();
			purchaseDTO.setUserId(memberDTO.getUserId());
			List<PurchaseDTO> purchaseSellerList = sellItemService.getSellerPurchaseList(memberDTO.getUserId());
			ModelAndView mv = new ModelAndView();
			mv.addObject("purchaseList", purchaseSellerList);
			mv.addObject("what","Seller List");
			mv.setViewName("member/list");
			for(PurchaseDTO c: purchaseSellerList) {
				System.out.println(c.getImp_uid());
			}
			return mv;
		}
		
		//셀러 구매 취소 내역 리스트	
			@GetMapping("purchaseCancelSellerlList")
			public ModelAndView getSellerPurchaseCancelList(HttpSession httpSession) throws Exception {
				System.out.println("purchaseCanceSellerlList");
				MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
				System.out.println(memberDTO.getUserId());
				List<PurchaseDTO> purchaseCancel = sellItemService.getSellerPurchaseCancleList(memberDTO.getUserId());
				ModelAndView mv = new ModelAndView();
				mv.addObject("purchaseList", purchaseCancel);
				mv.addObject("what","Seller Cancel List");
				mv.setViewName("member/list");
				for(PurchaseDTO c: purchaseCancel) {
					System.out.println(c.getImp_uid());
					System.out.println(c.getItemDTO().getItemName());
					System.out.println(c.getFileDTOs().get(0).getFileName());
				}
				return mv;
			}
			
	         //결제 진행 후 DB 인서트
	         @ResponseBody
	         @RequestMapping(value = "cartPayments", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	         public String setPurchase(@RequestParam String imp_uid, 
	               @RequestParam String merchant_uid, 
	               @RequestParam String amount,
	               @RequestParam String [] itemNum,
	               @RequestParam String userId,
	               @RequestParam String couponNum,
	               HttpSession session) throws Exception {
 	               //토큰 발급
	               IamportResponse<AccessToken> token = sellItemService.getToken();
	               
	               IamportClient client = sellItemService.getClient();
	               List<ShopCartDTO> ar = new ArrayList<>();
	               
	               Payment payment = client.paymentByImpUid(imp_uid).getResponse();
	               String paymentResult = payment.getStatus();
	               for(int i=0; i<itemNum.length; i++) {
	            	   ShopCartDTO shopCartDTO = new ShopCartDTO();
	            	   shopCartDTO.setUserId(userId);
	            	   shopCartDTO.setItemNum(Long.parseLong(itemNum[i]));
	            	   shopCartDTO = sellItemService.getCartOne(shopCartDTO);
	            	   ar.add(shopCartDTO);
	            	   System.out.println("ar: "+ar.get(i));
	               }
	               
	               //결제되어야 할 금액 계산
	               Long totalPrice2 = 0L;
	               Long totalPrice = 0L;
	               
	               for(ShopCartDTO s: ar) {
	            	   totalPrice2 = sellItemService.setPrice(s.getItemNum().toString(), s.getRevStartDay(), s.getRevEndDay(), s.getAdultsNum().toString(), s.getDogNum().toString()); 
	            	   totalPrice = totalPrice2+totalPrice;
	               }
	               
	               //쿠폰 여부
	               CouponDTO couponDTO = new CouponDTO();
	               System.out.println("userId:"+userId);
	               if(!couponNum.equals("")) {
	                  couponDTO.setCouponNum(Long.parseLong(couponNum));
	                  couponDTO = adminDAO.getCouponByNum(couponDTO);
	                  couponDTO.setUserId(userId);
	                  if(couponDTO.getDiscountMethod().equals("0")) {
	                     totalPrice = totalPrice * (100 - couponDTO.getDiscountRate())/100;
	                     adminDAO.setDeleteMemberCoupon(couponDTO);
	                  }else {
	                     totalPrice = totalPrice - couponDTO.getDiscountPrice();
	                     adminDAO.setDeleteMemberCoupon(couponDTO);
	                  }
 	               }
	               
	               //실제 결제 금액과 DB상 결제되어야 하는 금액 비교
	               if(amount.equals(totalPrice.toString())) {
	                  //실결제 여부 검증
		                  if(paymentResult.equals("paid")) {
		                	  int result = 0;
		                	 for(ShopCartDTO s: ar) {
			                     PurchaseDTO purchaseDTO = new PurchaseDTO();
			                     purchaseDTO.setImp_uid(imp_uid);
			                     purchaseDTO.setMerchant_uid(merchant_uid);
			                     purchaseDTO.setItemNum(s.getItemNum());
			                     purchaseDTO.setAmount(Long.parseLong(amount));
			                     purchaseDTO.setItemPrice(s.getTotalPrice());
			                     purchaseDTO.setRevStartDate(s.getRevStartDay());
			                     purchaseDTO.setRevEndDate(s.getRevEndDay());
			                     purchaseDTO.setAdultsCount(s.getAdultsNum());
			                     purchaseDTO.setDogCount(s.getDogNum());
			                     purchaseDTO.setUserId(userId);
			                     result = sellItemService.setPurchase(purchaseDTO);
		                	 }
			                //디비 반영 검증
				              if(result>0) {
				                    	 return paymentResult;
				                     } else {
				                    	 paymentResult = "결제 기록 오류가 발생했습니다. 고객센터에 문의해주세요.";
				                    	 return paymentResult;
				                     }
		                	 	} else {
		                           paymentResult = "결제 진행에 오류가 있습니다. 카드사에 문의해주세요.";
		                           return paymentResult;
		                        }
	                        } else {//실결제 금액이 DB상 결제 금액과 다른 경우 DB에 인서트 되지 않고 결제 취소 진행
	                              String reason = "결제 금액 상이함";
	                              String code = sellItemService.setPurchaseCancel(token, reason, imp_uid);
	                              if(code.equals("0")) {
	                                 paymentResult = "결제 금액 오류로 결제가 취소됩니다.";
	                                 return paymentResult;
	                              } else {
	                                 paymentResult = "결제가 정상적으로 이루어지지 않았습니다. 카드사에 문의해주세요.";
	                                 return paymentResult;
	                              }
	                        }
	            
	                                                     
	                  }
			
}
	
