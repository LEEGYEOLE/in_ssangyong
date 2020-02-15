//package com.sp.member;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.sp.mail.Mail;
//import com.sp.mail.MailSender;
//
//@Controller("member.memberController2")
//public class MemberController2 {
//	@Autowired
//	private MemberService service;
//	@Autowired
//	private MailSender mailSender;
//	
//	@RequestMapping(value="/member/member", method=RequestMethod.GET)
//	public String memberForm(Model model) {
//		model.addAttribute("mode", "member");
//		return ".member.member";
//	}
//
///*
//    * RedirectAttributes 
//      RedirectAttributes에 데이터등을 저장하면 Redirect 된 후 즉시 사라지게 되고
//	    사용자가 F5등을 눌러 리로드 하더라도 서버로 다시 submit 되어 저장되지 않게할 수 있다.
//*/
//	@RequestMapping(value="/member/member", method=RequestMethod.POST)
//	public String memberSubmit(Member dto,
//			final RedirectAttributes reAttr,
//			Model model) {
//
//		try {
//			service.insertMember(dto);
//		} catch (Exception e) {
//			model.addAttribute("mode", "member");
//			model.addAttribute("message", "아이디 중복으로 회원가입이 실패했습니다.");
//				
//			return ".member.member";
//		}
//		
//		StringBuilder sb=new StringBuilder();
//		sb.append(dto.getUserName()+ "님의 회원 가입이 정상적으로 처리되었습니다.<br>");
//		sb.append("메인화면으로 이동하여 로그인 하시기 바랍니다.<br>");
//		
//		 // 리다이렉트된 페이지에 값 넘기기
//        reAttr.addFlashAttribute("message", sb.toString());
//        reAttr.addFlashAttribute("title", "회원 가입");
////        reAttr.addAttribute("message", sb.toString());
////        reAttr.addAttribute("title", "회원 가입");
//		return "redirect:/member/complete";
//	}
//	
///*http://localhost:9090/sp3/member/pwd
//    * @ModelAttribute
//      - 스프링에서 JSP파일에 반환되는 Model 객체에 속성값을 주입하거나 바인딩할 때
//                사용되는 어노테이션이다.
//      - RedirectAttributes 에 저장된 데이터를 자바 메소드(리다이렉트로 매핑된 메소드)
//               에서 넘겨 받기 위해서는 메소드 인자에 @ModelAttribute("속성명")을 사용해야 한다.
//               message : 세션값이라 원래는 못받아옴...
//               ModelAttribute(3.1이상) : flash 데이터 ( 새로고침 하면 사라지는 데이터)를 받을 때도 사용...
//               새로고침시 null이 아니라 ""이 됨... 즉, length가 0이면 새로고침 한 것. 새로고침 한 경우 메인으로...
//*/
//	@RequestMapping(value="/member/complete")
//	public String complete(
//			@ModelAttribute("message") 
//			String message
//			) throws Exception{
//		System.out.println(message);
//		// 컴플릿 페이지(complete.jsp)의 출력되는 message와 title는 RedirectAttributes 값이다. 
//		// F5를 눌러 새로 고침을 하면 null이 된다.
//		
//		if(message==null || message.length()==0) // F5를 누른 경우
//			return "redirect:/";
//		
//		return ".member.complete";
//	}
//	
//	@RequestMapping(value="/member/login", method=RequestMethod.GET)
//	public String loginForm() {
//		return ".member.login";
//	}
//	
//	@RequestMapping(value="/member/login", method=RequestMethod.POST)
//	public String loginSubmit(
//			@RequestParam String userId,
//			@RequestParam String userPwd,
//			HttpSession session,
//			Model model
//			) {
//		
//		Member dto=service.loginMember(userId);
//		if(dto==null ||  !  userPwd.equals(dto.getUserPwd())) {
//			model.addAttribute("message", "아이디 또는 패스워드가 일치하지 않습니다.");
//			return ".member.login";
//		}
//		
//		// 세션에 로그인 정보 저장
//		SessionInfo info=new SessionInfo();
//		info.setUserId(dto.getUserId());
//		info.setUserName(dto.getUserName());
//		
//		session.setMaxInactiveInterval(30*60); // 세션유지시간 30분, 기본:30분
//		
//		session.setAttribute("member", info);
//		
//		// 로그인 이전 URI로 이동
//		String uri=(String)session.getAttribute("preLoginURI");
//		session.removeAttribute("preLoginURI");
//		if(uri==null)
//			uri="redirect:/";
//		else
//			uri="redirect:"+uri;
//		
//		return uri;
//	}
//	//member/findPwd
//	
//	@RequestMapping(value="/member/pwdFind", method=RequestMethod.GET)
//	public String findPwdForm() {
//		return ".member.pwdFind";
//	}
//	
//	@RequestMapping(value="/member/pwdFind", method=RequestMethod.POST)
//	public String findPwdSubmit(
//			@RequestParam String userId, final RedirectAttributes reAttr,
//			HttpSession session,
//			Model model
//			) {
//		Member member=service.readMember(userId);
////		System.out.println(member.getEmail());
//		if(member==null) {
//			model.addAttribute("message", "존재하지 않는 아이디입니다.");
//			return ".member.pwdFind";
//		}
//		System.out.println(member.toString());
//		Mail dto = new Mail();
//		dto.setReceiverEmail(member.getEmail());
//		dto.setSenderName("관리자");
//		dto.setSenderEmail("ruf951127@gmail.com");
//		dto.setSubject("임시 비밀번호를 알려드립니다.");
//		
//		String s = "";
//		for (int i = 0; i < 7; i++) {
//			char rc =  (char)(49 + (int)(Math.random()*57));
//		s+=rc;
//		}
//		dto.setContent(s);
//		
//		boolean b=mailSender.mailSend(dto);
//		String msg="<span style='color:blue;'>"+dto.getReceiverEmail()+"</span> 님에게<br>";
//		if(b) {
//			msg+="임시비밀번호가 메일로 전송되었습니다.";
//			member.setUserPwd(s);
//			try {
//				service.updateMember(member);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			msg+="메일을 전송하는데 실패했습니다.";
//		}
//		
//		reAttr.addFlashAttribute("message", msg);
//		
//		return "redirect:/mail/complete";
//	}
//	
//	@RequestMapping(value="/member/logout")
//	public String logout(HttpSession session) {
//		// 세션에 저장된 정보 지우기
//		session.removeAttribute("member");
//		
//		// 세션에 저장된 모든 정보 지우고, 세션초기화
//		session.invalidate();
//		
//		return "redirect:/";
//	}
//	
//	@RequestMapping(value="/member/pwd", method=RequestMethod.GET)
//	public String pwdForm(
//			String dropout,
//			Model model) {
//		
//		if(dropout==null) {
//			model.addAttribute("mode", "update");
//		} else {
//			model.addAttribute("mode", "dropout");
//		}
//		
//		return ".member.pwd";
//	}
//	
//	@RequestMapping(value="/member/pwd", method=RequestMethod.POST)
//	public String pwdSubmit(
//			@RequestParam String userPwd,
//			@RequestParam String mode,
//			final RedirectAttributes reAttr,
//			Model model,
//			HttpSession session
//	     ) {
//		
//		SessionInfo info=(SessionInfo)session.getAttribute("member");
//		
//		Member dto=service.readMember(info.getUserId());
//		if(dto==null) {
//			session.invalidate();
//			return "redirect:/";
//		}
//		
//		if(! dto.getUserPwd().equals(userPwd)) {
//			if(mode.equals("update")) {
//				model.addAttribute("mode", "update");
//			} else {
//				model.addAttribute("mode", "dropout");
//			}
//			model.addAttribute("message", "패스워드가 일치하지 않습니다.");
//			return ".member.pwd";
//		}
//		
//		if(mode.equals("dropout")){
//			// 게시판 테이블등 자료 삭제
//			
//			// 회원탈퇴 처리
//			/*
//			Map<String, Object> map = new HashMap<>();
//			map.put("memberIdx", info.getMemberIdx());
//			map.put("userId", info.getUserId());
//			*/
//
//			// 세션 정보 삭제
//			session.removeAttribute("member");
//			session.invalidate();
//
//			StringBuilder sb=new StringBuilder();
//			sb.append(dto.getUserName()+ "님의 회원 탈퇴 처리가 정상적으로 처리되었습니다.<br>");
//			sb.append("메인화면으로 이동 하시기 바랍니다.<br>");
//			
//			reAttr.addFlashAttribute("title", "회원 탈퇴");
//			reAttr.addFlashAttribute("message", sb.toString());
//			
//			return "redirect:/member/complete";
//		}
//
//		// 회원정보수정폼
//		model.addAttribute("dto", dto);
//		model.addAttribute("mode", "update");
//		return ".member.member";
//	}
//
//	@RequestMapping(value="/member/update", method=RequestMethod.POST)
//	public String updateSubmit(
//			Member dto,
//			final RedirectAttributes reAttr,
//			Model model) {
//		
//		try {
//			service.updateMember(dto);
//		} catch (Exception e) {
//		}
//		
//		StringBuilder sb=new StringBuilder();
//		sb.append(dto.getUserName()+ "님의 회원정보가 정상적으로 변경되었습니다.<br>");
//		sb.append("메인화면으로 이동 하시기 바랍니다.<br>");
//		
//		reAttr.addFlashAttribute("title", "회원 정보 수정");
//		reAttr.addFlashAttribute("message", sb.toString());
//		
//		return "redirect:/member/complete";
//	}
//
//	// @ResponseBody : 자바 객체를 HTTP 응답 몸체로 전송(AJAX에서 JSON 전송 등에 사용), 맵을 던졌을 때 자동으로 json으로 변환해서 던져준다.
//	@RequestMapping(value="/member/userIdCheck", method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> idCheck(
//			@RequestParam String userId
//			) throws Exception {
//		
//		String p="true";
//		Member dto=service.loginMember(userId);
//		if(dto!=null)
//			p="false";
//		
//		Map<String, Object> model=new HashMap<>();
//		model.put("passed", p);
//		return model;
//	}
//	
//}
