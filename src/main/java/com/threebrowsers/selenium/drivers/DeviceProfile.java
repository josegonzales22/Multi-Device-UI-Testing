package com.threebrowsers.selenium.drivers;

public enum DeviceProfile {

    DESKTOP(
            1920,
            1080,
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36"
    ),
    TABLET(
            1024,
            768,
            "Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1"
    ),
    MOBILE(
            375,
            667,
            "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1"
    );

    private final int width;
    private final int height;
    private final String userAgent;

    DeviceProfile(int width, int height, String userAgent) {
        this.width = width;
        this.height = height;
        this.userAgent = userAgent;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getResolutionString() {
        return width + "x" + height;
    }
}