package com.brito.autentication.web.dto.auth.protocol;

import com.brito.autentication.web.dto.protocol.DTO;

public interface AuthDTO extends DTO{
    String getUsername();
    String getPassword();
}
