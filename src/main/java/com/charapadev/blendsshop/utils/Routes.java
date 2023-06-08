package com.charapadev.blendsshop.utils;

import java.util.List;

public class Routes {

    public static Route REQUEST_AUTH_TOKEN = new Route("POST", "/login");
    public static Route SEE_PRODUCTS = new Route("GET", "/products/**");

    public static Route CREATE_ORDER = new Route("POST", "/orders");

    public static List<Route> getPublicRoutes() {
        return List.of(
            REQUEST_AUTH_TOKEN,
            SEE_PRODUCTS,
            CREATE_ORDER
        );
    }

}
