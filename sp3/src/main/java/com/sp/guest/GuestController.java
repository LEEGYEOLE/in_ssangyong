package com.sp.guest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.common.MyUtil;
import com.sp.member.SessionInfo;

@Controller("guest.guestController")
public class GuestController {
	@Autowired
	private GuestService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value = "/guest/guest")
	public String main() throws Exception {
		return ".guest.guest";
	}

	// guest insert
	@RequestMapping(value = "/guest/insert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertSubmit(Guest dto, HttpSession session) throws Exception {

		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String state = "true";
		try {
			dto.setUserId(info.getUserId());
			service.insertGuest(dto);
			
		} catch (Exception e) {
			state = "false";
		}
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		return model;
	}

	// guest list
		@RequestMapping(value = "/guest/list", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> list(
				@RequestParam(value="pageNo", defaultValue="1") int current_page
				) throws Exception {
			
			int rows = 5;
			int total_page;
			int dataCount;
			
			Map<String, Object> map = new HashMap<>();
			
			dataCount = service.dataCount();
			total_page = myUtil.pageCount(rows, dataCount);
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
			
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
			}
			Map<String, Object> model = new HashMap<>();
			model.put("list", listGuest);
			model.put("pageNo", current_page);
			model.put("dataCount", dataCount);
			model.put("total_page", total_page);
			model.put("paging", paging);
			
			return model;
			
		}
		
		@RequestMapping(value = "/guest/delete", method=RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> delete(
				@RequestParam Map<String, Object> paramMap,
				HttpSession session
				) throws Exception {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			String state = "true";
			try {
				paramMap.put("userId", info.getUserId());
				service.deleteGuest(paramMap);
			} catch (Exception e) {
				state = "false";
				e.printStackTrace();
			}
			Map<String, Object> model = new HashMap<>();
			model.put("state", state);

			return model;
		}
}
