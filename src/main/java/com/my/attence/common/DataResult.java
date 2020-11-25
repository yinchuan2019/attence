package com.my.attence.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回值DataResult
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class DataResult {

    /**
     * 请求响应code，0为成功 其他为失败
     */
    @ApiModelProperty(value = "请求响应code，0为成功 其他为失败", name = "code")
    private int code;

    /**
     * 响应异常码详细信息
     */
    @ApiModelProperty(value = "响应异常码详细信息", name = "msg")
    private String msg;

    @ApiModelProperty(value = "需要返回的数据", name = "data")
    private Object data;

    public DataResult(int code, Object data) {
        this.code = code;
        this.data = data;
        this.msg=null;
    }

    public DataResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }


    public DataResult() {
        this.code=0;
        this.msg="操作成功";
        this.data=null;
    }

    public DataResult(Object data) {
        this.data = data;
        this.code= 0;
        this.msg="操作成功";
    }


    /**
     * 操作成功 data为null
     */
    public static DataResult success(){
        return new DataResult();
    }
    /**
     * 操作成功 data 不为null
     */
    public static DataResult success(Object data){
        return new DataResult(data);
    }

    /**
     * 操作失败 data 不为null
     */
    public static DataResult fail(String msg){
        return new DataResult(1,msg);
    }
    /**
     *  自定义返回  data为null
     */
    public static DataResult getResult(int code, String msg){
        return new DataResult(code,msg);
    }


}
