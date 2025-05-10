package com.example.matketplaceparser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtRefreshAndAccess {
    private String access;
    private String refresh;
}
