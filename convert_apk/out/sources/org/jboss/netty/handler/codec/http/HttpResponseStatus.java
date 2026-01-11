package org.jboss.netty.handler.codec.http;

import org.opencv.videoio.Videoio;

public class HttpResponseStatus implements Comparable<HttpResponseStatus> {
    public static final HttpResponseStatus ACCEPTED = new HttpResponseStatus(202, "Accepted");
    public static final HttpResponseStatus BAD_GATEWAY = new HttpResponseStatus(502, "Bad Gateway");
    public static final HttpResponseStatus BAD_REQUEST = new HttpResponseStatus(Videoio.CAP_PROP_XI_DOWNSAMPLING, "Bad Request");
    public static final HttpResponseStatus CONFLICT = new HttpResponseStatus(Videoio.CAP_PROP_XI_GPO_SELECTOR, "Conflict");
    public static final HttpResponseStatus CONTINUE = new HttpResponseStatus(100, "Continue");
    public static final HttpResponseStatus CREATED = new HttpResponseStatus(201, "Created");
    public static final HttpResponseStatus EXPECTATION_FAILED = new HttpResponseStatus(Videoio.CAP_PROP_XI_AE_MAX_LIMIT, "Expectation Failed");
    public static final HttpResponseStatus FAILED_DEPENDENCY = new HttpResponseStatus(Videoio.CAP_PROP_XI_GAIN, "Failed Dependency");
    public static final HttpResponseStatus FORBIDDEN = new HttpResponseStatus(Videoio.CAP_PROP_XI_OFFSET_Y, "Forbidden");
    public static final HttpResponseStatus FOUND = new HttpResponseStatus(Videoio.CAP_PROP_PVAPI_DECIMATIONHORIZONTAL, "Found");
    public static final HttpResponseStatus GATEWAY_TIMEOUT = new HttpResponseStatus(504, "Gateway Timeout");
    public static final HttpResponseStatus GONE = new HttpResponseStatus(Videoio.CAP_PROP_XI_GPO_MODE, "Gone");
    public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED = new HttpResponseStatus(505, "HTTP Version Not Supported");
    public static final HttpResponseStatus INSUFFICIENT_STORAGE = new HttpResponseStatus(Videoio.CAP_PROP_XI_DEBOUNCE_EN, "Insufficient Storage");
    public static final HttpResponseStatus INTERNAL_SERVER_ERROR = new HttpResponseStatus(Videoio.CAP_QT, "Internal Server Error");
    public static final HttpResponseStatus LENGTH_REQUIRED = new HttpResponseStatus(Videoio.CAP_PROP_XI_LED_SELECTOR, "Length Required");
    public static final HttpResponseStatus LOCKED = new HttpResponseStatus(Videoio.CAP_PROP_XI_GAIN_SELECTOR, "Locked");
    public static final HttpResponseStatus METHOD_NOT_ALLOWED = new HttpResponseStatus(Videoio.CAP_PROP_XI_TRG_SOFTWARE, "Method Not Allowed");
    public static final HttpResponseStatus MOVED_PERMANENTLY = new HttpResponseStatus(Videoio.CAP_PROP_PVAPI_FRAMESTARTTRIGGERMODE, "Moved Permanently");
    public static final HttpResponseStatus MULTIPLE_CHOICES = new HttpResponseStatus(300, "Multiple Choices");
    public static final HttpResponseStatus MULTI_STATUS = new HttpResponseStatus(207, "Multi-Status");
    public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION = new HttpResponseStatus(203, "Non-Authoritative Information");
    public static final HttpResponseStatus NOT_ACCEPTABLE = new HttpResponseStatus(Videoio.CAP_PROP_XI_GPI_SELECTOR, "Not Acceptable");
    public static final HttpResponseStatus NOT_EXTENDED = new HttpResponseStatus(Videoio.CAP_PROP_XI_DEBOUNCE_POL, "Not Extended");
    public static final HttpResponseStatus NOT_FOUND = new HttpResponseStatus(Videoio.CAP_PROP_XI_TRG_SOURCE, "Not Found");
    public static final HttpResponseStatus NOT_IMPLEMENTED = new HttpResponseStatus(501, "Not Implemented");
    public static final HttpResponseStatus NOT_MODIFIED = new HttpResponseStatus(Videoio.CAP_PROP_PVAPI_BINNINGX, "Not Modified");
    public static final HttpResponseStatus NO_CONTENT = new HttpResponseStatus(204, "No Content");

