package com.eshop.productservice.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    public static boolean checkIfImage(String base64String) {
        // Check if the base64 string starts with a valid image header
        return StringUtils.startsWithIgnoreCase(base64String, "data:image/");
    }

    public File convertToImage(String base64String,String name) throws IOException {
        // Decode base64 string to byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64String.split(",")[1]);

        String imageType = base64String.split(";")[0].split("/")[1];
        if (!imageType.equals("jpeg") && !imageType.equals("jpg") && !imageType.equals("png")) {
            throw new IllegalArgumentException("Unsupported image type");
        }

        String fileName = System.currentTimeMillis() + "_"  + name + "." + imageType;

        // Create file object with the specified file name
        File imageFile = new File(fileName);

        // Write byte array to file
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            outputStream.write(imageBytes);
        }

        return imageFile;
    }
}
