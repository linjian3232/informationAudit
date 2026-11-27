package com.informationaudit.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class AuditStorage {
    private final Path root;

    public AuditStorage(@Value("${audit.storage-root}") String root) {
        this.root = Paths.get(root).toAbsolutePath().normalize();
    }

    public Path publicFiles() {
        return root.resolve("publicFile");
    }

    public Path uploaderPictures() {
        return root.resolve("img").resolve("uploaderPic");
    }

    public Path avatars() {
        return root.resolve("avatorImg");
    }

    public String storePublicFile(MultipartFile file) throws IOException {
        return "/publicFile/" + store(file, publicFiles());
    }

    public String storeUploaderPicture(MultipartFile file) throws IOException {
        return "/img/uploaderPic/" + store(file, uploaderPictures());
    }

    private String store(MultipartFile file, Path directory) throws IOException {
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null) {
            int dot = originalName.lastIndexOf('.');
            if (dot >= 0 && originalName.substring(dot).matches("\\.[A-Za-z0-9]{1,16}")) {
                extension = originalName.substring(dot);
            }
        }

        Files.createDirectories(directory);
        String storedName = UUID.randomUUID() + extension;
        Path destination = directory.resolve(storedName);
        file.transferTo(destination.toFile());
        return storedName;
    }
}