    /* renamed from: OK */
    public static final HttpResponseStatus f134OK = new HttpResponseStatus(200, "OK");
    public static final HttpResponseStatus PARTIAL_CONTENT = new HttpResponseStatus(206, "Partial Content");
    public static final HttpResponseStatus PAYMENT_REQUIRED = new HttpResponseStatus(Videoio.CAP_PROP_XI_OFFSET_X, "Payment Required");
    public static final HttpResponseStatus PRECONDITION_FAILED = new HttpResponseStatus(Videoio.CAP_PROP_XI_LED_MODE, "Precondition Failed");
    public static final HttpResponseStatus PROCESSING = new HttpResponseStatus(102, "Processing");
    public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED = new HttpResponseStatus(Videoio.CAP_PROP_XI_GPI_MODE, "Proxy Authentication Required");
    public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE = new HttpResponseStatus(Videoio.CAP_PROP_XI_EXP_PRIORITY, "Requested Range Not Satisfiable");
    public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE = new HttpResponseStatus(Videoio.CAP_PROP_XI_MANUAL_WB, "Request Entity Too Large");
    public static final HttpResponseStatus REQUEST_TIMEOUT = new HttpResponseStatus(Videoio.CAP_PROP_XI_GPI_LEVEL, "Request Timeout");
    public static final HttpResponseStatus REQUEST_URI_TOO_LONG = new HttpResponseStatus(Videoio.CAP_PROP_XI_AUTO_WB, "Request-URI Too Long");
    public static final HttpResponseStatus RESET_CONTENT = new HttpResponseStatus(205, "Reset Content");
    public static final HttpResponseStatus SEE_OTHER = new HttpResponseStatus(Videoio.CAP_PROP_PVAPI_DECIMATIONVERTICAL, "See Other");
    public static final HttpResponseStatus SERVICE_UNAVAILABLE = new HttpResponseStatus(503, "Service Unavailable");
    public static final HttpResponseStatus SWITCHING_PROTOCOLS = new HttpResponseStatus(101, "Switching Protocols");
    public static final HttpResponseStatus TEMPORARY_REDIRECT = new HttpResponseStatus(307, "Temporary Redirect");
    public static final HttpResponseStatus UNAUTHORIZED = new HttpResponseStatus(Videoio.CAP_PROP_XI_DATA_FORMAT, "Unauthorized");
    public static final HttpResponseStatus UNORDERED_COLLECTION = new HttpResponseStatus(425, "Unordered Collection");
    public static final HttpResponseStatus UNPROCESSABLE_ENTITY = new HttpResponseStatus(Videoio.CAP_PROP_XI_EXPOSURE_BURST_COUNT, "Unprocessable Entity");
    public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE = new HttpResponseStatus(Videoio.CAP_PROP_XI_AEAG, "Unsupported Media Type");
    public static final HttpResponseStatus UPGRADE_REQUIRED = new HttpResponseStatus(Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE, "Upgrade Required");
    public static final HttpResponseStatus USE_PROXY = new HttpResponseStatus(Videoio.CAP_PROP_PVAPI_BINNINGY, "Use Proxy");
    public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES = new HttpResponseStatus(506, "Variant Also Negotiates");
    private final int code;
    private final String reasonPhrase;

