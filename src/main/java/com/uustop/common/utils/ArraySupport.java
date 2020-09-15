package com.uustop.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZX
 * @Description: 数组处理
 * @Date: 2019-07-22 10:06
 **/
public class ArraySupport {

    /**
     * 找出两个数组不同的地方
     *
     * @param t1  数组
     * @param t2  数组
     * @param <T> 返回新数组
     * @return 结果
     */
    public static <T> List<T> compare(T[] t1, T[] t2) {
        return Arrays.stream(t2)
                .filter(t -> !Arrays.asList(t1).contains(t))
                .collect(Collectors.toList());
    }
}
