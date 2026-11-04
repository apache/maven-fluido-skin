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
assert html.contains( '<li><a class="active" aria-current="page">About</a></li>' )
// inactive menu
assert html.contains( '<li><a href="summary.html">Summary</a></li>' )
assert html.contains( '<li><a href="plugins.html">Plugins</a></li>' )
// breadcrumb
assert html.contains( '<li class="breadcrumb-item"><a href="https://www.apache.org/">Apache</a></li>' )

// MSKINS-263 - image without class in banner
assert html.contains('<div id="bannerLeft" class="me-auto"><p class="m-0 fs-3 fw-bold"><a href="../../../"><img class="img-fluid banner-light" src="../../../images/apache-maven-project-light-theme.svg" alt="Apache Maven Fluido Skin IT, sidebar layout" style="width: 489px;height: 83px;" /><img class="img-fluid banner-dark" src="../../../images/apache-maven-project-dark-theme.svg" alt="Apache Maven Fluido Skin IT, sidebar layout" style="width: 489px;height: 83px;" /></a></p></div>')
// the right banner is hidden on phones when there is a left one
assert html.contains('<div id="bannerRight" class="d-none d-md-block"><img class="img-fluid" src="../../../images/logos/MavenLogoLeaf.svg" alt="" style="width: 340px;height: 83px;" /></div>')

// below 992 px the side menu is an offcanvas panel, opened by a Menu button at the top
assert html.contains( '<div class="offcanvas-lg offcanvas-start" tabindex="-1" id="sidebarMenu" aria-labelledby="sidebarMenuLabel">' )
// the hamburger that opens it is the first thing in the banner row
assert html =~ /<div id="banner" class="navbar[^"]*">\s*<button type="button" class="navbar-toggler menu-toggle d-lg-none[^"]*" data-bs-toggle="offcanvas" data-bs-target="#sidebarMenu"/

// Bootstrap 5 stylesheet and script bundle, no jQuery
assert html.contains( '<link rel="stylesheet" href="./css/bootstrap-5.3.8.min.css" />' )
assert new File( basedir, "target/site/css/bootstrap-5.3.8.min.css" ).exists()
assert !html.contains( 'jquery' )
assert new File( basedir, "target/site/js/bootstrap-5.3.8.bundle.min.js" ).exists()
// default columns: Bootstrap 5 grid, content first in the source
assert html.contains( '<main id="bodyColumn" class="col-lg-9 col-xl-10 order-lg-last">' )
assert html.contains( '<header id="leftColumn" class="col-lg-3 col-xl-2 order-lg-first">' )
assert html.indexOf( 'id="bodyColumn"' ) < html.indexOf( 'id="leftColumn"' )
// the theme switch is on by default: light until the reader chooses, script in the head
assert html.contains( '<button type="button" class="theme-switch btn btn-sm border" title="Theme" aria-label="Theme" hidden>' )
assert html.contains( 'class="theme-icon-dark"' )
// the switch sits in the menu, after the panel's header
assert html.indexOf( 'class="theme-switch' ) > html.indexOf( 'id="sidebarMenu"' )
assert !html.contains( 'data-fluido-theme=' )
assert html.contains( '<script src="./js/fluido-theme.js"></script>' )

// wide tables scroll in their own focusable box (and stack on phones); code blocks are focusable too
String deps = new File( basedir, "target/site/dependencies.html" ).text
assert deps.contains( '<div class="table-wrap table-responsive" tabindex="0"><table class="table table-striped">' )
assert html =~ /<pre tabindex="0" class="prettyprint linenums">/
// report icons that report plugins still reference stay in the skin
for ( String icon : [ 'error', 'help', 'info', 'success', 'warning' ] ) {
    assert new File( basedir, "target/site/images/icon_${icon}_sml.gif" ).exists()
}
// the Edit icon sits next to the theme switch in the menu
assert html =~ /(?s)<div class="page-tools d-flex align-items-center gap-2 mb-2 px-3"><button type="button" class="theme-switch[^>]*>.*?<\/button>\s*<a class="edit-link btn btn-sm border" href="[^"]+"/

// pages listed in sideBarSkipped render without the side menu, the others keep it
String summary = new File( basedir, "target/site/summary.html" ).text
assert !summary.contains( 'id="leftColumn"' )
assert !summary.contains( 'data-bs-target="#sidebarMenu"' )
assert !new File( basedir, "target/site/dependency-info.html" ).text.contains( 'id="leftColumn"' )
assert html.contains( 'id="leftColumn"' )
assert new File( basedir, "target/site/plugins.html" ).text.contains( 'id="leftColumn"' )

// a page in a subdirectory is named by its path from the site root
assert !new File( basedir, "target/site/guide/overview.html" ).text.contains( 'id="leftColumn"' )
