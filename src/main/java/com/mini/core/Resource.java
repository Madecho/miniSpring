package com.mini.core;

import java.util.Iterator;


/**
 * 把外部的配置信息都当成 Resource（资源）来进行抽象
 */

public interface Resource extends Iterator<Object> {
}