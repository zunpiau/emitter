package io.github.zunpiau.emitter;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class LengthMatcher extends TypeSafeMatcher<String> {

    private final int expectLength;

    public LengthMatcher(int expectLength) {
        this.expectLength = expectLength;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.valueOf(expectLength));
    }

    @Override
    public boolean matchesSafely(String item) {
        return item.length() == expectLength;
    }

    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText(String.valueOf(item.length()));
    }

}
