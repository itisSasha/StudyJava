package domain;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/*
    resultInfo
    BEからFEに返すデータを保存
 */
public class ResultInfo implements Serializable {
    // 処理に異常はなければreturn true,あればfalse
    private boolean flag;
    // BEから返されるデータ
    private Object data;
    // 異常が発生した時のエラーMSG
    private String errorMsg;

    public ResultInfo(boolean flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public ResultInfo() {
    }

    public ResultInfo(boolean flag) {
        this.flag = flag;
    }

    public ResultInfo(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
