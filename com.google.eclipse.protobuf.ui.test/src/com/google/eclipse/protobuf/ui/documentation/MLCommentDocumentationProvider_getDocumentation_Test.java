/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.ui.documentation;

import static com.google.eclipse.protobuf.junit.core.Setups.unitTestSetup;
import static com.google.eclipse.protobuf.junit.core.XtextRule.createWith;
import static com.google.eclipse.protobuf.util.SystemProperties.lineSeparator;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.eclipse.emf.ecore.EObject;
import org.junit.*;

import com.google.eclipse.protobuf.junit.core.XtextRule;
import com.google.eclipse.protobuf.protobuf.Property;

/**
 * Tests for <code>{@link MLCommentDocumentationProvider#getDocumentation(EObject)}</code>
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class MLCommentDocumentationProvider_getDocumentation_Test {

  @Rule public XtextRule xtext = createWith(unitTestSetup());

  private MLCommentDocumentationProvider provider;

  @Before public void setUp() {
    provider = xtext.getInstanceOf(MLCommentDocumentationProvider.class);
  }

  // message Person {
  //   /* This comment should be ignored. */
  //   /*
  //    * Indicates whether the person is active or not.
  //    * (Optional.)
  //    */
  //   optional bool active = 1; 
  // }
  @Test public void should_return_single_line_comment_of_element() {
    Property active = xtext.find("active", Property.class);
    String documentation = provider.getDocumentation(active);
    String[] lines = documentation.split(lineSeparator());
    assertThat(lines[0], equalTo("Indicates whether the person is active or not."));
    assertThat(lines[1], equalTo("(Optional.)"));
  }
  
  // message Person {
  //   optional bool active = 1; 
  // }
  @Test public void should_return_empty_String_if_element_does_not_have_single_line_comment() {
    Property active = xtext.find("active", Property.class);
    String documentation = provider.getDocumentation(active);
    assertThat(documentation, equalTo(""));
  }
}
