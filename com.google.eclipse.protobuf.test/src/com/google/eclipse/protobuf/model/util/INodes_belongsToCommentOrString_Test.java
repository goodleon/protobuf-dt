/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.model.util;

import static com.google.eclipse.protobuf.junit.core.Setups.unitTestSetup;
import static com.google.eclipse.protobuf.junit.core.XtextRule.createWith;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.getNode;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.eclipse.xtext.nodemodel.*;
import org.junit.*;

import com.google.eclipse.protobuf.junit.core.XtextRule;

/**
 * Test for <code>{@link INodes#belongsToCommentOrString(INode)}</code>
 * 
 * @author alruiz@google.com (Alex Ruiz)
 */
public class INodes_belongsToCommentOrString_Test {

  @Rule public XtextRule xtext = createWith(unitTestSetup());
  
  private INodes nodes;
  
  @Before public void setUp() {
    nodes = xtext.getInstanceOf(INodes.class);
  }

  // // This is a test.
  // message Person {}
  @Test public void should_return_true_if_node_belongs_to_single_line_comment() {
    ILeafNode commentNode = xtext.find("// This is a test.");
    assertThat(nodes.belongsToCommentOrString(commentNode), equalTo(true));
  }
  
  // /* This is a test. */
  // message Person {}
  @Test public void should_return_true_if_node_belongs_to_multiple_line_comment() {
    ILeafNode commentNode = xtext.find("/* This is a test. */");
    assertThat(nodes.belongsToCommentOrString(commentNode), equalTo(true));
  }

  // message Person {
  //   optional string name = 1 [default = 'Alex'];
  // }
  @Test public void should_return_true_if_node_belongs_to_string() {
    ILeafNode node = xtext.find("'Alex'");
    assertThat(nodes.belongsToCommentOrString(node), equalTo(true));
  }

  // message Person {}
  @Test public void should_return_false_if_node_does_not_belong_to_any_comment_or_string() {
    ICompositeNode node = getNode(xtext.root());
    assertThat(nodes.belongsToCommentOrString(node), equalTo(false));
  }
}
