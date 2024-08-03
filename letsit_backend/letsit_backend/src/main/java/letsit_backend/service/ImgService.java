//package letsit_backend.service;
//
//import letsit_backend.model.Member;
//import letsit_backend.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ImgService {
//
//    private final MemberRepository memberRepository;
//    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/userImg/";
//
//    public void updateUserImg(Long kakaoId, MultipartFile imgFile) throws IOException {
//        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
//
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//            Member savedMemberEntity = fileHandler(imgFile, kakaoId);
//
//            if (savedMemberEntity != null) {
//                if (memberEntity.getProfileImageUrl() != null) {
//                    deleteUserImg(kakaoId);
//                }
//                memberEntity.setProfileImageUrl(savedMemberEntity.getProfileImageUrl());
//                memberRepository.save(memberEntity);
//            } else {
//                throw new IOException("이미지를 업로드하는 중 오류가 발생했습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + kakaoId);
//        }
//    }
//
//    private Member fileHandler(MultipartFile file, Long kakaoId) throws IOException {
//        File userImgDir = new File(UPLOAD_DIR);
//
//        if (!userImgDir.exists()) {
//            userImgDir.mkdirs();
//        }
//
//        if (!file.isEmpty()) {
//            String contentType = file.getContentType();
//            String originalFileExtension;
//
//            if (ObjectUtils.isEmpty(contentType)) {
//                throw new IllegalArgumentException("파일의 콘텐츠 유형이 비어 있습니다.");
//            } else {
//                if (contentType.contains("image/jpeg")) {
//                    originalFileExtension = ".jpg";
//                } else if (contentType.contains("image/png")) {
//                    originalFileExtension = ".png";
//                } else {
//                    throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
//                }
//            }
//
//            String originalFileName = file.getOriginalFilename();
//            int lastIndex = originalFileName.lastIndexOf('.');
//            String fileName = originalFileName.substring(0, lastIndex);
//
//            String userImgName = fileName + System.nanoTime() + originalFileExtension;
//            Path userImgPath = Paths.get(UPLOAD_DIR + userImgName);
//            file.transferTo(userImgPath.toFile());
//
//            Member memberEntity = new Member();
//            memberEntity.setProfileImageUrl(userImgPath.toString());
//
//            return memberEntity;
//        }
//
//        throw new IllegalArgumentException("파일이 비어 있습니다.");
//    }
//
//    public void deleteUserImg(Long kakaoId) {
//        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
//
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//            String imgPath = memberEntity.getProfileImageUrl();
//            File imgFile = new File(imgPath);
//
//            if (imgFile.exists() && imgFile.delete()) {
//                memberEntity.setProfileImageUrl(null);
//                memberRepository.save(memberEntity);
//            } else {
//                throw new IllegalArgumentException("이미지 파일 삭제 실패 또는 이미지 파일이 이미 존재하지 않습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + kakaoId);
//        }
//    }
//
//    public byte[] getUserProfileImage(Long kakaoId) throws IOException {
//        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
//
//        if (memberOptional.isPresent()) {
//            Member memberEntity = memberOptional.get();
//            String profileImagePath = memberEntity.getProfileImageUrl();
//
//            if (!StringUtils.isEmpty(profileImagePath)) {
//                Path imagePath = Paths.get(profileImagePath);
//
//                if (Files.exists(imagePath)) {
//                    return Files.readAllBytes(imagePath);
//                } else {
//                    throw new IllegalArgumentException("파일이 존재하지 않습니다.");
//                }
//            } else {
//                throw new IllegalArgumentException("프로필 이미지 경로가 비어 있습니다.");
//            }
//        } else {
//            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + kakaoId);
//        }
//    }
//}