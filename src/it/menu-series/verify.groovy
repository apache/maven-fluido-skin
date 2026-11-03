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

String page( String path ) {
    new File( basedir, "target/site/" + path ).text
}

// a page listed in the menu is marked as the current page
assert page( "docs/history.html" ).contains( '<li><a class="active" aria-current="page">History</a></li>' )
assert !page( "docs/history.html" ).contains( 'aria-current="true"' )

// an unlisted page in a subdirectory of a listed item's directory marks that item as part of its series
String notes = page( "docs/1.0/release-notes.html" )
assert notes.contains( 'class="active" aria-current="true">History</a></li>' )
assert !notes.contains( 'aria-current="page"' )
assert page( "docs/2.0/release-notes.html" ).contains( 'class="active" aria-current="true">History</a></li>' )

// a page next to the listed item, not below it, marks nothing
assert !page( "docs/unlisted.html" ).contains( 'class="active"' )

// a directory that two items point into belongs to neither
assert !page( "tools/a/details.html" ).contains( 'class="active"' )

// pages at the site root never take a series marker
assert !page( "index.html" ).contains( 'aria-current="true"' )
