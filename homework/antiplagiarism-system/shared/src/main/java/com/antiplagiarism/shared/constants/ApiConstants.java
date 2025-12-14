package com.antiplagiarism.shared.constants;

public final class ApiConstants {

    private ApiConstants() {
        // Utility class
    }

    // API Endpoints
    public static final String API_V1 = "/api/v1";
    public static final String API_V2 = "/api/v2";

    // File Storing Service
    public static final String FILES_ENDPOINT = "/files";
    public static final String WORKS_ENDPOINT = "/works";
    public static final String UPLOAD_ENDPOINT = "/upload";

    // Analysis Service
    public static final String ANALYZE_ENDPOINT = "/analyze";
    public static final String REPORTS_ENDPOINT = "/reports";
    public static final String WORDCLOUD_ENDPOINT = "/wordcloud";

    // API Gateway
    public static final String GATEWAY_API_PREFIX = "/api";
    public static final String GATEWAY_WORKS = GATEWAY_API_PREFIX + "/works";
    public static final String GATEWAY_REPORTS = GATEWAY_API_PREFIX + "/reports";

    // HTTP Headers
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    // File Constants
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String[] ALLOWED_FILE_TYPES = {
            "text/plain",
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    };

    // Analysis Constants
    public static final double PLAGIARISM_THRESHOLD = 0.7;
    public static final int SHINGLE_SIZE = 3;
    public static final int MIN_TEXT_LENGTH = 50;

    // Error Messages
    public static final String SERVICE_UNAVAILABLE = "Service temporarily unavailable";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String INVALID_FILE_TYPE = "Invalid file type";
    public static final String FILE_SIZE_EXCEEDED = "File size exceeded";

    // Database Constants
    public static final String WORK_ENTITY_TABLE = "works";
    public static final String REPORT_ENTITY_TABLE = "reports";

    // Event Types
    public static final String EVENT_FILE_UPLOADED = "FILE_UPLOADED";
    public static final String EVENT_ANALYSIS_STARTED = "ANALYSIS_STARTED";
    public static final String EVENT_ANALYSIS_COMPLETED = "ANALYSIS_COMPLETED";
    public static final String EVENT_PLAGIARISM_DETECTED = "PLAGIARISM_DETECTED";

    // Date Formats
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // Redis Keys
    public static final String CACHE_WORK_PREFIX = "work:";
    public static final String CACHE_REPORT_PREFIX = "report:";
    public static final long CACHE_TTL_MINUTES = 30;
}