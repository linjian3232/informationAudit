package com.informationaudit.config;

import com.informationaudit.storage.AuditStorage;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;

@Configuration
public class FileResourceConfig implements WebMvcConfigurer {
    private static final String FILES = "/publicFile/**";
    private static final String PICTURES = "/img/uploaderPic/**";
    private static final String AVATARS = "/avatorImg/**";

    private final AuditStorage storage;

    public FileResourceConfig(AuditStorage storage) {
        this.storage = storage;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PICTURES).addResourceLocations(directoryUri(storage.uploaderPictures()),
                "classpath:/defaults/uploader-picture/");
        registry.addResourceHandler(FILES).addResourceLocations(directoryUri(storage.publicFiles()));
        registry.addResourceHandler(AVATARS).addResourceLocations(directoryUri(storage.avatars()));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                if (handler instanceof ResourceHttpRequestHandler) {
                    response.setHeader("Content-Security-Policy", "sandbox; default-src 'none'");
                    response.setHeader("X-Content-Type-Options", "nosniff");
                    if (request.getServletPath().startsWith("/publicFile/")) {
                        response.setHeader("Content-Disposition", "attachment");
                    }
                }
                return true;
            }
        }).addPathPatterns(FILES, PICTURES, AVATARS);
    }

    private String directoryUri(Path directory) {
        String uri = directory.toUri().toString();
        return uri.endsWith("/") ? uri : uri + "/";
    }
}


