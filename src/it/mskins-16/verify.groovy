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

String html = new File( basedir, "target/site/index.html" ).text

// no search, social buttons or poweredBy configured: only the menu box, no empty one below it
assert ( html =~ /class="sidebar-nav[ "]/ ).size() == 1
assert !html.contains( 'id="poweredBy"' )

// no GitHub ribbon and no profile configured: their resources are neither linked nor copied
File site = new File( basedir, "target/site" )
assert !html.contains( 'gh-fork-ribbon' )
assert !new File( site, "css/gh-fork-ribbon.min.css" ).exists()
for ( String profile : [ 'pre-release', 'retired', 'sandbox' ] ) {
    assert !new File( site, "images/profiles/${profile}.svg" ).exists()
}

// banner images without alt: the project name when the banner has no name, empty when its name gives the text
assert html.contains( '<a href="http://www.apache.org"><img class="img-fluid" src="images/apache-maven-project-2.png" alt="Apache Maven Fluido Skin IT, MSKINS-16" ' )
assert html.contains( '<a href="http://www.apache.org"><img class="img-fluid" src="images/apache-maven-project-2.png" alt="" style="width: 473px;height: 93px;border: 1" /> Apache</a>' )
// a right banner that is a link is not hidden on phones, even with a left banner
assert html.contains( '<div id="bannerRight"><p class="m-0 fs-3 fw-bold"><a href="http://www.apache.org">' )
