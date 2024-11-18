package org.odc.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dorovcxxj",
                "api_key", "632872196495457",
                "api_secret", "zO1oCpmsPXXVdrceSnGPgReb9qk"
        ));
    }
}
