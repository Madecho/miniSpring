package com.minis.context;

import java.util.EventObject;

/**
 * 监控容器的启动状态，增加事件监听
 */
public class ApplicationEvent  extends EventObject {
    private static final long serialVersionUID = 1L;
    public ApplicationEvent(Object arg0) {
        super(arg0);
    }
}