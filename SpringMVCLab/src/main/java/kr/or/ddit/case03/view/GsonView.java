package kr.or.ddit.case03.view;

import java.io.PrintWriter;
import java.util.Map;

import org.springframework.web.servlet.view.AbstractView;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;

public class GsonView extends AbstractView{

	
	public GsonView() {
		setContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		try(
			PrintWriter out = response.getWriter();	
		){
			new Gson().toJson(model, out);			
		}
	}

}
