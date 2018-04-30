package io.github.zunpiau.emitter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Import({LogbackConfig.class})
@Configuration
@ComponentScan(basePackages = "io.github.zunpiau",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "io\\.github\\.zunpiau\\.emitter\\.web\\..*")})
public class RootConfig {

}
