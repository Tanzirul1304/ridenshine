package com.ridenshine.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {
    @GetMapping({"/products", "/services", "/booking", "/reviews", "/admin"})
    public String forwardReactRoutes() { return "forward:/index.html"; }
}
