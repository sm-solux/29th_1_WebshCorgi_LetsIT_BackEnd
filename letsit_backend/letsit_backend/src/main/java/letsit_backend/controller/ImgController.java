//package letsit_backend.controller;
//
//import letsit_backend.dto.Response;
//import letsit_backend.service.ImgService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;  // MultipartFile import 추가
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/profile")
//public class ImgController {
//    private final ImgService imgService;
//
//    @PostMapping("/upload")
//    public Response<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("kakaoId") Long kakaoId) {
//
//        log.info("프로필 업데이트 컨트롤러 실행");
//
//        String fileName = file.getOriginalFilename();
//        String contentType = file.getContentType();
//
//        log.info("fileName: " + fileName);
//        log.info("contentType: " + contentType);
//
//        try {
//            imgService.updateUserImg(kakaoId, file);
//            return Response.success("User information updated successfully", null);
//        } catch (Exception e) {
//            return Response.fail("Failed to update user information: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/image")
//    public ResponseEntity<byte[]> getUserImg(@RequestParam Long kakaoId) {
//
//        log.info("유저 사진 정보 컨트롤러 실행");
//
//        try {
//            byte[] imageBytes = imgService.getUserProfileImage(kakaoId);
//            log.info("imageBytes: " + imageBytes);
//
//            if (imageBytes != null && imageBytes.length > 0) {
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.IMAGE_JPEG); // 필요에 따라 변경
//                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}