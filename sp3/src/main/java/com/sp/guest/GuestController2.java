package com.sp.guest;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.common.MyUtil;
import com.sp.member.SessionInfo;

@Controller("guest2.guestController")
public class GuestController2 {
	@Autowired
	private GuestService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value = "/guest/guest2")
	public String main() throws Exception {
		return ".guest2.guest";
	}

	// AJAX:XML(@ResponseBody 이용한 방법 : dto같은 클래스 또 만들어야함...)
	@RequestMapping(value = "/guest/insert2", method = RequestMethod.POST)
	@ResponseBody
	public StringData submit(Guest dto, HttpSession session) throws Exception {

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String state = "true";
		try {
			dto.setUserId(info.getUserId());
			service.insertGuest(dto);
			
		} catch (Exception e) {
			state = "false";
		}
		return new StringData(state);
	}

	// guest list
	// AJAX:XML(@ResponseBody 사용하지않는 경우)
		@RequestMapping(value = "/guest/list2")
		public String list(
				@RequestParam(value="pageNo", defaultValue="1") int current_page, 
				Model model
				) throws Exception {
			
			int rows = 5;
			int total_page = 0;
			int dataCount=0;
			
			Map<String, Object> map = new HashMap<>();
			
			dataCount = service.dataCount();
			 if(dataCount!=0) {
		         total_page=myUtil.pageCount(rows, dataCount);
		      }
			if (current_page > total_page) {
				total_page = current_page;
			}
			
			int offset = (current_page - 1) * rows;
			if (offset < 0) {
				offset = 0;
			}
			map.put("offset", offset);
			map.put("rows", rows);

			List<Guest> listGuest = service.listGuest(map);
			for (Guest dto : listGuest) {
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
//				myUtil.htmlSymbols()	//자동으로 html 소스로 바꿔주는 메소드
				
			}
			// 자바스크립트 메소드를 호출하는 페이징처리
			// 에이작스 할때는 pagingMethod써야함
			String paging = myUtil.pagingMethod(current_page, total_page, "listPage");
			
			model.addAttribute("list", listGuest);
			model.addAttribute("pageNo", current_page);
			model.addAttribute("dataCount", dataCount);
			model.addAttribute("total_page", total_page);
			model.addAttribute("paging", paging);
			
			return "guest2/list";	//jsp로 포워딩(XML을 클라이언트에게 전달)
			
		}
		
		//AJAX:XML-@ResponseBody 를 사용하지 않는 경우
		@RequestMapping(value = "/guest/delete2", method=RequestMethod.POST)
		public void delete(
				@RequestParam Map<String, Object> paramMap,
				 HttpServletResponse resp, HttpSession session
				) throws Exception {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			String state = "true";
			try {
				paramMap.put("userId", info.getUserId());
				service.deleteGuest(paramMap);
			} catch (Exception e) {
				state = "false";
			}
			
			//XML 클라이언트에게 전송
			
			StringBuilder sb = new StringBuilder();
			sb.append("<root><state>");
			sb.append(state);
			sb.append("</state></root>");
			
			resp.setContentType("text/xml;charset=utf-8"); 
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		}
}
