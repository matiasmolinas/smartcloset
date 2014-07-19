package com.smartcloset.android.wearable.smartclosetassistant;

public final class Constants {
    private Constants() {
    }
    public static final String CLOTHING_LIST_FILE = "clothinglist.json";
    public static final String CLOTHING_NAME_TO_LOAD = "clothing_name";

    public static final String CLOTHING_FIELD_LIST = "clothing_list";
    public static final String CLOTHING_FIELD_IMAGE = "img";
    public static final String CLOTHING_FIELD_FEATURES = "features";
    public static final String CLOTHING_FIELD_NAME = "name";
    public static final String CLOTHING_FIELD_SUMMARY = "summary";
    public static final String CLOTHING_FIELD_STEPS = "steps";
    public static final String CLOTHING_FIELD_TEXT = "text";
    public static final String CLOTHING_FIELD_TITLE = "title";
    public static final String CLOTHING_FIELD_STEP_TEXT = "step_text";
    public static final String CLOTHING_FIELD_STEP_IMAGE = "step_image";

    static final String ACTION_START_DRESSING =
            "com.smartcloset.android.wearable.smartclosetassistant.START_DRESSING";
    public static final String EXTRA_CLOTHING = "clothing";

    public static final int NOTIFICATION_ID = 0;
    public static final int NOTIFICATION_IMAGE_WIDTH = 280;
    public static final int NOTIFICATION_IMAGE_HEIGHT = 280;
}
