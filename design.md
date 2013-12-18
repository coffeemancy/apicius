# apicius

## intention

Thin [Chef cookbooks site API](http://docs.opscode.com/api_cookbooks_site.html) implementation that can be used as a local cache or in highly-available, scalable implementations.

## architecture and design

### api

REST, serving up JSON per cookbooks site api from querying cookbook db and cookbook tarballs from cookbook store.

* Need way to query cookbook db
* Need way to serve files from cookbook store
* Need way to POST (PUT?)
    * Updates cookbook db, not for sending files
    * Authentication done by "something else" that apicius is behind (e.g. nginx)
    * file location (absolute path or relative path to apicius)
    * MD5 hash of file (optional and optionally checked on a request for file)


### cookbook db

Simple relational database queried by api indexing cookbook metadata and tarball locations in cookbook store.

* Need way to build db based on files in cookbook store
* Start with sqlite, could eventually add options for other db backends

### cookbook store

Directory of cookbook tarballs versioned based on git tags of cookbook repos.

* "Something else" (e.g. Jenkins/scp) transfers versioned tarballs of git repos based on tags
