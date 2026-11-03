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
// position right: the last item of the list on the right of the breadcrumbs bar, after the version
String html = index.text
assert html.contains( '<li id="publishDate">Last Published: 1990-01-01</li>' )
String right = html.substring( html.indexOf( '<ul class="list-unstyled d-flex flex-wrap column-gap-3 mb-0 ms-md-auto">' ) )
assert right.indexOf( 'id="projectVersion"' ) < right.indexOf( 'id="publishDate"' )
