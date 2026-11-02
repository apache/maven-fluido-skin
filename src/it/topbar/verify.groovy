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
String html = index.text
// active menu
assert html.contains( '<li><a>About</a></li>' )
// inactive menu
assert html.contains( '<li><a href="summary.html">Summary</a></li>' )
assert html.contains( '<li><a href="plugins.html">Plugins</a></li>' )
// breadcrumb
assert html.contains( '<li><a href="https://www.apache.org/">Apache</a><span class="divider">/</span></li>' )

// MSKINS-263 - image without class in banner
assert html.contains('<div class="pull-left"><div id="bannerLeft"><h1><a href="../../../"><img src="../../../images/apache-maven-project-light-theme.svg" alt="Apache Maven Fluido Skin IT, topbar layout" style="width: 489px;height: 83px;" /></a></h1></div></div>')
assert html.contains('<div class="pull-right"><div id="bannerRight"><h1><img src="../../../images/logos/MavenLogoLeaf.svg" alt="" style="width: 340px;height: 83px;" /></h1></div></div>')

// edit link after the page title: the SVG icon, copied with the site
assert html =~ /<a href="[^"]+"><img src="\.\/images\/edit\.svg" alt="Edit" width="14" height="14" \/><\/a><\/li>/
assert new File( basedir, "target/site/images/edit.svg" ).exists()
assert !new File( basedir, "target/site/images/accessories-text-editor.png" ).exists()
