package com.eoi.spotyfav.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImageUtils {
    public String saveImageBase64(String dir, String imageB64) {
        String[] parts = imageB64.split(",");
        String base64 = parts[1];
        String extension = getExtension(parts[0]);
        String fileName = System.currentTimeMillis() + "." + extension;
        BufferedImage image;
        byte[] imageByte = Base64.getDecoder().decode(base64);
        try {
            Path pathDir = Paths.get("public", "images", dir);
            if (!Files.exists(pathDir)) {
                Files.createDirectory(pathDir);
            }
            image = ImageIO.read(new ByteArrayInputStream(imageByte));
            Path path = pathDir.resolve(fileName);
            ImageIO.write(image, extension, path.toFile());
            return "images" + "/" + dir + "/" + fileName;
        } catch (IOException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
            return "";
        }
    }

    private String getExtension(String header) {
        Pattern extensionPattern = Pattern.compile("image/([a-z]+);");
        Matcher matcher = extensionPattern.matcher(header);
        return matcher.find() ? matcher.group(1) : "jpg";
    }
}
