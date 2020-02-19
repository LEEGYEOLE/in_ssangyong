package com.sp.tour;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.common.FileManager;
import com.sp.common.MyUtil;

@Controller("tour.tourController")
public class TourController {
	
	private MyUtil myUtil;
	@Autowired
	private FileManager fileManager;

	@RequestMapping(value = "/tour/main", method = RequestMethod.GET)
	public String main(Model model) throws Exception {

		model.addAttribute("mode", "created");
		return ".tour.tour";
	}
	@ResponseBody
	@RequestMapping(value = "/tour/ajaxRequest", method = RequestMethod.GET)
	public Map<String, Object> ajaxRequest(
			String getUrl) throws Exception {
		getUrl = URLDecoder.decode(getUrl,"UTF-8");
		URL url = new URL(getUrl);
	    System.out.println("Url : " + getUrl);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("CONTENT-TYPE","text/html"); 
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
	    String inputLine;
	    String buffer = "";
	    while ((inputLine = in.readLine()) != null){
	     	buffer += inputLine.trim();
	    }
	    System.out.println("buffer : " + buffer);
	    in.close();
	    
	    Map<String, Object> model = new HashMap<>();
	    model.put("buffer", buffer);
		return model;
	}
	
}