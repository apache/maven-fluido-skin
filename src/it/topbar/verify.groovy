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
assert html.contains( '<li><a class="dropdown-item">About</a></li>' )
// inactive menu
assert html.contains( '<li><a href="summary.html" class="dropdown-item">Summary</a></li>' )
assert html.contains( '<li><a href="plugins.html" class="dropdown-item">Plugins</a></li>' )
// breadcrumb
assert html.contains( '<li class="breadcrumb-item"><a href="https://www.apache.org/">Apache</a></li>' )

// MSKINS-263 - image without class in banner
assert html.contains('<div id="bannerLeft" class="me-auto"><p class="m-0 fs-3 fw-bold"><a href="../../../"><img class="img-fluid banner-light" src="../../../images/apache-maven-project-light-theme.svg" alt="Apache Maven Fluido Skin IT, topbar layout" style="width: 489px;height: 83px;" /><img class="img-fluid banner-dark" src="../../../images/apache-maven-project-dark-theme.svg" alt="Apache Maven Fluido Skin IT, topbar layout" style="width: 489px;height: 83px;" /></a></p></div>')
// the right banner is hidden on phones when there is a left one
assert html.contains('<div id="bannerRight" class="d-none d-md-block"><img class="img-fluid" src="../../../images/logos/MavenLogoLeaf.svg" alt="" style="width: 340px;height: 83px;" /></div>')

// edit link after the page title: the SVG icon, copied with the site
// the page title is not repeated as a crumb; an editable page gets an Edit icon next to the theme switch
assert html =~ /<a class="edit-link btn btn-sm border" href="https:\/\/github\.com\/[^"]+" title="Edit" aria-label="Edit">/
assert !html.contains( 'aria-current="page">Apache Maven Fluido Skin IT' )
assert new File( basedir, "target/site/images/edit.svg" ).exists()
assert !new File( basedir, "target/site/images/accessories-text-editor.png" ).exists()

// no side menu, so nothing to collapse or jump to
assert !html.contains( 'sidebarMenu' )
assert !html.contains( 'menu-toggle' )

// Bootstrap's navbar: a toggler below 992 px, the menus as Bootstrap dropdowns, and Bootstrap's script bundle
assert html.contains( 'data-bs-target="#topbarMenus"' )
assert html =~ /<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">[^<]+<\/a>/
assert html.contains( '<script src="./js/bootstrap-5.3.8.bundle.min.js" defer></script>' )
assert new File( basedir, "target/site/js/bootstrap-5.3.8.bundle.min.js" ).exists()
