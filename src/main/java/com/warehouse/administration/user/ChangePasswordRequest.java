package com.warehouse.administration.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {

    @NotBlank(message = "{app.oldpassword}")
    @Size(min = 6, max = 10)
    private String oldPassword;

    @NotBlank(message = "{app.newpassword}")
    @Size(min = 6, max = 10)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest [newPassword=" + newPassword + ", oldPassword=" + oldPassword + "]";
    }

}
