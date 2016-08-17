package com.xeeshi.nsoft.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ZEESHAN on 13/08/16.
 */
public class Error {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private Boolean error;

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     * The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error{" +
                "msg='" + msg + '\'' +
                ", error=" + error +
                '}';
    }
}

