package com.bharath.watchSpace.DTO;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO
{
    @NotBlank
    String name;
    @NotBlank
    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
