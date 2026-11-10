package com.saidur.creatrix.unicorn.salessolution.Network.Response;

public class ResponseResultInfo {
    public Boolean isSuccess ;
    public Boolean isError;
    public String ErrorMessage ;
    public String errormessagenew ;
    public String Msd;
    public Boolean isValiCheck;

    public String getErrormessagenew() {
        return errormessagenew;
    }

    public void setErrormessagenew(String errormessagenew) {
        this.errormessagenew = errormessagenew;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public Boolean getValiCheck() {
        return isValiCheck;
    }

    public void setValiCheck(Boolean valiCheck) {
        isValiCheck = valiCheck;
    }

    public String getMsd() {
        return Msd;
    }

    public void setMsd(String msd) {
        Msd = msd;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "isSuccess=" + isSuccess +
                ", isError=" + isError +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }
}
