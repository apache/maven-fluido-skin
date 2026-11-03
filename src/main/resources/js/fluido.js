/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/* Plain DOM code, ES5: the phone menu button, highlighting and labels for the stacked tables. The top bar's menus are
   Bootstrap's own components. */
( function() {

    function each( list, fn ) {
        for ( var i = 0; i < list.length; i++ ) {
            fn( list[ i ], i );
        }
    }

    function trim( text ) {
        return ( text || '' ).replace( /^\s+|\s+$/g, '' );
    }

    function addClass( element, name ) {
        element.className = trim( element.className + ' ' + name );
    }

    // a parameter table (Name or Element, Type, maybe Since, ..., Description) shows the type and the since
    // version as a small line under the name and hides their columns, so the description gets the width
    function mergeParameterColumns( rows, labels ) {
        var lower = [];
        each( labels, function( label ) {
            lower.push( label.toLowerCase() );
        } );
        var type = lower.indexOf( 'type' ), since = lower.indexOf( 'since' );
        if ( type < 1 || lower[ lower.length - 1 ] !== 'description' ) {
            return;
        }
        var merged = [ type ];
        if ( since > 0 && since < lower.length - 1 ) {
            merged.push( since );
        }
        each( merged, function( i ) {
            addClass( rows[ 0 ].children[ i ], 'param-merged' );
        } );
        for ( var r = 1; r < rows.length; r++ ) {
            var cells = rows[ r ].children;
            if ( cells.length !== labels.length ) {
                continue;
            }
            var meta = document.createElement( 'div' );
            meta.className = 'param-meta';
            each( merged, function( i ) {
                var cell = cells[ i ];
                var text = trim( cell.textContent );
                // plugin-tools writes "-" for a parameter without a since version
                if ( text && text !== '-' ) {
                    if ( meta.childNodes.length ) {
                        meta.appendChild( document.createTextNode( ' \u00B7 ' ) );
                    }
                    if ( i === since ) {
                        meta.appendChild( document.createTextNode( labels[ i ] + ' ' ) );
                    }
                    var copy = cell.cloneNode( true );
                    each( copy.querySelectorAll( '[id], [name]' ), function( el ) {
                        el.removeAttribute( 'id' );
                        el.removeAttribute( 'name' );
                    } );
                    while ( copy.firstChild ) {
                        meta.appendChild( copy.firstChild );
                    }
                }
                addClass( cell, 'param-merged' );
            } );
            if ( meta.childNodes.length ) {
                cells[ 0 ].appendChild( meta );
            }
        }
    }

    // below 992 px the side menu is open above the content; the button in the banner row hides and shows it. Without
    // JavaScript the button stays hidden and the menu open. Nothing is stored: every page starts with the menu open.
    function menuToggle() {
        var button = document.querySelector( '.menu-toggle' );
        var menu = document.getElementById( 'sidebarMenu' );
        if ( !button || !menu ) {
            return;
        }
        button.hidden = false;
        button.addEventListener( 'click', function() {
            var open = button.getAttribute( 'aria-expanded' ) !== 'false';
            button.setAttribute( 'aria-expanded', open ? 'false' : 'true' );
            if ( open ) {
                addClass( menu, 'menu-hidden' );
            } else {
                menu.className = trim( menu.className.replace( /(^|\s)menu-hidden(\s|$)/g, ' ' ) );
            }
        } );
    }

    function init() {
        menuToggle();

        // highlight the code blocks that site.vm marked; line numbers where sourceLineNumbersEnabled added linenums
        if ( window.hljs ) {
            each( document.querySelectorAll( 'pre.prettyprint > code' ), function( code ) {
                // a language highlight.js does not know (Markdown ```unknown) is detected instead, as prettify did
                var lang = code.className.match( /(^|\s)language-(\S+)/ );
                if ( lang && !hljs.getLanguage( lang[ 2 ] ) ) {
                    code.className = trim( code.className.replace( lang[ 0 ], ' ' ) );
                }
                hljs.highlightElement( code );
                if ( hljs.lineNumbersBlock && /(^|\s)linenums(\s|$)/.test( code.parentNode.className ) ) {
                    hljs.lineNumbersBlock( code, { singleLine: true } );
                }
            } );
        }

        // label each cell with its column heading, for the stacked tables on narrow screens (fluido.css);
        // only tables whose first row is a plain heading row, and only rows with one cell per heading
        each( document.querySelectorAll( '.table-wrap > table' ), function( table ) {
            var rows = [];
            each( table.children, function( section ) {
                if ( section.tagName === 'THEAD' || section.tagName === 'TBODY' ) {
                    each( section.children, function( row ) {
                        rows.push( row );
                    } );
                }
            } );
            var head = rows[ 0 ];
            if ( !head ) {
                return;
            }
            var labels = [], plain = head.children.length > 0;
            each( head.children, function( cell ) {
                if ( cell.tagName !== 'TH' || cell.hasAttribute( 'rowspan' ) || cell.hasAttribute( 'colspan' ) ) {
                    plain = false;
                }
                labels.push( trim( cell.textContent ) );
            } );
            if ( !plain ) {
                return;
            }
            head.className = trim( head.className + ' table-head' );
            for ( var r = 1; r < rows.length; r++ ) {
                if ( rows[ r ].children.length === labels.length ) {
                    each( rows[ r ].children, function( cell, i ) {
                        cell.setAttribute( 'data-label', labels[ i ] );
                        // an empty cell shows nothing but its label in a card: leave it out there
                        if ( !trim( cell.textContent ) && !cell.querySelector( 'img' ) ) {
                            cell.className = trim( cell.className + ' table-cell-empty' );
                        }
                    } );
                }
            }
            mergeParameterColumns( rows, labels );
        } );
    }

    if ( document.readyState === 'loading' ) {
        document.addEventListener( 'DOMContentLoaded', init );
    } else {
        init();
    }
} )();
