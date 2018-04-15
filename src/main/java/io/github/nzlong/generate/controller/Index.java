package io.github.nzlong.generate.controller;

import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 03 31 下午2:49
 */
@Path
public class Index {

    @Route
    public String index() {
        return "index.html";
    }

}
