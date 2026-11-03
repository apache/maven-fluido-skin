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

// navBarStyle navbar-inverse: a dark top bar through Bootstrap's dark colour mode; the Bootstrap 2 class itself is dropped
assert html =~ /<header id="topbar" class="navbar navbar-expand-lg bg-body-tertiary border-bottom\s*" data-bs-theme="dark">/
assert !( html =~ /class="[^"]*navbar-inverse/ )
// topBarIcon: the brand link with its image
assert html.contains( '<a class="brand navbar-brand" href="index.html"><img class="img-fluid" src="images/topbar-logo.png" alt="Maven Fluido Skin" />' )
