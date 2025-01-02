package com.example.dynamic_registration_interface;


import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8687174887721689748L;
    private Status status;
    private T result;
    private String message;
    private String errorCode;

    public static <T> Result<T> of(Status status, T result, String message, String errorCode) {
        return new Result<T>(status, result, message, errorCode);
    }

    public static <T> Result<T> succeed(T result) {
        return of(Result.Status.OK, result, (String)null, (String)null);
    }

    public static <T> Result<T> failed(T result, String message, String errorCode) {
        return of(Result.Status.ERROR, result, message, errorCode);
    }

    public static <T> Result<T> failed(String message, String errorCode) {
        return of(Result.Status.ERROR, (T)null, message, errorCode);
    }

    public static <T> Result<T> failed(String message) {
        return of(Result.Status.ERROR, (T)null, message, (String)null);
    }

    public boolean isOk() {
        return this.status == Result.Status.OK;
    }

    public Status getStatus() {
        return this.status;
    }

    public T getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Result)) {
            return false;
        } else {
            Result<?> other = (Result)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$result = this.getResult();
                Object other$result = other.getResult();
                if (this$result == null) {
                    if (other$result != null) {
                        return false;
                    }
                } else if (!this$result.equals(other$result)) {
                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$errorCode = this.getErrorCode();
                Object other$errorCode = other.getErrorCode();
                if (this$errorCode == null) {
                    if (other$errorCode != null) {
                        return false;
                    }
                } else if (!this$errorCode.equals(other$errorCode)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Result;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : $result.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $errorCode = this.getErrorCode();
        result = result * 59 + ($errorCode == null ? 43 : $errorCode.hashCode());
        return result;
    }

    public String toString() {
        return "Result(status=" + this.getStatus() + ", result=" + this.getResult() + ", message=" + this.getMessage() + ", errorCode=" + this.getErrorCode() + ")";
    }

    public Result() {
    }

    public Result(Status status, T result, String message, String errorCode) {
        this.status = status;
        this.result = result;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static enum Status {
        OK,
        ERROR;

        private Status() {
        }
    }
}