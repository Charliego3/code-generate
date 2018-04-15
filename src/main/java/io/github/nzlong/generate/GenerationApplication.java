package io.github.nzlong.generate;

import com.blade.Blade;
import io.github.nzlong.generate.kit.Const;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午2:28
 */
public class GenerationApplication {

    public static void main(String[] args) {
        Blade.me()
             .threadName(Const.PROJECT_THREAD_NAME)
             .start(GenerationApplication.class, args);
    }

}
