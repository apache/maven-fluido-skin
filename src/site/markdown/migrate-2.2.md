---
title: Migrating to Fluido 2.2.0
---

<!--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

# Migrating to Fluido 2.2.0

Fluido 2.2.0 keeps the site descriptor and the URLs and anchors of every page, but it replaces the framework the
skin is built on: Bootstrap 2.3.2 and jQuery 1.11.2 become Bootstrap 5.3.8, and Google Code Prettify becomes
highlight.js 11.12.0. A site that only uses the site descriptor and Markdown or other Doxia sources needs no change.
A site with its own `site.css`, or with HTML in its pages that uses Bootstrap 2 classes, needs the updates below.

## Staying on 2.1.0

To keep the old look until your pages are updated, pin the version in the `skin` element of your `site.xml`:

```xml
<skin>
  <groupId>org.apache.maven.skins</groupId>
  <artifactId>maven-fluido-skin</artifactId>
  <version>2.1.0</version>
</skin>
```

A site that names no version gets the newest release, and so 2.2.0, at its next build.

## What changes on every page

- **Look.** Bootstrap 5's typography, spacing and components. The URLs of the pages and the ids that Doxia, the
  reports and the skin write (`bodyColumn`, `leftColumn`, `banner`, `bannerLeft`, `bannerRight`, `breadcrumbs`,
  `topbar`, `publishDate`, `projectVersion`) stay the same.
- **Side menu on phones.** Below 992 px the menu is open above the content; a button in the banner row hides and
  shows it, and every page starts with it open. Without JavaScript the button does not show and the menu stays open.
  The menu now comes before the content in the page source, as in 2.1.0.
- **Top bar on phones.** With `topBarEnabled`, the top bar's menus fold behind Bootstrap's menu button below 992 px
  and open as dropdowns; both need JavaScript.
- **Code blocks.** highlight.js colours them. `pre` elements still get the `prettyprint` class (and `linenums` with
  `sourceLineNumbersEnabled`, which still works), but the colours come from `hljs-*` classes; styles written for
  prettify's `.pln`, `.str`, `.kwd`, `.com`, `.typ`, `.lit`, `.pun`, `.tag`, `.atn`, `.atv` no longer apply.
- **Tables.** Doxia's `bodyTable` becomes Bootstrap's `table table-striped` (and `bodyTableBorder` adds
  `table-bordered`); wide tables scroll inside their own box and stack on phones.
- **Links** use a darker blue than Bootstrap's default so that they reach a 4.5:1 contrast on light grey boxes too.
- **Third-party requests.** The skin loads nothing from outside your site unless you configure it (Matomo, the GitHub
  ribbon link).
- **`poweredBy`.** 2.1.0 wrote a `<div id="poweredBy">` below the side menu on every page, even with nothing to show in it. 2.2.0 writes it only
  when the site descriptor has a `poweredBy` element.

## Site descriptor options

