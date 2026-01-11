package android.support.p002v4.p006os;

import java.util.Locale;

/* renamed from: android.support.v4.os.LocaleListInterface */
interface LocaleListInterface {
    boolean equals(Object obj);

    Locale get(int i);

    Locale getFirstMatch(String[] strArr);

    Object getLocaleList();

    int hashCode();

    int indexOf(Locale locale);

    boolean isEmpty();

    void setLocaleList(Locale... localeArr);

    int size();

    String toLanguageTags();

    String toString();
}
