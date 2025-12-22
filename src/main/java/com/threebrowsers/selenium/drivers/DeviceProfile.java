package com.threebrowsers.selenium.drivers;

public enum DeviceProfile {

    DESKTOP(
        "1920x1080",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36"
    ),
    TABLET(
        "1024x768",
        "Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1"
    ),
    MOBILE(
        "375x667",
        "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1"
    );

    private final String resolution;
    private final String userAgent;

    DeviceProfile(String resolution, String userAgent) {
        this.resolution = resolution;
        this.userAgent = userAgent;
    }

    public String getResolution() {
        return resolution;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