    public static HttpResponseStatus valueOf(int code2) {
        String reasonPhrase2;
        if (code2 == 307) {
            return TEMPORARY_REDIRECT;
        }
        if (code2 == 510) {
            return NOT_EXTENDED;
        }
        switch (code2) {
            case 100:
                return CONTINUE;
            case 101:
                return SWITCHING_PROTOCOLS;
            case 102:
                return PROCESSING;
            default:
                switch (code2) {
                    case 200:
                        return f134OK;
                    case 201:
                        return CREATED;
                    case 202:
                        return ACCEPTED;
                    case 203:
                        return NON_AUTHORITATIVE_INFORMATION;
                    case 204:
                        return NO_CONTENT;
                    case 205:
                        return RESET_CONTENT;
                    case 206:
                        return PARTIAL_CONTENT;
                    case 207:
                        return MULTI_STATUS;
                    default:
                        switch (code2) {
                            case 300:
                                return MULTIPLE_CHOICES;
                            case Videoio.CAP_PROP_PVAPI_FRAMESTARTTRIGGERMODE /*301*/:
                                return MOVED_PERMANENTLY;
                            case Videoio.CAP_PROP_PVAPI_DECIMATIONHORIZONTAL /*302*/:
                                return FOUND;
                            case Videoio.CAP_PROP_PVAPI_DECIMATIONVERTICAL /*303*/:
                                return SEE_OTHER;
                            case Videoio.CAP_PROP_PVAPI_BINNINGX /*304*/:
                                return NOT_MODIFIED;
                            case Videoio.CAP_PROP_PVAPI_BINNINGY /*305*/:
                                return USE_PROXY;
                            default:
                                switch (code2) {
                                    case Videoio.CAP_PROP_XI_DOWNSAMPLING /*400*/:
                                        return BAD_REQUEST;
                                    case Videoio.CAP_PROP_XI_DATA_FORMAT /*401*/:
                                        return UNAUTHORIZED;
                                    case Videoio.CAP_PROP_XI_OFFSET_X /*402*/:
                                        return PAYMENT_REQUIRED;
                                    case Videoio.CAP_PROP_XI_OFFSET_Y /*403*/:
                                        return FORBIDDEN;
                                    case Videoio.CAP_PROP_XI_TRG_SOURCE /*404*/:
                                        return NOT_FOUND;
                                    case Videoio.CAP_PROP_XI_TRG_SOFTWARE /*405*/:
                                        return METHOD_NOT_ALLOWED;
                                    case Videoio.CAP_PROP_XI_GPI_SELECTOR /*406*/:
                                        return NOT_ACCEPTABLE;
                                    case Videoio.CAP_PROP_XI_GPI_MODE /*407*/:
                                        return PROXY_AUTHENTICATION_REQUIRED;
                                    case Videoio.CAP_PROP_XI_GPI_LEVEL /*408*/:
                                        return REQUEST_TIMEOUT;
                                    case Videoio.CAP_PROP_XI_GPO_SELECTOR /*409*/:
                                        return CONFLICT;
                                    case Videoio.CAP_PROP_XI_GPO_MODE /*410*/:
                                        return GONE;
                                    case Videoio.CAP_PROP_XI_LED_SELECTOR /*411*/:
                                        return LENGTH_REQUIRED;
                                    case Videoio.CAP_PROP_XI_LED_MODE /*412*/:
                                        return PRECONDITION_FAILED;
                                    case Videoio.CAP_PROP_XI_MANUAL_WB /*413*/:
                                        return REQUEST_ENTITY_TOO_LARGE;
                                    case Videoio.CAP_PROP_XI_AUTO_WB /*414*/:
                                        return REQUEST_URI_TOO_LONG;
                                    case Videoio.CAP_PROP_XI_AEAG /*415*/:
                                        return UNSUPPORTED_MEDIA_TYPE;
                                    case Videoio.CAP_PROP_XI_EXP_PRIORITY /*416*/:
                                        return REQUESTED_RANGE_NOT_SATISFIABLE;
                                    case Videoio.CAP_PROP_XI_AE_MAX_LIMIT /*417*/:
                                        return EXPECTATION_FAILED;
                                    default:
                                        switch (code2) {
                                            case Videoio.CAP_PROP_XI_EXPOSURE_BURST_COUNT /*422*/:
                                                return UNPROCESSABLE_ENTITY;
                                            case Videoio.CAP_PROP_XI_GAIN_SELECTOR /*423*/:
                                                return LOCKED;
                                            case Videoio.CAP_PROP_XI_GAIN /*424*/:
                                                return FAILED_DEPENDENCY;
                                            case 425:
                                                return UNORDERED_COLLECTION;
                                            case Videoio.CAP_PROP_XI_DOWNSAMPLING_TYPE /*426*/:
                                                return UPGRADE_REQUIRED;
                                            default:
                                                switch (code2) {
                                                    case Videoio.CAP_QT /*500*/:
                                                        return INTERNAL_SERVER_ERROR;
                                                    case 501:
                                                        return NOT_IMPLEMENTED;
                                                    case 502:
                                                        return BAD_GATEWAY;
                                                    case 503:
                                                        return SERVICE_UNAVAILABLE;
                                                    case 504:
                                                        return GATEWAY_TIMEOUT;
                                                    case 505:
                                                        return HTTP_VERSION_NOT_SUPPORTED;
                                                    case 506:
                                                        return VARIANT_ALSO_NEGOTIATES;
                                                    case Videoio.CAP_PROP_XI_DEBOUNCE_EN /*507*/:
                                                        return INSUFFICIENT_STORAGE;
                                                    default:
                                                        if (code2 < 100) {
                                                            reasonPhrase2 = "Unknown Status";
                                                        } else if (code2 < 200) {
                                                            reasonPhrase2 = "Informational";
                                                        } else if (code2 < 300) {
                                                            reasonPhrase2 = "Successful";
                                                        } else if (code2 < 400) {
                                                            reasonPhrase2 = "Redirection";
                                                        } else if (code2 < 500) {
                                                            reasonPhrase2 = "Client Error";
                                                        } else if (code2 < 600) {
                                                            reasonPhrase2 = "Server Error";
                                                        } else {
                                                            reasonPhrase2 = "Unknown Status";
                                                        }
                                                        return new HttpResponseStatus(code2, reasonPhrase2 + " (" + code2 + ')');
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public HttpResponseStatus(int code2, String reasonPhrase2) {
        if (code2 < 0) {
            throw new IllegalArgumentException("code: " + code2 + " (expected: 0+)");
        } else if (reasonPhrase2 != null) {
            for (int i = 0; i < reasonPhrase2.length(); i++) {
                char c = reasonPhrase2.charAt(i);
                if (c == 10 || c == 13) {
                    throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + reasonPhrase2);
                }
            }
            this.code = code2;
            this.reasonPhrase = reasonPhrase2;
        } else {
            throw new NullPointerException("reasonPhrase");
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public int hashCode() {
        return getCode();
    }

    public boolean equals(Object o) {
        if ((o instanceof HttpResponseStatus) && getCode() == ((HttpResponseStatus) o).getCode()) {
            return true;
        }
        return false;
    }

    public int compareTo(HttpResponseStatus o) {
        return getCode() - o.getCode();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(this.reasonPhrase.length() + 5);
        buf.append(this.code);
        buf.append(' ');
        buf.append(this.reasonPhrase);
        return buf.toString();
    }
}
