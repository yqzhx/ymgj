package com.uustop.common.exception;


import com.uustop.common.support.FTPClientCodeEnums;

public class FTPClientException extends RuntimeException {
    /*错误代码*/
    private Integer code;

    public FTPClientException(FTPClientCodeEnums ftpClientCodeEnums , String message) {
        super(message);
        this.code = ftpClientCodeEnums.getCode();
    }

    public FTPClientException(FTPClientCodeEnums ftpClientCodeEnums) {
        super(ftpClientCodeEnums.getMessage());
        this.code = ftpClientCodeEnums.getCode();
    }
}