| Option | 2.2.0 |
|---|---|
| `googleAnalytics`, `googleAnalytics/accountId`, `anonymizeIp`, `forceSSL` | Removed, ignored. Use `matomo` (see [Matomo](./index.html#Matomo_.28e.g._Apache_Analytics.29)), or put your own script in the `head` element of the site descriptor's `body`. |
| `googleSearch`, `googleSearch/sitesearch` | Removed, ignored. The search box sent queries to Google. |
| `facebookLike` | Removed, ignored. |
| `twitter` | Removed, ignored. |
| `darkThemeEnabled` | New. Off by default: pages are light, with no theme switch and no theme script. On: the page follows the browser's colour scheme and a switch at the top of the menu offers Light, Dark and Auto. Needs JavaScript. |
| `themeSwitch` | New. With `darkThemeEnabled`, `false` removes the switch and keeps following the browser. Without `darkThemeEnabled` it has no effect. |
| `navBarStyle` | `navbar-inverse` becomes Bootstrap 5's dark top bar (`data-bs-theme="dark"`); other Bootstrap 2 navbar classes are passed through and may no longer mean anything. |
| `leftColumnClass`, `bodyColumnClass` | `spanN` becomes `col-lg-N`. Defaults are `col-lg-3 col-xl-2` and `col-lg-9 col-xl-10`. |
| `copyrightClass` | `pull-right` and `pull-left` become `float-end` and `float-start`. |
| `topBarContainerStyle`, `topBarEnabled`, `topBarIcon`, `sideBarEnabled`, `breadcrumbDivider`, `profile`, `gitHub`, `anchorJs`, `sourceLineNumbersEnabled`, `skipGenerationDate` | Unchanged. |

## Bootstrap 2 classes in your own HTML and site.css

Pages written in HTML or XHTML, and Markdown with inline HTML, may use Bootstrap 2 classes. Bootstrap 5 does not
know them, so they lose their styling. The common ones:

| Bootstrap 2 | Bootstrap 5 |
|---|---|
| `row-fluid` | `row` |
| `span1` … `span12` | `col-md-1` … `col-md-12` (or `col-1` … `col-12` to keep columns on phones) |
| `offset1` … `offset11` | `offset-md-1` … `offset-md-11` |
| `container-fluid` | `container-fluid` (unchanged) |
| `pull-left`, `pull-right` | `float-start`, `float-end` |
| `hidden-phone` | `d-none d-md-block` |
| `visible-phone` | `d-md-none` |
| `hidden-desktop` | `d-lg-none` |
| `visible-desktop` | `d-none d-lg-block` |
| `muted` | `text-body-secondary` |
| `text-error` | `text-danger` |
| `label`, `label-success`, `label-important`, `label-warning`, `label-info`, `label-inverse` | `badge`, `badge text-bg-success`, `badge text-bg-danger`, `badge text-bg-warning`, `badge text-bg-info`, `badge text-bg-dark` |
| `badge-*` | `badge text-bg-*` |
| `alert` (yellow by default) | `alert alert-warning` |
| `alert-error` | `alert-danger` |
| `alert-block` | `alert` (no separate class) |
| `btn` (grey by default) | `btn btn-secondary` (or `btn-light`) |
| `btn-mini`, `btn-small`, `btn-large` | `btn-sm`, `btn-sm`, `btn-lg` |
| `btn-inverse` | `btn-dark` |
| `well` | `card card-body`, or `p-3 bg-body-tertiary border rounded` |
| `well-small`, `well-large` | add `p-2` or `p-4` instead of `p-3` |
| `hero-unit` | `p-5 mb-4 bg-body-tertiary rounded-3` |
| `table-condensed` | `table-sm` |
| `img-rounded`, `img-circle`, `img-polaroid` | `rounded`, `rounded-circle`, `img-thumbnail` |
| `icon-*` (Glyphicons) | none: Bootstrap 5 ships no icon font, and the Glyphicons images are gone from the skin |
| `nav-list`, `nav-header` | `nav flex-column`; a heading as `fw-semibold` text |
| `navbar-inner` | not needed |
| `dropdown-toggle` with `data-toggle` | `data-bs-toggle` |

Bootstrap's own migration guides list the rest: [2 to 3](https://getbootstrap.com/docs/3.4/migration/),
[3 to 4](https://getbootstrap.com/docs/4.6/migration/) and [4 to 5](https://getbootstrap.com/docs/5.3/migration/).

In `site.css`, selectors on the skin's own elements keep working (`#bodyColumn`, `#leftColumn`, `#banner`,
`#breadcrumbs`, `#topbar`). Selectors on Bootstrap 2 structure (`.navbar-inner`, `.nav-list`, `.well`, `.sidebar-nav
.nav-header`) no longer match. Colours are best set with Bootstrap's CSS variables, for example `--bs-link-color`,
so that they follow the dark colour mode if you enable it.

## Files that are no longer in the skin

In 2.1.0, jQuery, Bootstrap, Prettify and AnchorJS were not separate files: they were bundled into
`apache-maven-fluido-2.1.0.min.js` and `apache-maven-fluido-2.1.0.min.css`. 2.2.0 ships its libraries as separate
files.

| 2.1.0 | 2.2.0 |
|---|---|
| jQuery 1.11.2, in the bundle | none: the skin no longer uses jQuery; a page that needs it must ship its own copy |
| Bootstrap 2.3.2, in the bundle | `js/bootstrap-5.3.8.bundle.min.js`, `css/bootstrap-5.3.8.min.css` |
| Google Code Prettify, in the bundle | `js/highlight-11.12.0.min.js`; its colours are in `css/apache-maven-fluido-2.2.0.min.css` |
| AnchorJS 4.3.1, in the bundle | `js/anchor-4.3.1.min.js` |
| `img/glyphicons-halflings*.png`, `fonts/glyphicons-halflings-regular.*` | none |
| `images/profiles/pre-release.png`, `retired.png`, `sandbox.png` | the same names with `.svg` |
| `images/accessories-text-editor.png`, `images/window-new.png` | `images/edit.svg` for the edit link; no external-link image |

`css/apache-maven-fluido-2.2.0.min.css` and `js/apache-maven-fluido-2.2.0.min.js` keep the naming of earlier
releases. A page that links to `apache-maven-fluido-2.1.0.min.css` or `.js` by name, such as a hand-written error
page, has to be updated.
