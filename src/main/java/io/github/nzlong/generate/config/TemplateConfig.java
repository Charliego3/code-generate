package io.github.nzlong.generate.config;

import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.mvc.view.template.JetbrickTemplateEngine;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午2:52
 */
@Bean
public class TemplateConfig implements BeanProcessor {

    @Override
    public void processor(Blade blade) {
        blade.templateEngine(new JetbrickTemplateEngine());
    }

}
