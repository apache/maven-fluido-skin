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
def count = { String pattern -> ( html =~ pattern ).size() }

// the skin writes the charset of the output encoding, the page cannot declare a second one
assert count( /<meta charset=/ ) == 1
assert html.contains( '<meta charset="UTF-8" />' )
// a viewport or generator from the page replaces the skin's own
assert count( /<meta name="viewport"/ ) == 1
assert html.contains( 'content="width=device-width, initial-scale=2"' )
assert count( /<meta name="generator"/ ) == 1
assert html.contains( '<meta name="generator" content="Hand written" />' )
// everything else from the page head is kept
assert html.contains( '<meta name="description" content="Meta elements from the page head" />' )
