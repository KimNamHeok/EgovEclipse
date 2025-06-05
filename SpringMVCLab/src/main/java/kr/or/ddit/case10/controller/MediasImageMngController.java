package kr.or.ddit.case10.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;

/**
 * 특정 폴더 아래의 파일을 관리하기 위한 컨트롤러
 *  /case10/files (GET)
 *  /case10/files/파일명 (GET)
 *  /case10/files (POST)
 *  /case10/files/파일명 (DELETE)
 */
@Controller
@RequestMapping("/case10/files")
public class MediasImageMngController {
	@Value("${imagesFolder}")
	private Resource imageRes;
	private File saveFolder;
	@Autowired
	private ServletContext application;
	
	@PostConstruct
	public void init() throws IOException {
		saveFolder = imageRes.getFile();
	}
	
	@GetMapping
	public void fileList(Model model) {
		String[] fileNames = saveFolder.list();
		model.addAttribute("fileNames", fileNames);
	}
	
	/**
	 * 
	 * 파일 다운로드 처리
	 * Content-Disposition 헤더의 사용
	 * 	1) inline (기본 처리 형태) : 브라우저의 창 내에서 응답 컨텐츠를 소비함.
	 * 	2) attatchment ex) : attatchment;filename="파일명"
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value="{fileName}")
	public ResponseEntity<Resource> fileDownload(@PathVariable String fileName) throws IOException {
		Resource targetRes = imageRes.createRelative(fileName);
		if(!targetRes.exists()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404));
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(targetRes.contentLength());
		
		MediaType mediaType = Optional.ofNullable(application.getMimeType(targetRes.getFilename()))
				.map(MediaType::parseMediaType)
				.orElse(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentType(mediaType);
		
		headers.setContentDisposition(
			ContentDisposition.attachment()
					.filename(targetRes.getFilename(), Charset.defaultCharset())
					.build()
		);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(targetRes);
	}
	
	@PostMapping
	public String upload(
		MultipartFile uploadFile
		, RedirectAttributes redirectAttributes
	) throws IllegalStateException, IOException {
		if(!uploadFile.isEmpty()) {
			String saveName = UUID.randomUUID().toString();
			File saveFile = new File(saveFolder, saveName);
			uploadFile.transferTo(saveFile);
			redirectAttributes.addFlashAttribute("uploaded", saveName);
		}
		return "redirect:/case10/files";
	}
	
	@DeleteMapping("{fileName}")
	@ResponseBody
	public Map<String, Object> deleteOne(@PathVariable String fileName) throws IOException {
		Resource targetRes = imageRes.createRelative(fileName);
		if(!targetRes.exists()) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404));
		}
		targetRes.getFile().delete();
		return Map.of("success", true, "target", fileName);
	}
}