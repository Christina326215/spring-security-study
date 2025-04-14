package com.sunnyhsu.springsecuritystudy.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface MyService {

    String getMovie();

    String deleteMovie();
}
