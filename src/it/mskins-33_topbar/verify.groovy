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

File buildLog = new File( basedir, 'build.log' )
assert buildLog.exists()
assert buildLog.text.contains('[INFO] BUILD SUCCESS')

File index = new File( basedir, 'target/site/index.html')
assert index.exists()
assert index.text.contains('<div id="fb-root"></div>')
assert index.text.contains('<script async defer crossorigin="anonymous" src="https://connect.facebook.net/en/sdk.js#xfbml=1&version=v6.0"></script>')
assert index.text.contains(
        '<div class="fb-like pull-right" style="border:none; margin-top: 10px" data-href="https://maven.apache.org/skins/maven-fluido-skin/mskins-33/" data-layout="button_count" data-show-faces="false" data-action="like" data-share="false"></div>')

