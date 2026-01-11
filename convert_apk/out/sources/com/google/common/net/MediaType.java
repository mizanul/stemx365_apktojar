package com.google.common.net;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import kotlin.text.Typography;

public final class MediaType {
    public static final MediaType ANY_APPLICATION_TYPE = createConstant(APPLICATION_TYPE, "*");
    public static final MediaType ANY_AUDIO_TYPE = createConstant(AUDIO_TYPE, "*");
    public static final MediaType ANY_IMAGE_TYPE = createConstant(IMAGE_TYPE, "*");
    public static final MediaType ANY_TEXT_TYPE = createConstant(TEXT_TYPE, "*");
    public static final MediaType ANY_TYPE = createConstant("*", "*");
    public static final MediaType ANY_VIDEO_TYPE = createConstant(VIDEO_TYPE, "*");
    private static final String APPLICATION_TYPE = "application";
    public static final MediaType ATOM_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "atom+xml");
    private static final String AUDIO_TYPE = "audio";
    public static final MediaType BZIP2 = createConstant(APPLICATION_TYPE, "x-bzip2");
    public static final MediaType CACHE_MANIFEST_UTF_8 = createConstantUtf8(TEXT_TYPE, "cache-manifest");
    private static final String CHARSET_ATTRIBUTE = "charset";
    public static final MediaType CSS_UTF_8 = createConstantUtf8(TEXT_TYPE, "css");
    public static final MediaType CSV_UTF_8 = createConstantUtf8(TEXT_TYPE, "csv");
    public static final MediaType FORM_DATA = createConstant(APPLICATION_TYPE, "x-www-form-urlencoded");
    public static final MediaType GIF = createConstant(IMAGE_TYPE, "gif");
    public static final MediaType GZIP = createConstant(APPLICATION_TYPE, "x-gzip");
    public static final MediaType HTML_UTF_8 = createConstantUtf8(TEXT_TYPE, "html");
    public static final MediaType ICO = createConstant(IMAGE_TYPE, "vnd.microsoft.icon");
    private static final String IMAGE_TYPE = "image";
    public static final MediaType I_CALENDAR_UTF_8 = createConstantUtf8(TEXT_TYPE, "calendar");
    public static final MediaType JAVASCRIPT_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "javascript");
    public static final MediaType JPEG = createConstant(IMAGE_TYPE, "jpeg");
    public static final MediaType JSON_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "json");
    public static final MediaType KML = createConstant(APPLICATION_TYPE, "vnd.google-earth.kml+xml");
    public static final MediaType KMZ = createConstant(APPLICATION_TYPE, "vnd.google-earth.kmz");
    private static final ImmutableMap<MediaType, MediaType> KNOWN_TYPES;
    private static final CharMatcher LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
    public static final MediaType MICROSOFT_EXCEL = createConstant(APPLICATION_TYPE, "vnd.ms-excel");
    public static final MediaType MICROSOFT_POWERPOINT = createConstant(APPLICATION_TYPE, "vnd.ms-powerpoint");
    public static final MediaType MICROSOFT_WORD = createConstant(APPLICATION_TYPE, "msword");
    public static final MediaType MP4_AUDIO = createConstant(AUDIO_TYPE, "mp4");
    public static final MediaType MP4_VIDEO = createConstant(VIDEO_TYPE, "mp4");
    public static final MediaType MPEG_AUDIO = createConstant(AUDIO_TYPE, "mpeg");
    public static final MediaType MPEG_VIDEO = createConstant(VIDEO_TYPE, "mpeg");
    public static final MediaType OCTET_STREAM = createConstant(APPLICATION_TYPE, "octet-stream");
    public static final MediaType OGG_AUDIO = createConstant(AUDIO_TYPE, "ogg");
    public static final MediaType OGG_CONTAINER = createConstant(APPLICATION_TYPE, "ogg");
    public static final MediaType OGG_VIDEO = createConstant(VIDEO_TYPE, "ogg");
    public static final MediaType OOXML_DOCUMENT = createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MediaType OOXML_PRESENTATION = createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.presentationml.presentation");
    public static final MediaType OOXML_SHEET = createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MediaType OPENDOCUMENT_GRAPHICS = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.graphics");
    public static final MediaType OPENDOCUMENT_PRESENTATION = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.presentation");
    public static final MediaType OPENDOCUMENT_SPREADSHEET = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.spreadsheet");
    public static final MediaType OPENDOCUMENT_TEXT = createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.text");
    private static final Joiner.MapJoiner PARAMETER_JOINER = Joiner.m19on("; ").withKeyValueSeparator("=");
    public static final MediaType PDF = createConstant(APPLICATION_TYPE, "pdf");
    public static final MediaType PLAIN_TEXT_UTF_8 = createConstantUtf8(TEXT_TYPE, "plain");
    public static final MediaType PNG = createConstant(IMAGE_TYPE, "png");
    public static final MediaType POSTSCRIPT = createConstant(APPLICATION_TYPE, "postscript");
    public static final MediaType QUICKTIME = createConstant(VIDEO_TYPE, "quicktime");
    private static final CharMatcher QUOTED_TEXT_MATCHER = CharMatcher.ASCII.and(CharMatcher.noneOf("\"\\\r"));
    public static final MediaType RTF_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "rtf");
    public static final MediaType SHOCKWAVE_FLASH = createConstant(APPLICATION_TYPE, "x-shockwave-flash");
    public static final MediaType SVG_UTF_8 = createConstantUtf8(IMAGE_TYPE, "svg+xml");
    public static final MediaType TAR = createConstant(APPLICATION_TYPE, "x-tar");
    public static final MediaType TEXT_JAVASCRIPT_UTF_8 = createConstantUtf8(TEXT_TYPE, "javascript");
    private static final String TEXT_TYPE = "text";
    public static final MediaType TIFF = createConstant(IMAGE_TYPE, "tiff");
    /* access modifiers changed from: private */
    public static final CharMatcher TOKEN_MATCHER = CharMatcher.ASCII.and(CharMatcher.JAVA_ISO_CONTROL.negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.m56of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
    public static final MediaType VCARD_UTF_8 = createConstantUtf8(TEXT_TYPE, "vcard");
    private static final String VIDEO_TYPE = "video";
    public static final MediaType WEBM_AUDIO = createConstant(AUDIO_TYPE, "webm");
    public static final MediaType WEBM_VIDEO = createConstant(VIDEO_TYPE, "webm");
    private static final String WILDCARD = "*";
    public static final MediaType WMV = createConstant(VIDEO_TYPE, "x-ms-wmv");
    public static final MediaType XHTML_UTF_8 = createConstantUtf8(APPLICATION_TYPE, "xhtml+xml");
    public static final MediaType XML_UTF_8 = createConstantUtf8(TEXT_TYPE, "xml");
    public static final MediaType ZIP = createConstant(APPLICATION_TYPE, "zip");
    private final ImmutableListMultimap<String, String> parameters;
    private final String subtype;
    private final String type;

    static {
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        MediaType mediaType = ANY_TYPE;
        ImmutableMap.Builder put = builder.put(mediaType, mediaType);
        MediaType mediaType2 = ANY_TEXT_TYPE;
        ImmutableMap.Builder put2 = put.put(mediaType2, mediaType2);
        MediaType mediaType3 = ANY_IMAGE_TYPE;
        ImmutableMap.Builder put3 = put2.put(mediaType3, mediaType3);
        MediaType mediaType4 = ANY_AUDIO_TYPE;
        ImmutableMap.Builder put4 = put3.put(mediaType4, mediaType4);
        MediaType mediaType5 = ANY_VIDEO_TYPE;
        ImmutableMap.Builder put5 = put4.put(mediaType5, mediaType5);
        MediaType mediaType6 = ANY_APPLICATION_TYPE;
        ImmutableMap.Builder put6 = put5.put(mediaType6, mediaType6);
        MediaType mediaType7 = CACHE_MANIFEST_UTF_8;
        ImmutableMap.Builder put7 = put6.put(mediaType7, mediaType7);
        MediaType mediaType8 = CSS_UTF_8;
        ImmutableMap.Builder put8 = put7.put(mediaType8, mediaType8);
        MediaType mediaType9 = CSV_UTF_8;
        ImmutableMap.Builder put9 = put8.put(mediaType9, mediaType9);
        MediaType mediaType10 = HTML_UTF_8;
        ImmutableMap.Builder put10 = put9.put(mediaType10, mediaType10);
        MediaType mediaType11 = I_CALENDAR_UTF_8;
        ImmutableMap.Builder put11 = put10.put(mediaType11, mediaType11);
        MediaType mediaType12 = PLAIN_TEXT_UTF_8;
        ImmutableMap.Builder put12 = put11.put(mediaType12, mediaType12);
        MediaType mediaType13 = TEXT_JAVASCRIPT_UTF_8;
        ImmutableMap.Builder put13 = put12.put(mediaType13, mediaType13);
        MediaType mediaType14 = VCARD_UTF_8;
        ImmutableMap.Builder put14 = put13.put(mediaType14, mediaType14);
        MediaType mediaType15 = XML_UTF_8;
        ImmutableMap.Builder put15 = put14.put(mediaType15, mediaType15);
        MediaType mediaType16 = GIF;
        ImmutableMap.Builder put16 = put15.put(mediaType16, mediaType16);
        MediaType mediaType17 = ICO;
        ImmutableMap.Builder put17 = put16.put(mediaType17, mediaType17);
        MediaType mediaType18 = JPEG;
        ImmutableMap.Builder put18 = put17.put(mediaType18, mediaType18);
        MediaType mediaType19 = PNG;
        ImmutableMap.Builder put19 = put18.put(mediaType19, mediaType19);
        MediaType mediaType20 = SVG_UTF_8;
        ImmutableMap.Builder put20 = put19.put(mediaType20, mediaType20);
        MediaType mediaType21 = TIFF;
        ImmutableMap.Builder put21 = put20.put(mediaType21, mediaType21);
        MediaType mediaType22 = MP4_AUDIO;
        ImmutableMap.Builder put22 = put21.put(mediaType22, mediaType22);
        MediaType mediaType23 = MPEG_AUDIO;
        ImmutableMap.Builder put23 = put22.put(mediaType23, mediaType23);
        MediaType mediaType24 = OGG_AUDIO;
        ImmutableMap.Builder put24 = put23.put(mediaType24, mediaType24);
        MediaType mediaType25 = WEBM_AUDIO;
        ImmutableMap.Builder put25 = put24.put(mediaType25, mediaType25);
        MediaType mediaType26 = MP4_VIDEO;
        ImmutableMap.Builder put26 = put25.put(mediaType26, mediaType26);
        MediaType mediaType27 = MPEG_VIDEO;
        ImmutableMap.Builder put27 = put26.put(mediaType27, mediaType27);
        MediaType mediaType28 = OGG_VIDEO;
        ImmutableMap.Builder put28 = put27.put(mediaType28, mediaType28);
        MediaType mediaType29 = QUICKTIME;
        ImmutableMap.Builder put29 = put28.put(mediaType29, mediaType29);
        MediaType mediaType30 = WEBM_VIDEO;
        ImmutableMap.Builder put30 = put29.put(mediaType30, mediaType30);
        MediaType mediaType31 = WMV;
        ImmutableMap.Builder put31 = put30.put(mediaType31, mediaType31);
        MediaType mediaType32 = ATOM_UTF_8;
        ImmutableMap.Builder put32 = put31.put(mediaType32, mediaType32);
        MediaType mediaType33 = BZIP2;
        ImmutableMap.Builder put33 = put32.put(mediaType33, mediaType33);
        MediaType mediaType34 = FORM_DATA;
        ImmutableMap.Builder put34 = put33.put(mediaType34, mediaType34);
        MediaType mediaType35 = GZIP;
        ImmutableMap.Builder put35 = put34.put(mediaType35, mediaType35);
        MediaType mediaType36 = JAVASCRIPT_UTF_8;
        ImmutableMap.Builder put36 = put35.put(mediaType36, mediaType36);
        MediaType mediaType37 = JSON_UTF_8;
        ImmutableMap.Builder put37 = put36.put(mediaType37, mediaType37);
        MediaType mediaType38 = KML;
        ImmutableMap.Builder put38 = put37.put(mediaType38, mediaType38);
        MediaType mediaType39 = KMZ;
        ImmutableMap.Builder put39 = put38.put(mediaType39, mediaType39);
        MediaType mediaType40 = MICROSOFT_EXCEL;
        ImmutableMap.Builder put40 = put39.put(mediaType40, mediaType40);
        MediaType mediaType41 = MICROSOFT_POWERPOINT;
        ImmutableMap.Builder put41 = put40.put(mediaType41, mediaType41);
        MediaType mediaType42 = MICROSOFT_WORD;
        ImmutableMap.Builder put42 = put41.put(mediaType42, mediaType42);
        MediaType mediaType43 = OCTET_STREAM;
        ImmutableMap.Builder put43 = put42.put(mediaType43, mediaType43);
        MediaType mediaType44 = OGG_CONTAINER;
        ImmutableMap.Builder put44 = put43.put(mediaType44, mediaType44);
        MediaType mediaType45 = OOXML_DOCUMENT;
        ImmutableMap.Builder put45 = put44.put(mediaType45, mediaType45);
        MediaType mediaType46 = OOXML_PRESENTATION;
        ImmutableMap.Builder put46 = put45.put(mediaType46, mediaType46);
        MediaType mediaType47 = OOXML_SHEET;
        ImmutableMap.Builder put47 = put46.put(mediaType47, mediaType47);
        MediaType mediaType48 = OPENDOCUMENT_GRAPHICS;
        ImmutableMap.Builder put48 = put47.put(mediaType48, mediaType48);
        MediaType mediaType49 = OPENDOCUMENT_PRESENTATION;
        ImmutableMap.Builder put49 = put48.put(mediaType49, mediaType49);
        MediaType mediaType50 = OPENDOCUMENT_SPREADSHEET;
        ImmutableMap.Builder put50 = put49.put(mediaType50, mediaType50);
        MediaType mediaType51 = OPENDOCUMENT_TEXT;
        ImmutableMap.Builder put51 = put50.put(mediaType51, mediaType51);
        MediaType mediaType52 = PDF;
        ImmutableMap.Builder put52 = put51.put(mediaType52, mediaType52);
        MediaType mediaType53 = POSTSCRIPT;
        ImmutableMap.Builder put53 = put52.put(mediaType53, mediaType53);
        MediaType mediaType54 = RTF_UTF_8;
        ImmutableMap.Builder put54 = put53.put(mediaType54, mediaType54);
        MediaType mediaType55 = SHOCKWAVE_FLASH;
        ImmutableMap.Builder put55 = put54.put(mediaType55, mediaType55);
        MediaType mediaType56 = TAR;
        ImmutableMap.Builder put56 = put55.put(mediaType56, mediaType56);
        MediaType mediaType57 = XHTML_UTF_8;
        ImmutableMap.Builder put57 = put56.put(mediaType57, mediaType57);
        MediaType mediaType58 = ZIP;
        KNOWN_TYPES = put57.put(mediaType58, mediaType58).build();
    }

    private MediaType(String type2, String subtype2, ImmutableListMultimap<String, String> parameters2) {
        this.type = type2;
        this.subtype = subtype2;
        this.parameters = parameters2;
    }

    private static MediaType createConstant(String type2, String subtype2) {
        return new MediaType(type2, subtype2, ImmutableListMultimap.m55of());
    }

    private static MediaType createConstantUtf8(String type2, String subtype2) {
        return new MediaType(type2, subtype2, UTF_8_CONSTANT_PARAMETERS);
    }

    public String type() {
        return this.type;
    }

    public String subtype() {
        return this.subtype;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.parameters;
    }

    private Map<String, ImmutableMultiset<String>> parametersAsMap() {
        return Maps.transformValues(this.parameters.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>() {
            public ImmutableMultiset<String> apply(Collection<String> input) {
                return ImmutableMultiset.copyOf(input);
            }
        });
    }

    public Optional<Charset> charset() {
        ImmutableSet<String> charsetValues = ImmutableSet.copyOf(this.parameters.get((Object) "charset"));
        int size = charsetValues.size();
        if (size == 0) {
            return Optional.absent();
        }
        if (size == 1) {
            return Optional.m20of(Charset.forName((String) Iterables.getOnlyElement(charsetValues)));
        }
        throw new IllegalStateException("Multiple charset values defined: " + charsetValues);
    }

    public MediaType withoutParameters() {
        return this.parameters.isEmpty() ? this : create(this.type, this.subtype);
    }

    public MediaType withParameters(Multimap<String, String> parameters2) {
        return create(this.type, this.subtype, parameters2);
    }

    public MediaType withParameter(String attribute, String value) {
        Preconditions.checkNotNull(attribute);
        Preconditions.checkNotNull(value);
        String normalizedAttribute = normalizeToken(attribute);
        ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
        Iterator i$ = this.parameters.entries().iterator();
        while (i$.hasNext()) {
            Map.Entry<String, String> entry = i$.next();
            String key = entry.getKey();
            if (!normalizedAttribute.equals(key)) {
                builder.put(key, entry.getValue());
            }
        }
        builder.put(normalizedAttribute, normalizeParameterValue(normalizedAttribute, value));
        MediaType mediaType = new MediaType(this.type, this.subtype, builder.build());
        return (MediaType) Objects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        return withParameter("charset", charset.name());
    }

    public boolean hasWildcard() {
        return "*".equals(this.type) || "*".equals(this.subtype);
    }

    /* renamed from: is */
    public boolean mo10948is(MediaType mediaTypeRange) {
        return (mediaTypeRange.type.equals("*") || mediaTypeRange.type.equals(this.type)) && (mediaTypeRange.subtype.equals("*") || mediaTypeRange.subtype.equals(this.subtype)) && this.parameters.entries().containsAll(mediaTypeRange.parameters.entries());
    }

    public static MediaType create(String type2, String subtype2) {
        return create(type2, subtype2, ImmutableListMultimap.m55of());
    }

    static MediaType createApplicationType(String subtype2) {
        return create(APPLICATION_TYPE, subtype2);
    }

    static MediaType createAudioType(String subtype2) {
        return create(AUDIO_TYPE, subtype2);
    }

    static MediaType createImageType(String subtype2) {
        return create(IMAGE_TYPE, subtype2);
    }

    static MediaType createTextType(String subtype2) {
        return create(TEXT_TYPE, subtype2);
    }

    static MediaType createVideoType(String subtype2) {
        return create(VIDEO_TYPE, subtype2);
    }

    private static MediaType create(String type2, String subtype2, Multimap<String, String> parameters2) {
        Preconditions.checkNotNull(type2);
        Preconditions.checkNotNull(subtype2);
        Preconditions.checkNotNull(parameters2);
        String normalizedType = normalizeToken(type2);
        String normalizedSubtype = normalizeToken(subtype2);
        Preconditions.checkArgument(!"*".equals(normalizedType) || "*".equals(normalizedSubtype), "A wildcard type cannot be used with a non-wildcard subtype");
        ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
        for (Map.Entry<String, String> entry : parameters2.entries()) {
            String attribute = normalizeToken(entry.getKey());
            builder.put(attribute, normalizeParameterValue(attribute, entry.getValue()));
        }
        MediaType mediaType = new MediaType(normalizedType, normalizedSubtype, builder.build());
        return (MediaType) Objects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    private static String normalizeToken(String token) {
        Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(token));
        return Ascii.toLowerCase(token);
    }

    private static String normalizeParameterValue(String attribute, String value) {
        return "charset".equals(attribute) ? Ascii.toLowerCase(value) : value;
    }

    public static MediaType parse(String input) {
        String value;
        Preconditions.checkNotNull(input);
        Tokenizer tokenizer = new Tokenizer(input);
        try {
            String type2 = tokenizer.consumeToken(TOKEN_MATCHER);
            tokenizer.consumeCharacter('/');
            String subtype2 = tokenizer.consumeToken(TOKEN_MATCHER);
            ImmutableListMultimap.Builder<String, String> parameters2 = ImmutableListMultimap.builder();
            while (tokenizer.hasMore()) {
                tokenizer.consumeCharacter(';');
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                String attribute = tokenizer.consumeToken(TOKEN_MATCHER);
                tokenizer.consumeCharacter('=');
                if ('\"' == tokenizer.previewChar()) {
                    tokenizer.consumeCharacter((char) Typography.quote);
                    StringBuilder valueBuilder = new StringBuilder();
                    while ('\"' != tokenizer.previewChar()) {
                        if ('\\' == tokenizer.previewChar()) {
                            tokenizer.consumeCharacter('\\');
                            valueBuilder.append(tokenizer.consumeCharacter(CharMatcher.ASCII));
                        } else {
                            valueBuilder.append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
                        }
                    }
                    value = valueBuilder.toString();
                    tokenizer.consumeCharacter((char) Typography.quote);
                } else {
                    value = tokenizer.consumeToken(TOKEN_MATCHER);
                }
                parameters2.put(attribute, value);
            }
            return create(type2, subtype2, parameters2.build());
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static final class Tokenizer {
        final String input;
        int position = 0;

        Tokenizer(String input2) {
            this.input = input2;
        }

        /* access modifiers changed from: package-private */
        public String consumeTokenIfPresent(CharMatcher matcher) {
            Preconditions.checkState(hasMore());
            int startPosition = this.position;
            this.position = matcher.negate().indexIn(this.input, startPosition);
            return hasMore() ? this.input.substring(startPosition, this.position) : this.input.substring(startPosition);
        }

        /* access modifiers changed from: package-private */
        public String consumeToken(CharMatcher matcher) {
            int startPosition = this.position;
            String token = consumeTokenIfPresent(matcher);
            Preconditions.checkState(this.position != startPosition);
            return token;
        }

        /* access modifiers changed from: package-private */
        public char consumeCharacter(CharMatcher matcher) {
            Preconditions.checkState(hasMore());
            char c = previewChar();
            Preconditions.checkState(matcher.matches(c));
            this.position++;
            return c;
        }

        /* access modifiers changed from: package-private */
        public char consumeCharacter(char c) {
            Preconditions.checkState(hasMore());
            Preconditions.checkState(previewChar() == c);
            this.position++;
            return c;
        }

        /* access modifiers changed from: package-private */
        public char previewChar() {
            Preconditions.checkState(hasMore());
            return this.input.charAt(this.position);
        }

        /* access modifiers changed from: package-private */
        public boolean hasMore() {
            int i = this.position;
            return i >= 0 && i < this.input.length();
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MediaType)) {
            return false;
        }
        MediaType that = (MediaType) obj;
        if (!this.type.equals(that.type) || !this.subtype.equals(that.subtype) || !parametersAsMap().equals(that.parametersAsMap())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.type, this.subtype, parametersAsMap());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append('/');
        StringBuilder builder = sb.append(this.subtype);
        if (!this.parameters.isEmpty()) {
            builder.append("; ");
            PARAMETER_JOINER.appendTo(builder, (Iterable<? extends Map.Entry<?, ?>>) Multimaps.transformValues(this.parameters, new Function<String, String>() {
                public String apply(String value) {
                    return MediaType.TOKEN_MATCHER.matchesAllOf(value) ? value : MediaType.escapeAndQuote(value);
                }
            }).entries());
        }
        return builder.toString();
    }

    /* access modifiers changed from: private */
    public static String escapeAndQuote(String value) {
        StringBuilder escaped = new StringBuilder(value.length() + 16).append(Typography.quote);
        for (char ch : value.toCharArray()) {
            if (ch == 13 || ch == '\\' || ch == '\"') {
                escaped.append('\\');
            }
            escaped.append(ch);
        }
        escaped.append(Typography.quote);
        return escaped.toString();
    }
}
