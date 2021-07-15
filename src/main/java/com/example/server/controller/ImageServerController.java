package com.example.server.controller;

import com.example.server.entity.Base64File;
import com.example.server.entity.Photo;
import com.example.server.mapper.PhotoMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/image")
@Slf4j
public class ImageServerController {

    @Value("${file.image.host}")
    private String host;

    @Value("${file.image.store-path}")
    private String imageStorePath;

    @Value("${file.image.url-mapping}")
    private String imageUrlMapping;

    private final PhotoMapper photoMapper;

    @Autowired
    public ImageServerController(PhotoMapper photoMapper) {
        this.photoMapper = photoMapper;
    }

    @SneakyThrows
    @PostMapping("upload/normal")
    public ResponseEntity<Photo> uploadNormalImage(@RequestParam(value = "file") MultipartFile image) {
        String fileName = image.getOriginalFilename();
        fileName = UUID.randomUUID().toString().replace("-", "") + (fileName != null ?
                fileName.substring(fileName.lastIndexOf(".")) : null);
        File dest = new File(this.imageStorePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        image.transferTo(dest);
        Photo photo = Photo.builder().photoUrl(host + this.imageUrlMapping + fileName).build();
        photoMapper.insert(photo);
        return ResponseEntity.ok().body(photo);
    }

    @SneakyThrows
    @PostMapping("upload/base64")
    public ResponseEntity<String> uploadBase64Image(@RequestBody Base64File base64File) {
        String suffix = "";
        switch (base64File.getFileType()) {
            case "image/jpg":
            case "image/jpeg":
                suffix = ".jpg";
                break;
            case "image/gif":
                suffix = ".gif";
                break;
            case "image/ico":
                suffix = ".ico";
                break;
            default:
                suffix = ".png";
        }
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        byte[] b = Base64.decodeBase64(base64File.getBase64Data());
        for (int i = 0; i < b.length; i++) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        OutputStream out = new FileOutputStream(this.imageStorePath + fileName);
        log.info("创建 " + this.imageStorePath + fileName);
        out.write(b);
        out.flush();
        out.close();
        return ResponseEntity.ok().body(host + this.imageUrlMapping + fileName);
    }

}
