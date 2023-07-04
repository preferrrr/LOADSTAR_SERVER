package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.entity.Image;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.repository.ImageRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Value("${itemImgLocation}")
    //Value 어노테이션을 통해 application.properties 파일에 등록한 itemImgLocation 값을
    //불러와서 itemImgLocation 변수에 넣어줌
    private String itemImgLocation;

    public void saveImage(MultipartFile file) throws Exception {
        String oriImgName = file.getOriginalFilename();
        String imgName = "";

        imgName = uploadFile(itemImgLocation, oriImgName, file.getBytes());


        Image image = new Image();
        image.setImagePath(imgName);
        Long userId = 1l;
        User user = userRepository.getReferenceById(userId);
        image.setUser(user);
        //상품 이미저 정보 저장
        imageRepository.save(image);
        // 입력받은 상품 이미지 정보를 저장
    }

    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception {
//        UUID uuid = UUID.randomUUID();
//        //(Universally Unique Identifier)는 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용
//        //실제 사용 시 중복될 가능성이 거의 없기 때문에 파일의 이름으로 사용하면 중복 문제를 해결할 수 있다.
//        String extension = originalFileName.substring(originalFileName
//                .lastIndexOf("."));
//        String savedFileName = uuid.toString() + extension;
        //UUID로 받은 값과 원래 파일의 이름의 확장자를 조합해서 저장될 파일 이름을 만듬
        String fileUploadFullUrl = uploadPath + "/" + originalFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        //FileOutputStream 클래스는 바이트 당뉘의 출력을 내보내는 클래스입.
        //생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만든다.
        fos.write(fileData);
        //fileData를 파일 출력스트림에 입력
        fos.close();
        return fileUploadFullUrl;
    }

}
