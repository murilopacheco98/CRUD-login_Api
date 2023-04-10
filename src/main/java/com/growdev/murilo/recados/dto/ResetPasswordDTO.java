package com.growdev.murilo.recados.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResetPasswordDTO {
    private String resetPasswordToken;
    private String newPassword;
}
