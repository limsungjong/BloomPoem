package com.example.bloompoem.controller;

import com.example.bloompoem.domain.dto.UserResponse;
import com.example.bloompoem.domain.dto.UserSignInRequest;
import com.example.bloompoem.dto.UserDTO;
import com.example.bloompoem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller()
@CrossOrigin(origins = "http://192.168.45.148:5500/")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    @PostMapping("/user")
    public ResponseEntity<UserDTO> user(@RequestBody UserSignInRequest req) {
        UserDTO userDTO = UserDTO.toDTO(userRepository.findByUserEmail(req.getUserEmail()).get());

        UserResponse res = UserResponse.builder()
                .userAddress(userDTO.getUserAddress())
                .userAddressDetail(userDTO.getUserAddressDetail())
                .userPhoneNumber(userDTO.getUserPhoneNumber())
                .userEmail(userDTO.getUserEmail())
                .userName(userDTO.getUserName())
                .build();

        return ResponseEntity.ok().body(userDTO);
    }
    @PostMapping("/user/image")
    public ResponseEntity<Resource> userImage(@RequestParam Map<String, String> param) {
        System.out.println("\n");
        System.out.println("=======================================");
        System.out.println("[ModuleApiController] : [showImage] : [start]");
        System.out.println("[request name] : " + String.valueOf(param.get("name")));
        System.out.println("=======================================");
        System.out.println("\n");

        // 시스템 os 정보 확인 변수 선언
        String os = System.getProperty("os.name").toLowerCase();


        // 사진이 저장된 폴더 경로 변수 선언
        String imageRoot = "";

        // os 정보 확인 및 사진이 저장된 서버 로컬 경로 지정 실시
        // 로컬 : Home/Resource/assets 폴더는 이미지 파일을 공통적으로 저장 관리
        if(os.contains("win")) {
            imageRoot = "C:\\Users\\sung8\\OneDrive\\project\\ex\\image\\"+param.get("name"); //윈도우 경로 (디스크 필요)
        }
        else if(os.contains("linux")) {
            imageRoot = "/Home/Resource/assets/"; //리눅스 경로
        }

        // Resorce를 사용해서 로컬 서버에 저장된 이미지 경로 및 파일 명을 지정
        Resource resource = new FileSystemResource(imageRoot);



        // 로컬 서버에 저장된 이미지 파일이 없을 경우
        if(!resource.exists()){
            System.out.println("FILE : NOT_FOUND");
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 리턴 결과 반환 404
        }
        System.out.println(resource);

        // 로컬 서버에 저장된 이미지가 있는 경우 로직 처리
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(imageRoot);
            // 인풋으로 들어온 파일명 .png / .jpg 에 맞게 헤더 타입 설정
            header.add("Content-Type", Files.probeContentType(filePath));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // 이미지 리턴 실시 [브라우저에서 get 주소 확인 가능]
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
}
