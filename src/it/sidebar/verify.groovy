/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

File index = new File( basedir, "target/site/index.html" )
assert index.exists()

// MSKINS-130 check that html structure is as clean as possible
String html = index.getText()
// active menu
assert html.contains( '<li class="active nav-item"><a><span class="none"></span>Introduction</a></li>' )
// inactive menu
assert html.contains( '<li class="nav-item"><a id="SummaryLink" href="summary.html" title="Summary">Summary<span id="SummaryLinkSpan" class="none"></span></a></li>' )
assert html.contains( '<li class="nav-item"><a id="PluginsLink" href="plugins.html" title="Plugins">Plugins<span id="PluginsLinkSpan" class="none"></span></a></li>' )
// breadcrumb
assert html.contains( '<li id="breadcrumbApacheListItem" class=""><a id="ApacheLink" href="https://www.apache.org/" class="externalLink" title="Apache">Apache</a><span id="breadcrumbApacheSpanDivider" class="divider">/</span></li>' )
