package com.sp.sbbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.sbbs.Board;
import com.sp.common.MyUtil;
import com.sp.member.SessionInfo;

@Controller("sbbs.boardController")
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value="/sbbs/list")
	public String list(
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="all") String condition,
			@RequestParam(defaultValue="") String keyword,
			HttpServletRequest req,
			Model model) throws Exception {
		
		System.out.println(current_page+":"+condition+":"+keyword);
		String cp = req.getContextPath();
		
		int rows = 5;
		int total_page=0;
		int dataCount=0;
		
		if(req.getMethod().equalsIgnoreCase("GET")) { // GET 방식인 경우
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		//전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
	    map.put("keyword", keyword);
	    
	    dataCount = service.dataCount(map);
        if(dataCount != 0)
            total_page = myUtil.pageCount(rows, dataCount) ;
	    

        // 다른 사람이 자료를 삭제하여 전체 페이지수가 변화 된 경우
        if(total_page < current_page) 
            current_page = total_page;

        // 리스트에 출력할 데이터를 가져오기
        int offset = (current_page-1) * rows;
		if(offset < 0) offset = 0;
        map.put("offset", offset);
        map.put("rows", rows);
        
        // 글 리스트
        List<Board> list = service.listBoard(map);

//		System.out.println("갖고왔는디 :" +list.size());
		
        // 리스트의 번호
        int listNum, n = 0;
        for(Board dto : list) {
            listNum = dataCount - (offset + n);
            dto.setListNum(listNum);
            n++;
        }
        
        String query = "";
        String listUrl = cp+"/sbbs/list";
        String articleUrl = cp+"/sbbs/article?page=" + current_page;
        if(keyword.length()!=0) {
        	query = "condition=" +condition + 
        	         "&keyword=" + URLEncoder.encode(keyword, "utf-8");	
        }
        
        if(query.length()!=0) {
        	listUrl = cp+"/sbbs/list?" + query;
        	articleUrl = cp+"/sbbs/article?page=" + current_page + "&"+ query;
        }
        
        String paging = myUtil.paging(current_page, total_page, listUrl);

        model.addAttribute("list", list);
        model.addAttribute("articleUrl", articleUrl);
        model.addAttribute("page", current_page);
        model.addAttribute("dataCount", dataCount);
        model.addAttribute("total_page", total_page);
        model.addAttribute("paging", paging);
        
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		
        
		return ".sbbs.list";
	}
	
	@RequestMapping(value="/sbbs/created", method=RequestMethod.GET)
	public String createdForm(
			Model model) throws Exception {
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("parent", null);
		List<Board> categoryList = null;
		try {
			categoryList=service.listCategory(map);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("mode", "created");
		return ".sbbs.created";
	}
	
	@RequestMapping(value="/sbbs/created", method=RequestMethod.POST)
	public String createdSubmit(
			Board dto,
			HttpSession session
			) throws Exception {
		
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		dto.setUserId(info.getUserId());
		dto.setUserName(info.getUserName());

		System.out.println(dto.toString());
		try {
			service.insertBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(dto.toString());
		return "redirect:/sbbs/list";
	}
	
	//중분류 카테고리 리스트
	@RequestMapping(value="/sbbs/subCategory", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listSubCategory(
			@RequestParam int num
			) throws Exception {
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("parent", num);
		List<Board>  listSubcategory=service.listCategory(map);
		Map<String, Object> model=new HashMap<>();
		model.put("listSubcategory", listSubcategory);
		return model;
	}		
}
