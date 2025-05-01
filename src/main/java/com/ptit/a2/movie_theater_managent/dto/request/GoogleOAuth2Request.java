package com.ptit.a2.movie_theater_managent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOAuth2Request {
    private String code;
    private String redirectUri;
} 