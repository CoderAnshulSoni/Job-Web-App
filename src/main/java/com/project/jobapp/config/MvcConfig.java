package com.project.jobapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String UPlOAD_DIR = "photos";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposedDirectory(UPlOAD_DIR, registry);
    }

    // Converts the uploadDir string to a Path
    // Maps requests starting with "/photos/**" to a file system location file: <absolute path to photos directory>
    // The ** will match on all subdirectories
    private void exposedDirectory(String uploadDir, ResourceHandlerRegistry registry) {

        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/" + uploadDir + "/**").addResourceLocations("file" + path.toAbsolutePath() + "/");
    }
}
