package com.softserve.teamproject;

import java.util.Collection;
import java.util.stream.Collectors;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;

public class StringContainsOneOf extends BaseMatcher<String> {

  private Collection<StringContains> matchers;

  public StringContainsOneOf(Collection<StringContains> matchers) {
    this.matchers = matchers;
  }

  public static Matcher<String> containsOneOfStrings(Collection<String> substrings) {
    return new StringContainsOneOf(
        substrings.stream().map(StringContains::new).collect(Collectors.toList()));
  }


  @Override
  public boolean matches(Object o) {
    for (StringContains matcher : matchers) {
      if (matcher.matches(o)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void describeTo(Description description) {
    matchers.forEach(matcher ->{
      matcher.describeTo(description);
      description.appendText(" or ");
    });
  }
}
