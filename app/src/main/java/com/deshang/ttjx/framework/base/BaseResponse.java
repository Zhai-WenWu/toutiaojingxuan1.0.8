package com.deshang.ttjx.framework.base;

import java.io.Serializable;

public abstract class BaseResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5375804597574885028L;
    //	public int errCode = -1;
//    @Expose
//    public int errcode;
    //	public String errinf;
    public String msg;
}
