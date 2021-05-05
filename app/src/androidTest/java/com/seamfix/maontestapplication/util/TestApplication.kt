package com.seamfix.maontestapplication.util

import com.seamfix.maontestapplication.MainApplication


class TestApplication: MainApplication() {

    override val baseUrl: String
        get() = "http://127.0.0.1:8080"
}