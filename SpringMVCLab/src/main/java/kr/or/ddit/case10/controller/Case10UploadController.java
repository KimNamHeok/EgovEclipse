package kr.or.ddit.case10.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import kr.or.ddit.case10.vo.DummyFileVO;
import kr.or.ddit.validate.utils.ErrorsUtils;
import lombok.extern.slf4j.Slf4j;

//Lombok의 @Slf4j 어노테이션을 사용하여 로깅을 위한 Logger 객체(log)를 자동 생성
@Slf4j
//Spring MVC의 @Controller 어노테이션을 이용해서 해당 클래스를 컨트롤러로 등록
@Controller
//"/case10/upload" 경로로 들어오는 요청을 처리한다는 의미의 매핑 설정
@RequestMapping("/case10/upload")
public class Case10UploadController {

 // ErrorsUtils 빈(Bean)을 주입받음 - 유효성 검사 에러를 변환하는 커스텀 유틸리티
 @Autowired
 private ErrorsUtils errorUtils;

 // application.properties 혹은 다른 설정 파일에서 정의한 "dummyUpload" 프로퍼티에 해당하는 Resource를 주입받음
 @Value("${dummyUpload}")
 private Resource saveRes;

 // GET 요청 시 "/case10/upload" 경로로 들어오면 업로드 폼 화면을 렌더링하기 위한 메서드.
 @GetMapping
 public String formUI() {
     // "case10/uploadForm" 뷰 이름을 반환 (예: JSP, Thymeleaf 템플릿 파일 등)
     return "case10/uploadForm";
 }

 // POST 요청 시 업로드 폼 데이터를 처리하는 메서드 (멤버 변수 바인딩, 파일 업로드 처리 포함)
 @PostMapping
 public String formProcess2(
     // 업로드 폼의 데이터를 DummyFileVO 객체에 바인딩하며, 유효성 검사가 적용됨 (@Valid)
     @Valid @ModelAttribute("dummyFile") DummyFileVO dummyFile, 
     // 유효성 검사 결과를 담는 객체. 에러 발생 시 이를 통해 확인 및 처리 가능
     BindingResult errors,
     // 리다이렉트 시 플래시 속성을 전달하기 위한 객체
     RedirectAttributes redirectAttributes
 ) throws IllegalStateException, IOException {

     // 유효성 검사에서 에러가 있다면
     if (errors.hasErrors()) {
         // ErrorsUtils를 이용해 에러를 Map 형식(필드명, 에러 메시지 리스트)으로 변환
         Map<String, List<String>> customErrors = errorUtils.errorsToMap(errors);
         // 리다이렉트 시 에러 메시지를 전달하기 위해 플래시 속성에 추가
         redirectAttributes.addFlashAttribute("errors", customErrors);
     } else {
         // 업로드된 파일(MultipartFile 객체)을 가져옴. DummyFileVO 클래스에 정의된 프로퍼티 이름이 uploadFile
         MultipartFile other = dummyFile.getUploadFile();
         // 파일의 MIME 타입을 가져옴
         String mime = other.getContentType();
         // 만약 MIME 타입이 "image/"로 시작하지 않으면 (즉, 이미지 파일이 아니라면)
         if (!mime.startsWith("image/")) {
             // HTTP 400 Bad Request 상태 예외를 발생시킴
             throw new ResponseStatusException(HttpStatusCode.valueOf(400));
         }
         // 업로드된 파일의 원본 파일 이름 가져오기
         String originalFileName = other.getOriginalFilename();
         // 저장할 파일명의 접두사로 현재 시간의 밀리초 값을 사용하여 고유성을 부여하고, "_"와 원본 파일 이름을 이어붙임
         String saveName = System.currentTimeMillis() + "_" + originalFileName;

         // 앞서 주입받은 saveRes(Resource)를 파일 시스템의 폴더로 변환
         File saveFolder = saveRes.getFile();
         // saveFolder 경로 내에 저장할 파일 객체 생성
         File saveFile = new File(saveFolder, saveName);

         // 업로드된 파일을 지정한 위치(saveFile)로 저장 (파일 전송)
         other.transferTo(saveFile);

         // 로그에 업로더 정보를 기록 (DummyFileVO 내부의 uploader 필드 값)
         log.info("uploader : {}", dummyFile.getUploader());
         // 로그에 업로드된 파일의 객체 정보를 기록
         log.info("uploadFile : {}", other);

         // 리다이렉트 이후에 뷰에서 사용하기 위해 플래시 속성으로 saveName을 추가
         redirectAttributes.addFlashAttribute("saveName", saveName);
     }

     // 업로드 후 다시 "/case10/upload" 페이지로 리다이렉트
     return "redirect:/case10/upload";
 }

 // 아래는 주석 처리된 두 번째 업로드 처리 메서드. 
 // 이 메서드는 @PostMapping 어노테이션이 주석처리 되어 있어 사용되지 않음.
 // 다른 방식의 파라미터 바인딩(개별적으로 uploader 및 파일을 받는 방식)을 보여줌.
 //    @PostMapping
 public String formProcess(
     // 요청 파라미터 "uploader"를 String으로 받아옴
     @RequestParam String uploader,
     // 요청 파트 이름 "uploadFile"에 해당하는 파일(MultipartFile 객체)를 받아옴
     @RequestPart(name="uploadFile") MultipartFile other,
     // 리다이렉트 시 필요한 속성을 담기 위한 객체
     RedirectAttributes redirectAttributes
 ) throws IllegalStateException, IOException {
     // 만약 받은 파일이 비어있는 경우 예외 처리 (HTTP 400)
     if (other.isEmpty()) {
         throw new ResponseStatusException(HttpStatusCode.valueOf(400));
     }
     // 파일의 MIME 타입을 가져옴
     String mime = other.getContentType();
     // 파일의 MIME 타입이 "image/"로 시작하지 않으면 예외를 발생시킴
     if (!mime.startsWith("image/")) {
         throw new ResponseStatusException(HttpStatusCode.valueOf(400));
     }
     // 업로드된 파일의 원본 파일 이름을 가져옴
     String originalFileName = other.getOriginalFilename();
     // 현재 시간의 밀리초 값과 원본 파일 이름을 조합하여 고유의 파일 이름 생성
     String saveName = System.currentTimeMillis() + "_" + originalFileName;

     // 주입받은 saveRes(Resource)를 File 객체(저장 폴더)로 변환
     File saveFolder = saveRes.getFile();
     // 지정한 폴더 내에 저장하고자 하는 파일 객체 생성
     File saveFile = new File(saveFolder, saveName);

     // 업로드된 파일을 실제 저장소에 기록
     other.transferTo(saveFile);

     // 로그에 업로더 정보를 기록
     log.info("uploader : {}", uploader);
     // 로그에 업로드된 파일 객체에 대한 정보를 기록
     log.info("uploadFile : {}", other);

     // 업로드 후 "/case10/upload" 페이지로 리다이렉트
     return "redirect:/case10/upload";
 }
}