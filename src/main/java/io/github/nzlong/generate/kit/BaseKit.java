package io.github.nzlong.generate.kit;

import java.util.List;

/**
 * @author: nzlong
 * @description:
 * @date: Create in 2018 04 15 下午12:19
 */
public class BaseKit {

    public static boolean isEmptyOrNull(List list) {
        if (list == null || list.size() <= 0 ) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmptyOrNull(List list) {
        return !isEmptyOrNull(list);
    }

}
